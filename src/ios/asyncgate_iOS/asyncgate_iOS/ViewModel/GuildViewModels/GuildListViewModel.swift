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
    
    init() {
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
