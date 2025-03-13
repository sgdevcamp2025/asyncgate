//
//  Chat.swift
//  asyncgate_iOS
//
//  Created by kdk on 3/9/25.
//

import SwiftUI

struct ChatChatView: View {
    @StateObject var webSocketManager: WedSocketManager = WedSocketManager()
    @StateObject var chatchat: ChatViewModel = ChatViewModel()
    
    @State private var typingContent: String = ""
    
    var body: some View {
        VStack {
            Text("Messages:")
                .font(.title2)
                .padding(.top)
            
            Button("NEEEEEEEEE") {
                chatchat.getMessages()
            }
            
            HStack {
                TextField("Type a message", text: $typingContent)
                    .padding()
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                
                Button("Send Typing Status") {
                    webSocketManager.sendTyping(channelId: "7e27f7c8-581d-4001-a684-a58e953b3e26", name: "John Doe", content: typingContent)
                }
                .padding()
            }
        }
        .onAppear {
            webSocketManager.connect() // WebSocket 연결
            print("처음 연결 시도")
        }
        .onDisappear {
            webSocketManager.disconnect()
            print("해제됨")// WebSocket 연결 해제
        }
    }
}
