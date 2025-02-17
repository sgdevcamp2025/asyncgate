//
//  SignUpResponse.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Foundation

// MARK: 응답 - 회원가입 진행 후 성공 시 받아올 응답
struct SignUpResponse: Decodable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: EmptyResponse
}

// MARK: 에러 응답 - 회원가입 진행 후 에러 발생 시 받아올 응답
struct SignUpErrorResponse: Decodable, Error {
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

// MARK: 응답 - 이메일 중복 확인 후 받을 응답
struct CheckDuplicatedEmailResponse: Decodable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: DuplicateEmailResponse
}

// 이메일 중복 여부를 받아옴
struct DuplicateEmailResponse: Decodable {
    let isDuplicate: Bool
    
    enum CodingKeys: String, CodingKey {
        case isDuplicate = "is_duplicate"
    }
}

// 빈 배열의 형태로 받아오므로 빈 상태로 선언
struct EmptyResponse: Decodable {
}
