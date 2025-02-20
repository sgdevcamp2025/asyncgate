//
//  GuildListViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

// MARK: ViewModel - 내 길드 목록
class GuildListViewModel: ObservableObject {
    @Published var myGuildList: [GuildInList] = []
    
    @Published var firstGuildId: String?
    
    @Published var errorMessage: String?
    
    // 더미 데이터 예시
//    let dummyGuildData: [GuildInList] = [
//        GuildInList(guildId: "guild-12345", name: "Knight's Order", profileImageUrl: nil),
//        GuildInList(guildId: "guild-67890", name: "Warrior's Clan", profileImageUrl: nil),
//        GuildInList(guildId: "guild-54321", name: "Mages Guild", profileImageUrl: nil)
//    ]
    
    // 초기화하여 불러오기
    init() {
//        self.myGuildList = self.dummyGuildData
        fetchMyGuildList()
    }
    
    // MARK: 함수 - 내 길드 목록 조회하기
    func fetchMyGuildList() {
        GuildServiceAPIManager.shared.loadMyGuildList() { result in
            switch result {
            case .success(let successResponse):
                DispatchQueue.main.async {
                    self.myGuildList = successResponse.result.responses
                    if let firstGuildId = self.myGuildList.first {
                        self.firstGuildId = firstGuildId.guildId
                    }
                }
                
            case .failure(let errorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = errorResponse.localizedDescription
                }
                print("GuildListViewModel - fetchMyGuildList() - 에러 발생 \(errorResponse)")
            }
        }
    }
}
