//
//  ChatResponse.swift
//  asyncgate_iOS
//
//  Created by kdk on 3/9/25.
//

import Foundation

struct ChatResponse: Codable {
    let httpStatus: Int
    let message: String
    let time: String
    let result: ChatResult
}

struct ChatResult: Codable {
    let isFirst: Bool
    let isLast: Bool
    let totalCount: Int
    let totalPages: Int
    let currentPage: Int
    let pageSize: Int
    let directMessages: [DirectMessage]
}

struct DirectMessage: Codable {
    let id: String
    let channelId: String
    let userId: String
    let type: String
    let profileImage: String
    let name: String
    let content: String
    let thumbnail: String
    let parentId: String
    let parentName: String
    let parentContent: String
    let createdAt: String
    
    func toChatMeassage() -> ChatMessage {
        ChatMessage(
            channelId: self.channelId,
            userId: self.userId,
            profileImage: self.profileImage,
            name: self.name,
            content: self.content,
            createdAt: formattedCreatedAt()
        )
    }
    
    // 함수 - 채팅 생성 날짜를 형태에 맞게 변환 (값 전환 불가 시 기본 값)
    func formattedCreatedAt() -> String {
        let dateStringformatter = DateFormatter()
        dateStringformatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        dateStringformatter.locale = Locale(identifier: "ko_KR")
        dateStringformatter.timeZone = TimeZone(abbreviation: "UTC")
        
        if let date = dateStringformatter.date(from: createdAt) {
            let formatter = DateFormatter()
            formatter.dateFormat = "M/d/yy, HH:mm"
            formatter.locale = Locale(identifier: "ko_KR")
            
            return formatter.string(from: date)
        }
       
        return createdAt
    }
}

struct ChatMessage: Codable {
    let channelId: String
    let userId: String
    let profileImage: String
    let name: String
    let content: String
    let createdAt: String
}
