//
//  GuildCategoryViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

// MARK: ViewModel - 길드 카테고리
class GuildCategoryViewModel: ObservableObject {
    @Published var name: String = "새로운 카테고리"
    @Published var isPrivate: Bool = false
    @Published var guildId: String?
    
    @Published var categoryId: String = ""
    
    @Published var isNeedRefresh: Bool = false
    @Published var isRefreshing: Bool = false
    
    @Published var errorMessage: String?
    
    // 초기화
    func reset() {
        self.name = "새로운 카테고리"
        self.isPrivate = false
        self.guildId = nil
        self.errorMessage = nil
        self.isNeedRefresh = false
        self.isRefreshing = false
    }
    
    // MARK: 함수 - 카테고리 생성
    func createCategory() {
        if let guildId = guildId {
            CategoryGuildServiceAPIManager.shared.createGuildCategory(name: name, guildId: guildId, isPrivate: isPrivate) {
                result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        self.isNeedRefresh = true
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
    
    // MARK: 함수 - 카테고리 삭제
    func deleteCategory() {
        print("guildId:::::::::::::: \(guildId)")
        if let guildId = guildId {
            CategoryGuildServiceAPIManager.shared.deleteGuildCategory(guildId: guildId, categoryId: categoryId) {
                result in
                switch result {
                case .success(_):
                    DispatchQueue.main.async {
                        self.isNeedRefresh = true
                        self.isRefreshing = true
                    }
                    self.reset()
                    
                case .failure(let errorResponse):
                    DispatchQueue.main.async {
                        self.errorMessage = errorResponse.localizedDescription
                    }
                    print("GuildDetailViewModel - deleteCategory() - 에러 발생: \(errorResponse)")
                }
            }
        }
    }
}
