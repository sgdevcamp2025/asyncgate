//
//  User.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Foundation

// MARK: Model - 회원가입한 User 정보를 담은 모델
struct User: Codable {
    let email: String
    let password: String
    let name: String
    let nickname: String
    let birth: String
}
