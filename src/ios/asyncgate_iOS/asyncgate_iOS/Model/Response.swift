//
//  Response.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Foundation

// MARK: 응답 - result가 String 타입인 응답
struct SuccessResultStringResponse: Decodable {
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

// MARK: 에러 응답 - 에러 발생 시 받아올 응답
struct FourErrorResponse: Decodable, Error {
    let timeStamp: String
    let status: Int
    let error: String
    let path: String
    
    enum CodingKeys: String, CodingKey {
        case timeStamp = "timestamp"
        case status
        case error
        case path
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
    let responses: [GuildInList]
}

// SuccessLoadGuildListResponse/LoadGuildListResponse -> 길드 한개
struct GuildInList: Codable, Hashable {
    let guildId: String
    let name: String
    let profileImageUrl: String?
}

// MARK: 응답 - 길드 단일 조회 시 받는 응답
struct GuildDetailResponse: Codable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: GuildDetailResponseResult
}

struct GuildDetailResponseResult: Codable {
    let guild: GuildInfo
    let categories: [GuildCategory]
    let channels: [GuildChannel]
}

struct GuildInfo: Codable, Hashable {
    let guildId: String
    let name: String
    let isPrivate: Bool
    let profileImageUrl: String?
}

struct GuildCategory: Codable, Hashable {
    let categoryId: String
    let name: String
    let isPrivate: Bool
}

struct GuildChannel: Codable, Hashable {
    let channelId: String
    let name: String
    let topic: String?
    let channelType: String
    let isPrivate: Bool
}

// MARK: 응답 - 카테고리 관련 응답
struct CategoryResponse: Codable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: CategoryInfo
}

struct CategoryInfo: Codable, Hashable {
    let categoryId: String
    let name: String
    let isPrivate: Bool
    let guildId: String
}

// MARK: 응답 - 채널 관련 응답
struct ChannelResponse: Codable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: ChannelInfo
}

struct ChannelInfo: Codable, Hashable {
    let channelId: String
    let name: String
    let topic: String?
    let isPrivate: Bool
    let guildId: String
    let categoryId: String
    let channelType: String
}
