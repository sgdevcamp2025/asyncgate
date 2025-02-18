//
//  Response.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Foundation

// MARK: 응답 - 서버 연결 확인 응답
struct HealthResponse: Decodable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: String
}

// MARK: 응답 - 회원가입, 유저 정보 수정 등 가장 자주 사용되는 응답
struct SuccessEmptyResultResponse: Decodable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: EmptyResponse
}

// SuccessEmptyResultResponse -> 빈 배열의 형태로 받아오므로 빈 상태로 선언
struct EmptyResponse: Decodable {
}

// MARK: 에러 응답 - 에러 발생 시 받아올 응답
struct ErrorResponse: Decodable, Error {
    let timeStamp: String
    let path: String
    let status: Int
    let error: String
    let requestId: String
    
    enum CodingKeys: String, CodingKey {
        case timeStamp = "timestamp"
        case path
        case status
        case error
        case requestId
    }
}

// MARK: 에러 응답 - httpStatus만 받는 응답
struct OnlyHttpStatusResponse: Decodable, Error {
    let httpStatus: Int
}

// MARK: 응답 - 길드 생성 시 받는 응답
struct SuccessCreateGuildResponse: Codable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: CreateGuildResponse
}

// SuccessCreateGuildResponse -> 길드 정보
struct CreateGuildResponse: Codable {
    let guildId: String
    let name: String
    let isPrivate: Bool
    let profileImageUrl: String
}

// MARK: USER API RESPONSE (USER API에서만 사용됨)

// MARK: 응답 - 엑세스 토큰을 받는 응답
struct SignInResponse: Decodable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: AccessTokenResponse
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

