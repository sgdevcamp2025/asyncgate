import { Client } from '@stomp/stompjs';
import React, { useEffect, useRef, useState } from 'react';

import { getUserId } from '@/api/users';
import { useChannelActionStore } from '@/stores/channelAction';
import { useUserInfoStore } from '@/stores/userInfo';
import { tokenAxios } from '@/utils/axios';

const SERVER_URL = import.meta.env.VITE_SIGNALING;

// user interface
interface UserInRoom {
  id: string;
  nickname: string;
  profile_image: string;
  is_mic_enabled: boolean;
  is_camera_enabled: boolean;
  is_screen_sharing_enabled: boolean;
}

const WebRTC = () => {
  const stompClient = useRef<Client | null>(null);
  const [connected, setConnected] = useState(false);
  const [roomId, setRoomId] = useState('');
  const [offerSent, setOfferSent] = useState(false);
  const localVideoRef = useRef<HTMLVideoElement | null>(null);
  const remoteVideoRef = useRef<HTMLVideoElement | null>(null);
  const peerConnection = useRef<RTCPeerConnection | null>(null);

  // 구독된 publisher id들을 저장
  const [subscribedPublishers, setSubscribedPublishers] = useState<string[]>([]);
  // 아직 publisher id와 매핑되지 않은 미디어 스트림을 저장
  const [pendingStreams, setPendingStreams] = useState<MediaStream[]>([]);
  // 최종적으로 매핑된 remote stream을 저장 (키: publisher id)
  const [remoteStreams, setRemoteStreams] = useState<{ [userId: string]: MediaStream }>({});

  // 최초 입장인지 확인
  const [firstEnter, setFirstEnter] = useState(true);

  // 유저 리스트
  const [userInRoomList, setUserInRoomList] = useState<UserInRoom[]>([]);
  const {
    isInVoiceChannel,
    isSharingScreen,
    isVideoOn,
    isMicOn,
    setIsInVoiceChannel,
    setIsSharingScreen,
    setIsVideoOn,
    setIsMicOn,
  } = useChannelActionStore();

  const token = localStorage.getItem('access_token');

  const { userInfo } = useUserInfoStore();
  const userId = userInfo?.userId || '';

  // ✅ STOMP WebSocket 연결 함수
  const connectStomp = async () => {
    if (!roomId) {
      alert('방 ID를 입력해주세요!');
      return;
    }

    console.log('🟢 WebSocket 연결 시도 중...');
    if (!token) return null;
    const client = new Client({
      webSocketFactory: () => new WebSocket(SERVER_URL, ['v10.stomp', token]),
      connectHeaders: {
        Authorization: `Bearer ${token}`,
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 10000,
      heartbeatOutgoing: 10000,
      onConnect: () => {
        console.log(`✅ STOMP WebSocket 연결 성공 (Room: ${roomId})`);
        setConnected(true);

        client.subscribe(`/topic/users/${roomId}`, (message) => {
          const users = JSON.parse(message.body);
          console.log('users을 수신 하였습니다. : ', users);
          handleUsers(users);
        });

        // ✅ STOMP WebSocket이 연결된 후 Answer 메시지 Subscribe 실행
        client.subscribe(`/topic/answer/${roomId}/${userId}`, (message) => {
          const answer = JSON.parse(message.body);
          console.log('answer을 수신 하였습니다. : ', answer);
          handleAnswer(answer.message);
        });

        client.subscribe(`/topic/candidate/${roomId}/${userId}`, (message) => {
          const candidate = JSON.parse(message.body);
          console.log('candidate을 수신 하였습니다. : ', candidate);
          handleIceCandidate(candidate.candidate);
        });

        client.subscribe(`/topic/publisher/${roomId}`, (message) => {
          const publisherId = JSON.parse(message.body).message;
          console.log('publisher 수신:', publisherId);

          // publisher id가 자신의 userId와 같으면 아무 작업도 하지 않음
          if (publisherId === userId) {
            console.log('자신의 publisher id는 무시합니다:', publisherId);
            return;
          }

          handlePublish(publisherId);

          // 이미 ontrack 이벤트에서 pending stream이 있다면 즉시 매핑
          setPendingStreams((prevPending) => {
            if (prevPending.length > 0) {
              const stream = prevPending[0];
              setRemoteStreams((prevStreams) => ({
                ...prevStreams,
                [publisherId]: stream,
              }));
              return prevPending.slice(1);
            } else {
              // 아직 ontrack 이벤트가 도착하지 않았다면, subscribedPublishers에 publisher id를 저장
              setSubscribedPublishers((prev) => [...prev, publisherId]);
              return prevPending;
            }
          });
        });

        client.subscribe(`/topic/removed/${roomId}`, (message) => {
          const recentUsers = JSON.parse(message.body);
          console.log('recentUsers', recentUsers);
        });

        console.log(`✅ 구독 성공 하였습니다.`);
      },
      onDisconnect: () => {
        alert('🔌 STOMP WebSocket 연결 해제됨');
        console.log('🔌 STOMP WebSocket 연결 해제됨');
        setConnected(false);
      },
      onWebSocketError: (error) => {
        alert(`🚨 WebSocket 오류 발생: ${error}`);
        console.error('🚨 WebSocket 오류 발생:', error);
      },
      onStompError: (frame) => {
        alert(`🚨 STOMP 오류 발생: ${frame}`);
        console.error('🚨 STOMP 오류 발생:', frame);
      },
    });

    client.activate();
    stompClient.current = client;
  };

  // ✅ WebRTC Offer 전송 (버튼 클릭 시 실행)
  const sendOffer = async () => {
    if (!stompClient.current || !connected) {
      alert('offer STOMP WebSocket이 연결되지 않았습니다.');
      return;
    }

    try {
      const localStream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true,
      });

      if (localVideoRef.current) {
        localVideoRef.current.srcObject = localStream;
      }

      peerConnection.current = new RTCPeerConnection({
        iceServers: [
          { urls: 'stun:stun.l.google.com:19302' },
          {
            urls: 'turn:asyncturn.store',
            username: 'asyncgate5',
            credential: 'smilegate5',
          },
        ],
      });

      localStream.getTracks().forEach((track) => {
        peerConnection.current?.addTrack(track, localStream);
      });

      // ontrack 이벤트: remote 미디어 스트림 수신
      // ontrack 이벤트: 원격 미디어 스트림 수신 시 호출
      peerConnection.current.ontrack = (event) => {
        console.log('ontrack 이벤트 수신:', event);
        const stream = event.streams[0];
        console.log('수신된 stream:', stream, '비디오 트랙:', stream.getVideoTracks());

        // 이미 signaling에서 publisher id를 받은 경우 pending 없이 바로 매핑
        setSubscribedPublishers((prevPublishers) => {
          if (prevPublishers.length > 0) {
            const [publisherId, ...rest] = prevPublishers;
            setRemoteStreams((prevStreams) => ({
              ...prevStreams,
              [publisherId]: stream,
            }));
            return rest;
          } else {
            // 아직 publisher id가 도착하지 않았다면 pending queue에 저장
            setPendingStreams((prev) => [...prev, stream]);
            return prevPublishers;
          }
        });
      };

      const offer = await peerConnection.current.createOffer();
      await peerConnection.current.setLocalDescription(offer);

      // 🔥 STOMP를 사용해 WebRTC Offer 전송
      stompClient.current.publish({
        destination: '/offer',
        body: JSON.stringify({
          data: {
            // ✅ data 내부에 room_id 포함
            room_id: roomId,
            sdp_offer: offer.sdp,
          },
        }),
      });

      console.log('📤 WebRTC Offer 전송:', offer.sdp);
    } catch (error) {
      console.error('❌ Offer 전송 실패:', error);
    }
  };

  // ✅ kurento ice 수집 요청
  const sendGatherIceCandidate = async () => {
    if (!stompClient.current) {
      alert('gather STOMP WebSocket이 연결되지 않았습니다.');
      return;
    }

    try {
      // 🔥 STOMP를 사용해 WebRTC Offer 전송
      stompClient.current.publish({
        destination: '/gather/candidate',
        body: JSON.stringify({
          data: {
            room_id: roomId,
          },
        }),
      });

      sendIceCandidates(); // 🔥 SDP Answer 수신 후 ICE Candidate 전송
    } catch (error) {
      console.error('gather 요청 실패:', error);
    }
  };

  // ✅ WebRTC Answer 처리
  const handleAnswer = async (sdpAnswer: string) => {
    if (!peerConnection.current) return;

    try {
      await peerConnection.current.setRemoteDescription(
        new RTCSessionDescription({
          type: 'answer',
          sdp: sdpAnswer,
        }),
      );
    } catch (error) {
      console.error('Answer 요청 실패:', error);
    } finally {
      sendGatherIceCandidate();
    }
  };

  // ✅ WebRTC Users 처리
  const handleUsers = async (users: UserInRoom[]) => {
    if (!peerConnection.current) return;

    setUserInRoomList(users);

    if (firstEnter) {
      for (const user of users) {
        console.log('subscribe 합니다. ~');
        console.log(user);
        await handlePublish(user.id);
      }
      setFirstEnter(false);
    }
  };

  // ✅ WebRTC Candidate 처리
  const handleIceCandidate = async (candidate: RTCIceCandidateInit) => {
    if (!peerConnection.current) return;

    console.log('📥 ICE Candidate 수신:', candidate);

    try {
      await peerConnection.current.addIceCandidate(new RTCIceCandidate(candidate));
      console.log('✅ ICE Candidate 추가 성공');
    } catch (error) {
      console.error('❌ ICE Candidate 추가 실패:', error);
    }
  };

  // ✅ WebRTC Candidate 처리
  const handlePublish = async (publisher_id: string): Promise<void> => {
    if (!peerConnection.current || !stompClient.current) return;

    stompClient.current.publish({
      destination: '/subscribe',
      body: JSON.stringify({
        data: {
          room_id: roomId,
          publisher_id: publisher_id,
        },
      }),
    });
  };

  // ✅ ICE Candidate 전송 (SDP Answer를 받은 후 실행)
  const sendIceCandidates = () => {
    if (!peerConnection.current || !stompClient.current) return;

    console.log('접근 완료 !!');
    peerConnection.current.onicecandidate = (event) => {
      if (event.candidate) {
        if (event.candidate.candidate.includes('typ host')) {
          console.log('typ host');
          return; // host 후보는 버림
        }

        console.log('전송 ice candidate : ', event.candidate);

        if (stompClient.current) {
          stompClient.current.publish({
            destination: '/candidate',
            body: JSON.stringify({
              data: {
                room_id: roomId,
                candidate: {
                  candidate: event.candidate.candidate,
                  sdpMid: event.candidate.sdpMid,
                  sdpMLineIndex: event.candidate.sdpMLineIndex,
                },
              },
            }),
          });
        }
        console.log('📤 ICE Candidate 전송:', event.candidate);
      }
    };

    peerConnection.current.onicegatheringstatechange = () => {
      console.log('[pc] ICE 수집 상태:', peerConnection.current?.iceGatheringState);

      if (peerConnection.current?.iceGatheringState === 'complete') {
        console.log('[pc] ICE 후보 수집 완료');
      }
    };

    peerConnection.current.oniceconnectionstatechange = () => {
      const state = peerConnection.current?.iceConnectionState;
      console.log('[pc] ICE 연결 상태 변경:', state);
    };
  };

  // ✅ STOMP 연결 해제 함수
  const disconnectStomp = () => {
    if (stompClient.current) {
      stompClient.current.deactivate();
      stompClient.current = null;
      setConnected(false);
      console.log('🔌 STOMP WebSocket 연결 해제 시도');
    }
  };

  const joinRoom = async (roomId: string) => {
    if (!roomId) {
      alert('방 ID를 입력해주세요!');
      return;
    }

    try {
      const response = await tokenAxios.post(`https://api.jungeunjipi.com/room/${roomId}/join`, {
        audio_enabled: isMicOn,
        media_enabled: isVideoOn,
        data_enabled: isSharingScreen,
      });

      if (response) {
        console.log('joinroom에서 얻은 sdp_answer', response.data.sdp_answer);
        handleSdpAnswer(response.data.sdp_answer);
        setIsInVoiceChannel(true);
      } else {
        console.error('참여 실패:', response);
      }
    } catch (error) {
      console.error('API 요청 오류:', error);
    }
  };

  const leaveRoom = async (roomId: string) => {
    if (!roomId) {
      alert('방 ID를 입력해주세요!');
      return;
    }

    try {
      const response = await tokenAxios.delete(`https://api.jungeunjipi.com/room/${roomId}/leave`);
      console.log('방 나가기 성공: ', response);
      // ✅ 상태 초기화
      setIsInVoiceChannel(false);
      setConnected(false);
      setRoomId('');

      disconnectStomp();
    } catch (error) {
      console.error('🚨 방 나가기 오류:', error);
    }
  };

  // ✅ SDP Answer 처리
  const handleSdpAnswer = async (sdpAnswer: string) => {
    if (peerConnection.current) {
      await peerConnection.current.setRemoteDescription(
        new RTCSessionDescription({
          type: 'answer',
          sdp: sdpAnswer,
        }),
      );
      console.log('✅ SDP Answer 설정 완료');
    }
  };

  return (
    <div>
      <h1>Kurento SFU WebRTC</h1>

      <input
        type="text"
        placeholder="Room ID 입력"
        value={roomId}
        onChange={(e) => setRoomId(e.target.value)}
        disabled={connected}
        style={{ marginRight: '10px', padding: '5px' }}
      />

      <button onClick={connectStomp} disabled={connected} style={{ marginRight: '10px' }}>
        {connected ? '🔄 WebSocket 연결됨' : '✅ WebSocket 연결'}
      </button>

      <button onClick={sendOffer} disabled={!connected || offerSent} style={{ marginRight: '10px' }}>
        {offerSent ? '📤 Offer 전송 완료' : '📤 Offer 전송'}
      </button>

      <button onClick={disconnectStomp} disabled={!connected} style={{ marginRight: '10px' }}>
        🔌 WebSocket 해제
      </button>

      <button onClick={() => joinRoom(roomId)} disabled={isInVoiceChannel}>
        {isInVoiceChannel ? '🔄 방 참여 완료' : '✅ 방 참여'}
      </button>

      <button onClick={() => leaveRoom(roomId)} disabled={!isInVoiceChannel}>
        방 나가기
      </button>

      <div style={{ marginTop: '20px' }}>
        <h3>📹 내 화면</h3>
        <video ref={localVideoRef} autoPlay playsInline muted style={{ width: '320px', border: '1px solid black' }} />
      </div>

      <div style={{ marginTop: '20px' }}>
        <h3>🔗 원격 사용자 화면</h3>
        {Object.entries(remoteStreams).map(([userId, stream]) => (
          <div key={userId} style={{ marginBottom: '10px' }}>
            <h4>{userId}</h4>
            <video
              autoPlay
              playsInline
              style={{ width: '320px', border: '1px solid black' }}
              ref={(videoElement) => {
                if (videoElement && videoElement.srcObject !== stream) {
                  videoElement.srcObject = stream;
                }
              }}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default WebRTC;
