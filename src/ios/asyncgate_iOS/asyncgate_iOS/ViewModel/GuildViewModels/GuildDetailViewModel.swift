//
//  GuildDetailViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

class GuildDetailViewModel: ObservableObject {
    @Published var guild: GuildInfo?
    @Published var categories: [GuildCategory] = []
    @Published var channels: [GuildChannel] = []
    
    @Published var guildId: String?
    
    @Published var errorMessage: String?
    
    // MARK: 함수 - 길드 세부 정보 불러오기
    func fetchGuildDetail() {
        if let guildId = guildId {
            GuildServiceAPIManager.shared.fetchGuildInfo(guildId: guildId) { result in
                switch result {
                case .success(let susscessResponse):
                    DispatchQueue.main.async {
                        self.guild = susscessResponse.result.guild
                        self.categories = susscessResponse.result.categories
                        self.channels = susscessResponse.result.channels
                    }
                    
                case .failure(let errorResponse):
                    DispatchQueue.main.async {
                        self.errorMessage = errorResponse.localizedDescription
                    }
                    print("GuildDetailViewModel - fetchGuildDetail() - 에러 발생: \(errorResponse)")
                }
            }
        }
    }
}
