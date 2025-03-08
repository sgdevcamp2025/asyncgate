/* eslint-disable @typescript-eslint/no-explicit-any */
import { useRef, useState, useEffect, useCallback } from 'react';

import { useChannelActionStore } from '@/stores/channelAction';
import { tokenAxios } from '@/utils/axios';

import useStompWebRTC from './hooks/useStompWebRTC';

enum MessageType {
  JOIN = 'join',
  USER_JOINED = 'user-joined',
  OFFER = 'offer',
  ANSWER = 'answer',
  CANDIDATE = 'candidate',
  EXIT = 'exit',
  AUDIO = 'AUDIO',
  MEDIA = 'MEDIA',
}

interface AnswerMessage {
  type: string;
  message: string;
}

const VideoTest = () => {
  const localVideoRef = useRef<HTMLVideoElement>(null);
  const remoteVideoRef = useRef<HTMLVideoElement>(null);
  const screenShareRef = useRef<HTMLVideoElement>(null);

  const pcRef = useRef<RTCPeerConnection | null>(null);
  const localStreamRef = useRef<MediaStream | null>(null);
  const screenStreamRef = useRef<MediaStream | null>(null);
  const screenTrackRef = useRef<MediaStreamTrack | null>(null);

  const [roomId, setRoomId] = useState('');
  const [joined, setJoined] = useState(false);
  const [answers, setAnswers] = useState<AnswerMessage>();
  const [statusMessage, setStatusMessage] = useState('');

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

  const { client, isConnected } = useStompWebRTC({
    roomId,
    handleUsers,
    handleAnswer,
    handleIceCandidate,
    handlePublish,
  });

  const token = localStorage.getItem('access_token');

  const handleAnswer = async (sdpAnswer: string) => {
    if (!pcRef.current) return;

    try {
      await pcRef.current.setRemoteDescription(
        new RTCSessionDescription({
          type: 'answer',
          sdp: sdpAnswer,
        }),
      );
    } catch (error) {
      console.error('answer 요청 실패', error);
    } finally {
      sendGetherIceCandidate();
    }
  };

  const sendGetherIceCandidate = async () => {
    if (!client) {
      alert('gather STOMP WebSocket이 연결되지 않았습니다.');
      return;
    }

    try {
      client.publish({
        destination: '/gather/candidate',
        body: JSON.stringify({
          data: {
            room_id: roomId,
          },
        }),
      });

      sendIceCandidates(); // SDP Answer 수신 후 ICE Candidate 전송
    } catch (error) {
      console.error('gather 요청 실패:', error);
    }
  };

  const sendIceCandidates = () => {
    if (!pcRef.current || !client) return;

    console.log('접근 완료');
    pcRef.current.onicecandidate = (event) => {
      if (event.candidate) {
        if (event.candidate.candidate.includes('typ host')) {
          return; // host 후보는 버립니다
        }

        console.log('전송 ice candidate: ', event.candidate);

        client.publish({
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
        console.log('ICE Candidate 전송: ', event.candidate);
      }
    };

    pcRef.current.onicegatheringstatechange = () => {
      console.log('[pc] ICE 수집 상태:', pcRef.current?.iceGatheringState);

      if (pcRef.current?.iceGatheringState === 'complete') {
        console.log('[pc] ICE 후보 수집 완료');
      }
    };

    pcRef.current.oniceconnectionstatechange = () => {
      const state = pcRef.current?.iceConnectionState;
      console.log('[pc] ICE 연결 상태 변경:', state);
    };
  };

  const pendingCandidates = useRef<RTCIceCandidateInit[]>([]);

  // WebSocket 메시지 처리 함수
  const handleStompMessage = async (message: any) => {
    console.log('수신된 메시지', message);

    if (message.type === 'response' && message.users && message.users.length > 0) {
      const user = message.users.filter((user: any) => user.is_me === false);

      console.log('메시지정보', message);
      console.log('사용자정보', message.user);

      if (user.sdpOffer) {
        // 사용자가 sdpOffer를 보냈는지 확인
        setStatusMessage('Offer 수신되었습니다. 응답 중...');

        if (!pcRef.current) {
          await createPeerConnection();
        }
      }

      // 사용자가 sdpAnswer를 보냈는지 확인
      if (user.sdpAnswer) {
        setStatusMessage('응답을 받았습니다. 연결 중...');

        console.log(answers?.message);

        try {
          if (pcRef.current) {
            console.log('원격 설명 설정 시도');
            await pcRef.current.setRemoteDescription(
              new RTCSessionDescription({
                type: 'answer',
                sdp: answers?.message,
              }),
            );
            console.log('원격 설명 설정 완료');
          }

          if (pendingCandidates.current.length > 0) {
            console.log(`${pendingCandidates.current.length}개의 대기 중인 후보 처리 중`);

            for (const candidate of pendingCandidates.current) {
              try {
                if (pcRef.current) {
                  await pcRef.current.addIceCandidate(new RTCIceCandidate(candidate));
                  console.log('대기 중이던 ICE candidate 추가됨');
                }
              } catch (err) {
                console.error('대기 중이던 ICE candidate 추가 중 오류:', err);
              }
            }
            pendingCandidates.current = [];
          }
        } catch (err) {
          setStatusMessage(`Answer 처리 오류: ${err instanceof Error ? err.message : String(err)}`);
        }
      }

      // 새 사용자 참여 여부 확인 (audio, video가 false인 경우 새로 참여한 것으로 가정)
      if (user.audio === false && user.video === false && !joined) {
        setStatusMessage(`사용자가 방에 참여했습니다`);

        if (!joined) {
          await joinRoom();
        }
      }

      if (user.candidate) {
        console.log('ICE Candidate 수신:', user.candidate);
        try {
          if (pcRef.current) {
            await pcRef.current.addIceCandidate(new RTCIceCandidate(user.candidate));
            console.log('ICE Candidate 추가됨');
          }
        } catch (err) {
          console.error('ICE Candidate 추가 중 오류:', err);
        }
      }
    }
  };

  const handleIceCandidate = async (candidate: RTCIceCandidateInit) => {
    console.log('[handleIceCandidate] Candidate 메시지:', candidate);
    try {
      if (pcRef.current && pcRef.current.remoteDescription) {
        await pcRef.current.addIceCandidate(new RTCIceCandidate(candidate));
        console.log('ICE Candidate 추가됨');
      } else {
        console.log('원격 설명이 설정되지 않음, 후보 대기열에 추가');
        pendingCandidates.current.push(candidate);
      }
    } catch (err) {
      console.error('ICE Candidate 추가 중 오류:', err);
    }
  };

  const disconnectStomp = () => {
    if (client) {
      client.deactivate();
      setIsInVoiceChannel(false);
      console.log('🔌 STOMP WebSocket 연결 해제 시도');
    }
  };

  const createPeerConnection = useCallback(async () => {
    try {
      pcRef.current = new RTCPeerConnection({
        iceServers: [{ urls: 'stun:stun.l.google.com:19302' }],
      });

      // onicecandidate 이벤트: 수집된 ICE 후보를 시그널링 서버로 전송
      pcRef.current.onicecandidate = (event) => {
        if (event.candidate && token && client) {
          console.log('[pc] 생성된 ICE Candidate:', event.candidate.candidate);

          pendingCandidates.current.push(event.candidate);

          if (client && client.connected) {
            try {
              client.publish({
                destination: '/candidate',
                body: JSON.stringify({
                  type: MessageType.CANDIDATE,
                  data: {
                    room_id: roomId,
                    candidate: event.candidate.candidate,
                  },
                }),
              });
              console.log('ICE candidate 전송 성공');
            } catch (err) {
              console.error('ICE candidate 전송 오류:', err);
            }
          } else {
            console.log('STOMP 연결이 없어 candidate를 큐에 저장합니다');
          }
        } else {
          console.log('[pc] ICE Candidate 수집 완료');
        }
      };

      // 연결 상태 변경 이벤트
      pcRef.current.onconnectionstatechange = () => {
        console.log('[pc] onconnectionstatechange fired:', pcRef.current?.connectionState);
        console.log('[pc] ICE 연결 상태 변경:', pcRef.current?.iceConnectionState);
        setStatusMessage(`연결 상태: ${pcRef.current?.connectionState}`);

        if (pcRef.current?.connectionState === 'connected') {
          setIsInVoiceChannel(true);
          setStatusMessage('연결 성공! 화상 통화 중...');
        }
      };

      // 원격 트랙(상대방 미디어) 수신 시 비디오 태그에 설정
      pcRef.current.ontrack = (event) => {
        console.log('[pc] 원격 트랙 수신됨:', event);

        if (event.streams && event.streams.length > 0) {
          const remoteStream = event.streams[0];
          console.log('[pc] 원격 스트림:', remoteStream);
          console.log('[pc] 원격 스트림 트랙:', remoteStream.getTracks());

          // 추가
          remoteStream.getTracks().forEach((track) => {
            console.log(
              `[pc] 트랙 ID ${track.id}: 종류=${track.kind}, 활성화=${track.enabled}, 준비=${track.readyState}`,
            );

            // 트랙의 상태 변경 감지
            track.onended = () => console.log(`[pc] 트랙 ${track.id} 종료됨`);
            track.onmute = () => console.log(`[pc] 트랙 ${track.id} 음소거됨`);
            track.onunmute = () => console.log(`[pc] 트랙 ${track.id} 음소거 해제됨`);
          });

          if (remoteVideoRef.current) {
            console.log('[pc] 원격 비디오 요소에 스트림 설정');

            if (remoteVideoRef.current.srcObject) {
              console.log('[pc] 이전 스트림 정리');
              remoteVideoRef.current.srcObject = null;
            }

            remoteVideoRef.current.srcObject = remoteStream;
            remoteVideoRef.current.muted = false;

            console.log('[pc] 비디오 요소 준비 상태:', {
              videoWidth: remoteVideoRef.current.videoWidth,
              videoHeight: remoteVideoRef.current.videoHeight,
              readyState: remoteVideoRef.current.readyState,
              paused: remoteVideoRef.current.paused,
            });

            // 명시적으로 재생 시도 (비동기/동기 모두 시도)
            try {
              remoteVideoRef.current.play();
              console.log('[pc] 동기 재생 시도');
            } catch (e) {
              console.error('[pc] 동기 재생 실패:', e);
            }

            remoteVideoRef.current
              .play()
              .then(() => console.log('[pc] 비동기 재생 성공'))
              .catch((e) => {
                console.error('[pc] 비동기 재생 실패:', e);

                setStatusMessage('비디오 자동 재생 실패. 화면을 클릭하여 재생하세요.');
              });

            // 비디오 로딩 및 재생 확인을 위한 이벤트 리스너 추가
            remoteVideoRef.current.onloadedmetadata = () => {
              console.log('[pc] 원격 비디오 메타데이터 로드됨');
              remoteVideoRef.current
                ?.play()
                .then(() => console.log('[pc] 원격 비디오 재생 시작'))
                .catch((e) => console.error('[pc] 원격 비디오 재생 실패:', e));
            };

            // 추가 디버깅을 위한 이벤트 리스너
            remoteVideoRef.current.oncanplay = () => {
              console.log('[pc] 원격 비디오 재생 가능 상태');

              remoteVideoRef.current?.play().catch((e) => console.error('[pc] 재생 가능 상태에서 재생 실패:', e));
            };

            remoteVideoRef.current.onerror = (e) => {
              console.error('[pc] 원격 비디오 오류:', e);
            };
          } else {
            console.warn('[pc] 원격 비디오 요소가 없습니다');
          }
        } else {
          console.warn('[pc] 원격 스트림이 없습니다', event);
        }
      };

      const localStream = await navigator.mediaDevices.getUserMedia({
        video: true,
        audio: true,
      });

      localStreamRef.current = localStream;

      // 내 비디오 태그에 출력
      if (localVideoRef.current) {
        localVideoRef.current.srcObject = localStream;
      }

      setIsVideoOn(true);

      localStream.getTracks().forEach((track) => {
        pcRef.current?.addTrack(track, localStream);
      });

      console.log('[pc] RTCPeerConnection 및 로컬 미디어 설정 완료');
      return true;
    } catch (err) {
      console.error('PeerConnection 생성 또는 미디어 접근 중 오류:', err);
      setStatusMessage(`오류: ${err instanceof Error ? err.message : String(err)}`);
      return false;
    }
  }, [setIsInVoiceChannel, roomId, setIsVideoOn, token]);

  const playAllVideos = () => {
    console.log('모든 비디오 재생 시도');

    // 로컬 비디오 재생
    if (localVideoRef.current) {
      localVideoRef.current
        .play()
        .then(() => console.log('로컬 비디오 재생 성공'))
        .catch((e) => console.error('로컬 비디오 재생 실패:', e));
    }

    // 원격 비디오 재생
    if (remoteVideoRef.current) {
      remoteVideoRef.current
        .play()
        .then(() => console.log('원격 비디오 재생 성공'))
        .catch((e) => console.error('원격 비디오 재생 실패:', e));
    }

    // 화면 공유 비디오 재생
    if (isSharingScreen && screenShareRef.current) {
      screenShareRef.current
        .play()
        .then(() => console.log('화면 공유 비디오 재생 성공'))
        .catch((e) => console.error('화면 공유 비디오 재생 실패:', e));
    }
  };

  const joinRoom = async () => {
    if (!roomId.trim()) {
      setStatusMessage('방 ID를 입력하세요');
      return;
    }

    try {
      const response = await tokenAxios.post(`https://api.jungeunjipi.com/room/${roomId}/join`, {
        audio_enabled: isMicOn,
        media_enabled: isVideoOn,
        data_enabled: isSharingScreen,
      });

      if (response) {
        console.log(response);
        // handleSdpAnswer(response.sdp_answer);
        setIsInVoiceChannel(true);
      } else {
        console.error('참여 실패: ', response);
      }
    } catch (error) {
      console.error('API 요청 오류: ', error);
    }
  };

  const handleSdpAnswer = async (sdpAnswer: string) => {
    if (pcRef.current) {
      await pcRef.current.setRemoteDescription(
        new RTCSessionDescription({
          type: 'answer',
          sdp: sdpAnswer,
        }),
      );
      console.log('✅ SDP Answer 설정 완료');
    }
  };

  // 화면 공유 시작
  const startScreenShare = async () => {
    try {
      const screenStream = await navigator.mediaDevices.getDisplayMedia({
        video: true,
        audio: false,
      });

      screenStreamRef.current = screenStream;

      // 화면 공유 비디오 요소에 스트림 설정
      if (screenShareRef.current) {
        // 먼저 이전 스트림 정리
        if (screenShareRef.current.srcObject) {
          screenShareRef.current.srcObject = null;
        }

        // 새 스트림 설정
        screenShareRef.current.srcObject = screenStream;

        // 수동으로 재생 시도
        screenShareRef.current.play().catch((err) => {
          console.error('화면 공유 비디오 재생 실패:', err);
        });
      } else {
        console.error('화면 공유 비디오 요소를 찾을 수 없음');
      }

      // 화면 공유 종료 이벤트 리스너
      screenStream.getVideoTracks()[0].onended = () => {
        stopScreenShare();
      };

      // WebRTC 연결이 존재하는 경우 트랙 교체 (p2p 연결용)
      if (pcRef.current) {
        const screenVideoTrack = screenStream.getVideoTracks()[0];
        screenTrackRef.current = screenVideoTrack;

        const sender = pcRef.current.getSenders().find((s) => s.track?.kind === 'video');

        if (sender) {
          try {
            await sender.replaceTrack(screenVideoTrack);
          } catch (err) {
            console.error('트랙 교체 실패:', err);
          }
        } else {
          try {
            pcRef.current.addTrack(screenVideoTrack, screenStream);
          } catch (err) {
            console.error('트랙 추가 실패:', err);
          }
        }
      }

      setIsSharingScreen(true);
      setStatusMessage('화면 공유 중...');
    } catch (error) {
      console.error('화면 공유 시작 중 오류:', error);
      setStatusMessage(`화면 공유 시작 중 오류: ${error instanceof Error ? error.message : String(error)}`);
    }
  };

  // 화면 공유 중지
  const stopScreenShare = async () => {
    if (screenStreamRef.current) {
      // 모든 화면 공유 트랙 중지
      screenStreamRef.current.getTracks().forEach((track) => track.stop());

      // 화면 공유 비디오 초기화
      if (screenShareRef.current) {
        screenShareRef.current.srcObject = null;
      }

      // 다시 로컬 카메라 비디오로 돌아가기
      if (pcRef.current && localStreamRef.current) {
        const videoTrack = localStreamRef.current.getVideoTracks()[0];
        const sender = pcRef.current.getSenders().find((s) => s.track?.kind === 'video');

        if (sender && videoTrack) {
          sender.replaceTrack(videoTrack);
        }

        // 오디오 트랙도 원래대로 복구
        const audioTrack = localStreamRef.current.getAudioTracks()[0];
        if (audioTrack) {
          const audioSender = pcRef.current.getSenders().find((s) => s.track?.kind === 'audio');
          if (audioSender) {
            audioSender.replaceTrack(audioTrack);
          }
        }
      }

      screenStreamRef.current = null;
      screenTrackRef.current = null;
      setIsSharingScreen(false);
      setStatusMessage('화면 공유 종료됨');
    }
  };

  // 마이크 음소거/해제
  const toggleAudio = () => {
    if (localStreamRef.current) {
      const audioTracks = localStreamRef.current.getAudioTracks();
      const newAudioState = !isMicOn;

      audioTracks.forEach((track) => {
        track.enabled = newAudioState;
      });

      if (token && isConnected && client) {
        client.publish({
          destination: '/toggle',
          body: JSON.stringify({
            type: MessageType.AUDIO,
            data: {
              room_id: roomId,
              enabled: newAudioState,
            },
          }),
        });
      }

      setIsMicOn(newAudioState);
      setStatusMessage(`마이크 ${newAudioState ? '활성화됨' : '음소거됨'}`);
    }
  };

  // 비디오 켜기/끄기
  const toggleVideo = () => {
    if (localStreamRef.current) {
      const videoTracks = localStreamRef.current.getVideoTracks();
      const newVideoState = !isVideoOn;

      videoTracks.forEach((track) => {
        track.enabled = newVideoState;
      });

      if (token && isConnected && client) {
        client.publish({
          destination: '/toggle',
          body: JSON.stringify({
            type: MessageType.MEDIA,
            data: {
              room_id: roomId,
              enabled: newVideoState,
            },
          }),
        });
      }

      setIsVideoOn(newVideoState);
      setStatusMessage(`비디오 ${!isVideoOn ? '활성화됨' : '비활성화됨'}`);
    }
  };

  // 통화 종료
  const hangUp = async () => {
    setStatusMessage('통화 종료 중...');

    try {
      const response = await tokenAxios(`https://api.jungeunjipi.com/room/${roomId}/leave`);

      if (!response) {
        console.error('방 나가기 실패: ', response);
        return;
      }
      // 로컬 비디오 스트림 정리
      if (localStreamRef.current) {
        localStreamRef.current.getTracks().forEach((track) => track.stop());
        localStreamRef.current = null;
      }

      // 비디오 요소 초기화
      if (localVideoRef.current?.srcObject) {
        localVideoRef.current.srcObject = null;
      }
      if (remoteVideoRef.current?.srcObject) {
        remoteVideoRef.current.srcObject = null;
      }

      setJoined(false);
      setIsInVoiceChannel(false);

      setStatusMessage('통화가 종료되었습니다.');
    } catch (error) {
      console.error('API 요청 오류', error);
    }
  };

  return (
    <div style={{ padding: '1rem' }} onClick={playAllVideos}>
      <h2>테스트 페이지</h2>
      <div>
        <input type="text" value={roomId} onChange={(e) => setRoomId(e.target.value)} placeholder="Enter Room ID" />
        <button onClick={joinRoom} disabled={joined}>
          방 참여
        </button>
        <button onClick={toggleAudio}>{isMicOn ? '마이크 끄기' : '마이크 켜기'}</button>
        <button onClick={toggleVideo}>{isVideoOn ? '카메라 끄기' : '카메라 켜기'}</button>
        <button onClick={isSharingScreen ? stopScreenShare : startScreenShare} disabled={!joined}>
          {isSharingScreen ? '화면 공유 중지' : '화면 공유 시작'}
        </button>
        <button onClick={hangUp}>통화 종료</button>
      </div>

      <div style={{ marginTop: '1rem' }}>
        <p>
          <strong>상태:</strong> {statusMessage}
        </p>
      </div>

      <div style={{ display: 'flex', flexWrap: 'wrap', marginTop: '1rem', gap: '1rem' }}>
        <div>
          <h3>내 비디오</h3>
          <video
            ref={localVideoRef}
            autoPlay
            playsInline
            style={{ width: '320px', height: '240px', background: '#ccc' }}
          />
        </div>
        <div>
          <h3>상대방 비디오</h3>
          <video
            ref={remoteVideoRef}
            autoPlay
            playsInline
            muted={false}
            controls
            onClick={() => {
              if (remoteVideoRef.current) {
                remoteVideoRef.current
                  .play()
                  .then(() => console.log('클릭으로 비디오 재생 시작'))
                  .catch((e) => console.error('클릭 재생 실패:', e));
              }
            }}
            style={{
              width: '320px',
              height: '240px',
              background: '#333',
              border: '1px solid #666',
            }}
          />
        </div>
        {isSharingScreen && (
          <div>
            <h3>화면 공유</h3>
            <video
              ref={screenShareRef}
              autoPlay
              playsInline
              style={{
                width: '320px',
                height: '240px',
                background: '#333',
                border: '2px solid red',
              }}
            />
          </div>
        )}
      </div>
    </div>
  );
};

export default VideoTest;
