//
//  GuildListViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

// MARK: ViewModel - 내 길드 목록
class GuildListViewModel: ObservableObject {
    @Published var myGuildList: [Guild] = []
    
    @Published var errorMessage: String?
    
    // 더미 데이터 예시
    let dummyGuildData: [Guild] = [
        Guild(guildId: "guild-12345", name: "Knight's Order", profileImageUrl: "https://velog.velcdn.com/images/dbqls200/post/4f90cb9e-7ea9-43dd-9d28-ae3918f95a0d/image.png"),
        Guild(guildId: "guild-67890", name: "Warrior's Clan", profileImageUrl: nil),
        Guild(guildId: "guild-54321", name: "Mages Guild", profileImageUrl: nil)
    ]
    
    // 초기화하여 불러오기
    init() {
        dummyData()
        // fetchMyGuildList()
    }
    
    func dummyData() {
        DispatchQueue.main.async {
            self.myGuildList = self.dummyGuildData
        }
    }
    
    // MARK: 함수 - 내 길드 목록 조회하기
    func fetchMyGuildList() {
        GuildServiceAPIManager.shared.loadMyGuildList() { result in
            switch result {
            case .success(let successResponse):
                DispatchQueue.main.async {
                    self.myGuildList = successResponse.result.responses
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
