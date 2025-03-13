//
//  OnlyUserAPIResponse.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import Foundation

// MARK: ONLY USER API RESPONSE (USER API에서만 사용됨)

// MARK: 응답 - 엑세스 토큰을 받는 응답
struct SignInResponse: Decodable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: AccessTokenResponse?
}

// SignInResponse -> 엑세스 토큰을 받아옴
struct AccessTokenResponse: Decodable {
    let accessToken: String
    
    enum CodingKeys: String, CodingKey {
        case accessToken = "access_token"
    }
}

// MARK: 응답 - 이메일 중복 확인 후 받을 응답
struct CheckDuplicatedEmailResponse: Decodable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: DuplicateEmailResponse
}

// CheckDuplicatedEmailResponse -> 이메일 중복 여부를 받아옴
struct DuplicateEmailResponse: Decodable {
    let isDuplicate: Bool
    
    enum CodingKeys: String, CodingKey {
        case isDuplicate = "is_duplicate"
    }
}

