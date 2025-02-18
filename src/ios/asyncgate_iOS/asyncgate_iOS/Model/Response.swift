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

// MARK: 응답 - 내 길드 목록 조회 시 받는 응답
struct SuccessLoadGuildListResponse: Codable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: LoadGuildListResponse
}

// SuccessLoadGuildListResponse -> 길드 배열
struct LoadGuildListResponse: Codable {
    let responses: [Guild]
}

// SuccessLoadGuildListResponse/LoadGuildListResponse -> 길드 한개
struct Guild: Codable, Hashable {
    let guildId: String
    let name: String
    let profileImageUrl: String?
}
