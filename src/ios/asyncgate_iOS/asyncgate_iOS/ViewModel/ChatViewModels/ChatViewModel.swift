//
//  ChatViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 3/9/25.
//

import SwiftUI

class ChatViewModel: ObservableObject {
    @Published var messages: [ChatMessage] = []
    @Published var channelId: String = ""
    
    func getMessages() {
        ChattingManager.shared.getChattingList(page: 10, size: 10, channelId: "7e27f7c8-581d-4001-a684-a58e953b3e26") { result in
            switch result {
            case .success(let value):
                value.result.directMessages.forEach { oneMessage in
                    print("oneMessage \(oneMessage)")
                    DispatchQueue.main.async {
                        self.messages.append(oneMessage.toChatMeassage())
                        print("susususususus")
                    }
                }
                
            case .failure(let error):
                print("ChatViewModel - getMessages() - 에러 발생 \(error)")
            }
        }
    }
}
