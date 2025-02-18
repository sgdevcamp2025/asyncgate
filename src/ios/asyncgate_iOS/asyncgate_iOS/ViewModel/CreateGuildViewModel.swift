//
//  CreateGuildViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

// MARK: ViewModel - 길드 생성
class CreateGuildViewModel: ObservableObject {
    // Request 변수
    @Published var name: String = ""
    @Published var isPrivate: Bool = true
    @Published var profileImage: UIImage?
    
    // MARK: 함수 - 길드 생성하기
    func createGuild() {
        GuildServiceAPIManager.shared.createGuild(name: name, isPrivate: isPrivate, profileImage: profileImage) { result in
            switch result {
            case .success(let successResponse):
                print("CreateGuildViewModel - createGuild() - 길드 생성 성공 \(successResponse)")
                
            case .failure(let errorResponse):
                print("CreateGuildViewModel - createGuild() - 에러 발생 \(errorResponse)")
            }
        }
    }
}
