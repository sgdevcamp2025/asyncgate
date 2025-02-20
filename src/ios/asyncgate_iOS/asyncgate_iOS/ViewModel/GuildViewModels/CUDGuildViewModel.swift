//
//  CUDGuildViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

// MARK: ViewModel - 길드 CUD
class CUDGuildViewModel: ObservableObject {
    // Request 변수
    @Published var name: String = ""
    @Published var isPrivate: Bool = true
    @Published var profileImage: UIImage?
    
    @Published var errorMessage: String?

    @Published var isNeedRefresh: Bool = false
    @Published var isRefreshing: Bool = false
    
    @Published var guildId: String = ""
    
    // 변수 초기화
    func reset() {
        self.name = ""
        self.isPrivate = true
        self.profileImage = nil
        self.errorMessage = nil
        self.isNeedRefresh = false
    }
    
    // MARK: 함수 - 길드 생성하기
    func createGuild() {
        GuildServiceAPIManager.shared.createGuild(name: name, isPrivate: isPrivate, profileImage: profileImage) { result in
            switch result {
            case .success(let successResponse):
                DispatchQueue.main.async {
                    self.errorMessage = nil
                    self.isNeedRefresh = true
                    self.isRefreshing = true
                }
                self.reset()
                
                print("CreateGuildViewModel - createGuild() - 길드 생성 성공 \(successResponse)")
                
            case .failure(let errorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = "길드를 생성하지 못했습니다."
                }
                print("CreateGuildViewModel - createGuild() - 에러 발생 \(errorResponse)")
            }
        }
    }
    
    // MARK: 함수 - 길드 수정하기
    func patchGuild() {
        GuildServiceAPIManager.shared.updateGuild(guildId: guildId, name: name, isPrivate: isPrivate, profileImage: profileImage) { result in
            switch result {
            case .success(let successResponse):
                DispatchQueue.main.async {
                    self.errorMessage = nil
                    self.isNeedRefresh = true
                }
                self.reset()
                
                print("CreateGuildViewModel - patchGuild() - 길드 수정 성공 \(successResponse)")
                
            case .failure(let errorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = "길드 수정을 실패했습니다."
                }
                print("CreateGuildViewModel - patchGuild() - 에러 발생 \(errorResponse)")
            }
        }
    }
    
    // MARK: 함수 - 길드 삭제하기
    func deleteGuildDetail() {
        GuildServiceAPIManager.shared.deleteGuild(guildId: guildId) { result in
            switch result {
            case .success(_):
                DispatchQueue.main.async {
                    self.isNeedRefresh = true
                }
                self.reset()
                print("GuildDetailViewModel - deleteGuildDetail() - 길드 삭제 성공")
                
            case .failure(let errorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = errorResponse.localizedDescription
                }
                print("GuildDetailViewModel - deleteGuildDetail() - 에러 발생: \(errorResponse)")
            }
        }
    }
}
