//
//  WedSocketManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 3/9/25.
//

import Starscream

class WedSocketManager: ObservableObject, WebSocketDelegate {
    
    private var socket: WebSocket?
    
    @Published var messages: [ChatMessage] = []
    
    private let accessTokenViewModel = AccessTokenViewModel.shared
    
    // 기본 웹소켓 설정
    init() {
        connect()
    }
    
    // Websocket 연결 시작
    func connect() {
        if let accessToken = accessTokenViewModel.accessToken {
            guard let url = URL(string: "") else {
                return
            }
            
            var request = URLRequest(url: url)
            request.addValue("Bearer \(accessToken)", forHTTPHeaderField: "Authorization")
            request.addValue(accessToken, forHTTPHeaderField: "Sec-WebSocket-Protocol")
            request.timeoutInterval = 5
            
            self.socket = WebSocket(request: request)
            self.socket?.delegate = self
            self.socket?.connect()
        }
    }
    
    // Websocket 연결 종료
    func disconnect() {
        self.socket?.disconnect()

    }
    
    func didConnect(socket: WebSocketClient) {
        let connectMessage = """
        CONNECT
        accept-version:1.0
        heart-beat:10000,10000
        """
        socket.write(string: connectMessage)
    }
    
    // 서버에서 받기
    func didReceive(event: WebSocketEvent, client: WebSocketClient) {
        switch event {
        case .connected(let headers):
            print("웹소켓 연결 성공. 헤더 \(headers)")
        case .disconnected(let reason, let code):
            print("웹소켓 연결 종료 \(reason), code: \(code)")
        case .text(let string):
            print("서버에서 받은 텍스트 메시지: \(string)")
        case .binary(let data):
            print("바이너리 데이터: \(data)")
        case .ping(_):
            print("웹소켓 핑")
            break
        case .pong(_):
            print("웹소켓 퐁")
            break
        case .viabilityChanged(let result):
            if result {
                print("웹소켓 연결 안정적? \(result)")
            } else {
                print("다시 다시 재연결 시도 중...")
                socket?.connect()
            }
            break
        case .reconnectSuggested(let result):
            print("웹소켓 재연결 필요? \(result)")
            if result {
                print("재연결 시도 중...")
                socket?.connect()
            }
            break
        case .cancelled:
            print("웹소켓이 강제로 종료됨")
            break
        case .peerClosed:
            print("서버에서 웹소켓 닫음")
            break
        case .error(let error):
                if let error = error {
                    print("웹소켓 도중 에러 발생: \(error.localizedDescription)")
                    
                    if let upgradeError = error as? Starscream.HTTPUpgradeError {
                        // HTTPUpgradeError에서 발생한 에러를 더 자세히 출력
                        print("업그레이드 에러 발생: \(upgradeError.localizedDescription)")
                       
                    }
                    
                    if let urlError = error as? URLError {
                        print("URL 오류 코드: \(urlError.code)")
                        print("URL 오류 설명: \(urlError.localizedDescription)")
                    }
                } else {
                    print("웹소켓 도중 알 수 없는 오류 발생")
                }
        }
    }
    
    // 서버로 현재 입력 상태인지 보내기
    func sendTyping(channelId: String, name: String, content: String) {
        if let accessToken = accessTokenViewModel.accessToken {
            let directTyping: [String: Any] = [
                "channelId": channelId,
                "name": name,
                "content": content
            ]
            
            guard let directTypingJsonData = try? JSONSerialization.data(withJSONObject: directTyping), let jsonString = String(data: directTypingJsonData, encoding: .utf8) else { return }
            
            let stompMessage = """
            SEND
            destination:/kafka/chat/direct/typing
            
            \(jsonString)
            """
            
            socket?.write(string: stompMessage)
        }
    }
    
    // Websocket 연결 끊어짐
    func didDisconnect(error: Error?, socket: WebSocketClient) {
        print("웹소켓 연결 끊어짐")
    }
}
