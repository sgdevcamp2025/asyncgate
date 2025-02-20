//
//  UserInfoViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/17/25.
//

import SwiftUI

// MARK: ViewModel - 유저 정보
class UserInfoViewModel: ObservableObject {
    // Request 변수
    @Published var name: String = ""
    @Published var nickName: String = ""
    @Published var profileImage: UIImage?
    
    // Response 변수
    @Published var errorMessage: String?
    @Published var isUpdateUserInfo: Bool = false
    
    // MARK: 함수 - 유저 정보 업데이트
    func updateUserInfos() {
        UserNetworkManager.shared.updateUserInfo(name: name, nickName: nickName, profileImage: profileImage) { result in
            switch result {
            case .success(_):
                DispatchQueue.main.async {
                    self.isUpdateUserInfo = true
                }
                
            case .failure(let errorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = errorResponse.error
                }
                print("UpdateUserInfoViewModel - updateUserInfos() error : \(errorResponse)")
            }
        }
    }
    
    // MARK: 함수 - 회원 탈퇴
    func deleteUserInfo() {
        UserNetworkManager.shared.deleteUser() { result in
            switch result {
            case .success(_):
                print("UpdateUserInfoViewModel - updateUserInfos() 탈퇴 성공")
                
            case .failure(let errorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = errorResponse.error
                }
                print("UpdateUserInfoViewModel - deleteUserInfo() error : \(errorResponse)")
            }
        }
    }
}
