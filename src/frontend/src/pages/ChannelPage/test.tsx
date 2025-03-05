/* eslint-disable @typescript-eslint/no-explicit-any */
import { useRef, useState, useEffect, useCallback } from 'react';

import { useChannelActionStore } from '@/stores/channelAction';
import { tokenAxios } from '@/utils/axios';

const SERVER_URL = import.meta.env.VITE_SIGNALING;
enum MessageType {
  JOIN = 'join',
  USER_JOINED = 'user-joined',
  OFFER = 'offer',
  ANSWER = 'answer',
  CANDIDATE = 'candidate',
  EXIT = 'exit',
  AUDIO = 'AUDIO',
  MEDIA = 'MEDIA',
  DATA = 'DATA',
}

// 메시지 인터페이스
interface WebSocketMessage {
  type: MessageType;
  data: any;
  token: string;
}

const VideoTest = () => {
  const localVideoRef = useRef<HTMLVideoElement>(null);
  const remoteVideoRef = useRef<HTMLVideoElement>(null);
  const screenShareRef = useRef<HTMLVideoElement>(null);

  const pcRef = useRef<RTCPeerConnection | null>(null);
  const wsRef = useRef<WebSocket | null>(null);
  const localStreamRef = useRef<MediaStream | null>(null);
  const screenStreamRef = useRef<MediaStream | null>(null);
  const screenTrackRef = useRef<MediaStreamTrack | null>(null);

  const [roomId, setRoomId] = useState('');
  const [joined, setJoined] = useState(false);
  const [statusMessage, setStatusMessage] = useState('');

  const { isSharingScreen, isVideoOn, isMicOn, setIsInVoiceChannel, setIsSharingScreen, setIsVideoOn, setIsMicOn } =
    useChannelActionStore();

  const token = localStorage.getItem('access_token');

  useEffect(() => {
    const connectWebSocket = () => {
      if (token) {
        try {
          const ws = new WebSocket(SERVER_URL);
          wsRef.current = ws;

          ws.onopen = () => {
            console.log('[ws] 연결됨');
            setStatusMessage('시그널링 서버에 연결됨');
          };

          ws.onmessage = (event) => {
            try {
              const message: WebSocketMessage = JSON.parse(event.data);
              handleWebSocketMessage(message);
            } catch (error) {
              console.error('[ws] 메시지 파싱 오류:', error);
            }
          };

          ws.onerror = (error) => {
            console.error('[ws] 에러 발생:', error);
            setStatusMessage('WebSocket 오류가 발생했습니다');
          };

          ws.onclose = () => {
            console.log('[ws] 연결 종료됨');
            setStatusMessage('서버 연결이 종료되었습니다');

            setTimeout(() => {
              connectWebSocket();
            }, 2000);
          };
        } catch (error) {
          console.error('[ws] 연결 생성 중 오류:', error);
          setStatusMessage(`서버 연결 오류: ${error instanceof Error ? error.message : String(error)}`);
        }
      }
    };

    connectWebSocket();

    return () => {
      // 미디어 스트림, PeerConnection, WebSocket 종료
      if (localStreamRef.current) {
        localStreamRef.current.getTracks().forEach((track) => track.stop());
      }

      if (screenStreamRef.current) {
        screenStreamRef.current.getTracks().forEach((track) => track.stop());
      }

      if (pcRef.current) {
        pcRef.current.close();
      }

      if (wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
        wsRef.current.close();
      }
    };
  }, []);

  const pendingCandidates = useRef<RTCIceCandidateInit[]>([]);

  // WebSocket 메시지 처리 함수
  const handleWebSocketMessage = async (message: any) => {
    console.log('수신된 메시지', message);

    if (message.type === 'candidate' && message.candidate) {
      console.log('[handleWebSocketMessage] Candidate 메시지:', message.candidate);
      try {
        if (pcRef.current && pcRef.current.remoteDescription) {
          await pcRef.current.addIceCandidate(new RTCIceCandidate(message.candidate));
          console.log('ICE Candidate 추가됨');
        } else {
          // remoteDescription이 아직 설정되지 않았으면 후보를 대기열에 추가
          console.log('원격 설명이 설정되지 않음, 후보 대기열에 추가');
          pendingCandidates.current.push(message.candidate);
        }
      } catch (err) {
        console.error('ICE Candidate 추가 중 오류:', err);
      }
      return;
    }

    if (message.type === 'response' && message.users && message.users.length > 0) {
      const user = message.users[0];

      console.log('메시지정보', message);
      console.log('사용자정보', message.users);

      if (user.sdpOffer) {
        // 사용자가 sdpOffer를 보냈는지 확인
        setStatusMessage('Offer 수신되었습니다. 응답 중...');

        if (!pcRef.current) {
          await createPeerConnection();
        }

        if (pcRef.current) {
          try {
            await pcRef.current.setRemoteDescription(
              new RTCSessionDescription({
                type: 'offer',
                sdp: user.sdpOffer,
              }),
            );

            // 이거 type이 response에서 하는게 맞나?
            if (pendingCandidates.current.length > 0) {
              console.log(`${pendingCandidates.current.length}개의 대기 중인 후보 처리 중`);

              for (const candidate of pendingCandidates.current) {
                try {
                  await pcRef.current.addIceCandidate(new RTCIceCandidate(candidate));
                  console.log('대기 중이던 ICE candidate 추가됨');
                } catch (err) {
                  console.error('대기 중이던 ICE candidate 추가 중 오류:', err);
                }
              }
              pendingCandidates.current = [];
            }

            const answer = await pcRef.current.createAnswer();
            await pcRef.current.setLocalDescription(answer);

            if (token) {
              sendWebSocketMessage(
                MessageType.ANSWER,
                {
                  sdpAnswer: answer.sdp,
                  roomId,
                },
                token,
              );
            }

            setStatusMessage('응답을 보냈습니다. 연결 중...');
          } catch (err) {
            console.error('Offer 처리 중 오류:', err);
            setStatusMessage(`Offer 처리 오류: ${err instanceof Error ? err.message : String(err)}`);
          }
        }
      }

      // 사용자가 sdpAnswer를 보냈는지 확인
      if (user.sdpAnswer) {
        setStatusMessage('응답을 받았습니다. 연결 중...');

        try {
          if (pcRef.current) {
            await pcRef.current.setRemoteDescription(
              new RTCSessionDescription({
                type: 'answer',
                sdp: user.sdpAnswer,
              }),
            );
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
            pendingCandidates.current = []; // 처리 후 배열 비우기
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

  // WebSocket 메시지 전송 헬퍼 함수
  const sendWebSocketMessage = (type: MessageType, data: any, token: string) => {
    if (wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
      const message: WebSocketMessage = { type, data, token };
      wsRef.current.send(JSON.stringify(message));
    } else {
      console.error('[ws] WebSocket이 열려있지 않아 메시지를 보낼 수 없습니다');
      setStatusMessage('서버에 연결되어 있지 않습니다');
    }
  };

  const createPeerConnection = useCallback(async () => {
    try {
      pcRef.current = new RTCPeerConnection({
        iceServers: [{ urls: 'stun:stun.l.google.com:19302' }],
      });

      console.log('[pc] PeerConnection 구성됨:', pcRef.current);

      // ICE 후보 수집 상태 모니터링
      pcRef.current.onicegatheringstatechange = () => {
        console.log('[pc] ICE 수집 상태:', pcRef.current?.iceGatheringState);

        // 수집 완료 시 로그
        if (pcRef.current?.iceGatheringState === 'complete') {
          console.log('[pc] ICE 후보 수집 완료');
        }
      };

      pcRef.current.oniceconnectionstatechange = () => {
        const state = pcRef.current?.iceConnectionState;
        console.log('[pc] ICE 연결 상태 변경:', state);

        // ICE 연결 실패 시 처리
        if (state === 'failed' || state === 'disconnected') {
          console.log('[pc] ICE 연결 문제 발생, 재연결 시도...');

          // 연결이 끊어진 경우 재연결 시도
          if (pcRef.current) {
            // ICE 재시작 오퍼 생성
            pcRef.current
              .createOffer({
                iceRestart: true,
                offerToReceiveAudio: true,
                offerToReceiveVideo: true,
              })
              .then((offer) => {
                return pcRef.current?.setLocalDescription(offer);
              })
              .then(() => {
                // 새 오퍼를 서버로 전송
                if (token && pcRef.current?.localDescription?.sdp) {
                  sendWebSocketMessage(
                    MessageType.OFFER,
                    {
                      sdpOffer: pcRef.current.localDescription.sdp,
                      roomId,
                      iceRestart: true,
                    },
                    token,
                  );
                  console.log('[pc] ICE 재시작 오퍼 전송됨');
                }
              })
              .catch((err) => {
                console.error('[pc] ICE 재시작 실패:', err);
              });
          }
        }

        // ICE 연결 성공 시
        if (state === 'connected' || state === 'completed') {
          console.log('[pc] ICE 연결 성공!');
          setStatusMessage('ICE 연결 성공! 화상 통화 진행 중...');
        }
      };

      // onicecandidate 이벤트: 수집된 ICE 후보를 시그널링 서버로 전송
      pcRef.current.onicecandidate = (event) => {
        if (event.candidate && token) {
          console.log('[pc] 생성된 ICE Candidate:', event.candidate);

          sendWebSocketMessage(
            MessageType.CANDIDATE,
            {
              candidate: event.candidate,
              roomId,
            },
            token,
          );
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
    setStatusMessage('방 참여 중...');

    if (wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
      if (!pcRef.current) {
        const success = await createPeerConnection();
        if (!success) {
          setStatusMessage('미디어 장치 접근 실패');
          return;
        }
      }

      if (roomId) {
        const response = await tokenAxios.post(`https://api.jungeunjipi.com/room/${roomId}/join`, {
          audio_enabled: isMicOn,
          media_enabled: isVideoOn,
          data_enabled: isSharingScreen,
        });

        if (response) {
          if (pcRef.current) {
            // 타임아웃을 설정하여 ICE 후보 수집에 충분한 시간 부여
            setTimeout(async () => {
              try {
                const offer = await pcRef.current!.createOffer({
                  offerToReceiveAudio: true,
                  offerToReceiveVideo: true,
                });

                await pcRef.current!.setLocalDescription(offer);

                // SDP 제안을 보내기 전에 잠시 대기하여 ICE 후보 수집이 일부 완료되도록 함
                setTimeout(() => {
                  if (token && pcRef.current?.localDescription) {
                    sendWebSocketMessage(
                      MessageType.OFFER,
                      {
                        sdpOffer: pcRef.current.localDescription.sdp,
                        roomId,
                      },
                      token,
                    );
                  }

                  setJoined(true);
                  setStatusMessage(`방 ${roomId}에 참여함. 응답 대기 중...`);
                }, 1000);
              } catch (error) {
                console.error('Offer 생성 중 오류:', error);
                setStatusMessage(`Offer 생성 오류: ${error instanceof Error ? error.message : String(error)}`);
              }
            }, 500);
          }
        } else {
          alert('방 참가 실패');
        }
      }
    } else {
      setStatusMessage('소켓 연결이 없습니다. 페이지를 새로고침하세요.');
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

      if (token && wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
        sendWebSocketMessage(
          MessageType.DATA,
          {
            roomId,
            enabled: true,
          },
          token,
        );
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

      if (token && wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
        sendWebSocketMessage(
          MessageType.DATA,
          {
            roomId,
            enabled: false,
          },
          token,
        );
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

      if (token && wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
        sendWebSocketMessage(
          MessageType.AUDIO,
          {
            roomId,
            enabled: newAudioState,
          },
          token,
        );
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

      if (token && wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
        sendWebSocketMessage(
          MessageType.MEDIA,
          {
            roomId,
            enabled: newVideoState,
          },
          token,
        );
      }

      setIsVideoOn(newVideoState);
      setStatusMessage(`비디오 ${!isVideoOn ? '활성화됨' : '비활성화됨'}`);
    }
  };

  // 통화 종료
  const hangUp = () => {
    setStatusMessage('통화 종료 중...');

    if (token && wsRef.current && wsRef.current.readyState === WebSocket.OPEN) {
      sendWebSocketMessage(
        MessageType.EXIT,
        {
          roomId,
        },
        token,
      );
    }

    if (pcRef.current) {
      pcRef.current.close();
      pcRef.current = null;
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
  };

  useEffect(() => {
    if (isSharingScreen && screenShareRef.current && screenStreamRef.current) {
      screenShareRef.current.srcObject = screenStreamRef.current;

      screenShareRef.current.play().catch((err) => console.error('useEffect에서 비디오 재생 오류:', err));
    }
  }, [isSharingScreen]);

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
