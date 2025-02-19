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
    
    @Published var errorMessage: String?
    
    func createCategory() {
        if let guildId = guildId {
            CategoryGuildServiceAPIManager.shared.createGuildCategory(name: name, guildId: guildId, isPrivate: isPrivate) {
                result in
                switch result {
                case .success(let susscessResponse):
                    DispatchQueue.main.async {
                        self.isCreatedCategory = true
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
