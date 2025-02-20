//
//  GuildChannelViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

class GuildChannelViewModel: ObservableObject {
    @Published var name: String = "새로운 채널"
    @Published var guildId: String = ""
    @Published var categoryId: String = ""
    @Published var channelType: String = "TEXT"
    @Published var isPrivate: Bool = false
    
    @Published var isCreatedChannel: Bool = false
    
    @Published var errorMessage: String?
    
    func reset() {
        self.name = "새로운 채널"
        self.guildId = ""
        self.categoryId = ""
        self.channelType = "TEXT"
        self.isPrivate = false
    }
    
    func createChannel() {
        ChannelGuildServiceAPIManager.shared.createGuildChannel(name: name, guildId: guildId, categoryId: categoryId, channelType: channelType, isPrivate: isPrivate) {
                result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        self.isCreatedChannel = true
                    }
                    self.reset()
                    
                case .failure(let errorResponse):
                    DispatchQueue.main.async {
                        self.isCreatedChannel = false
                        self.errorMessage = errorResponse.localizedDescription
                    }
                    print("GuildChannelViewModel - createChannel() - 에러 발생: \(errorResponse)")
                }
            }
    }
}
