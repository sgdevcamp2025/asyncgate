//
//  GuildChannelViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

// MARK: ViewModel - 길드 채널
class GuildChannelViewModel: ObservableObject {
    @Published var name: String = "새로운 채널"
    @Published var guildId: String = ""
    @Published var categoryId: String = ""
    @Published var channelType: String = "TEXT"
    @Published var isPrivate: Bool = false
    
    @Published var channelId: String = ""
    @Published var topic: String = ""
    
    @Published var isNeedRefresh: Bool = false
    
    @Published var errorMessage: String?
    
    func reset() {
        self.name = "새로운 채널"
        self.guildId = ""
        self.categoryId = ""
        self.channelType = "TEXT"
        self.isPrivate = false
        self.isNeedRefresh = false
        self.channelId = ""
        self.topic = ""
    }
    
    init() {
        self.categoryId = "2715874a-858d-4cfb-b169-eadf8a223062"
        self.guildId = ""
        self.channelId = "1c71b065-f497-46d6-8416-80f8e636769e"
        self.topic = "test"
        self.name = "스윗"
    }
    
    // MARK: 함수 - 새로운 채널 생성
    func createChannel() {
        ChannelGuildServiceAPIManager.shared.createGuildChannel(name: name, guildId: guildId, categoryId: categoryId, channelType: channelType, isPrivate: isPrivate) {
                result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        self.isNeedRefresh = true
                    }
                    self.reset()
                    
                case .failure(let errorResponse):
                    DispatchQueue.main.async {
                        self.isNeedRefresh = false
                        self.errorMessage = errorResponse.localizedDescription
                    }
                    print("GuildChannelViewModel - createChannel() - 에러 발생: \(errorResponse)")
                }
            }
    }
    
    // MARK: 함수 - 채널 수정
    func updateChannel() {
        ChannelGuildServiceAPIManager.shared.updateGuildChannel(guildId: guildId, categoryId: categoryId, channelId: channelId, name: name, topic: topic, isPrivate: isPrivate) {
                result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        self.isNeedRefresh = true
                    }
                    self.reset()
                    
                case .failure(let errorResponse):
                    DispatchQueue.main.async {
                        self.errorMessage = errorResponse.localizedDescription
                    }
                    print("GuildChannelViewModel - updateChannel() - 에러 발생: \(errorResponse)")
                }
            }
    }
    
    // MARK: 함수 - 채널 삭제
    func deleteChannel() {
        ChannelGuildServiceAPIManager.shared.deleteGuildChannel(guildId: guildId, categoryId: categoryId, channelId: channelId) {
                result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        self.isNeedRefresh = true
                    }
                    self.reset()
                    
                case .failure(let errorResponse):
                    DispatchQueue.main.async {
                        self.errorMessage = errorResponse.localizedDescription
                    }
                    print("GuildChannelViewModel - deleteChannel() - 에러 발생: \(errorResponse)")
                }
            }
    }
}
