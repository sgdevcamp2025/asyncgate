//
//  UserInfoViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/17/25.
//

import SwiftUI

// MARK: ViewModel - 유저 정보 수정
class UserInfoViewModel: ObservableObject {
    // Request 변수
    @Published var name: String = ""
    @Published var nickName: String = ""
    @Published var profileImage: String = ""
    
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
}
