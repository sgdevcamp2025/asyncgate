//
//  GuildCategoryViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

class GuildCategoryViewModel: ObservableObject {
    @Published var name: String = "새로운 카테고리"
    @Published var isPrivate: Bool = false
    @Published var guildId: String?
    
    @Published var isCreatedCategory: Bool = false
    @Published var isRefreshing: Bool = false
    
    @Published var errorMessage: String?
    
    func reset() {
        self.name = "새로운 카테고리"
        self.isPrivate = false
        self.guildId = nil
        self.isCreatedCategory = false
        self.errorMessage = nil
    }
    
    func createCategory() {
        if let guildId = guildId {
            CategoryGuildServiceAPIManager.shared.createGuildCategory(name: name, guildId: guildId, isPrivate: isPrivate) {
                result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        self.isCreatedCategory = true
                        self.isRefreshing = true
                    }
                    self.reset()
                    
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
