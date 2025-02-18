//
//  GuildDetailViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

class GuildDetailViewModel: ObservableObject {
    @Published var guild: GuildInfo?
    @Published var categories: [GuildCategory] = []
    @Published var channels: [GuildChannel] = []
    
    @Published var guildId: String?
    
    @Published var errorMessage: String?
    
    func reset() {
        self.guild = nil
        self.categories = []
        self.channels = []
    }
    
    // MARK: 더미 데이터
    let dummyGuildDetailResponse = GuildDetailResponse(
        httpStatus: 200,
        message: "Success",
        time: "2025-02-18T02:35:11.245Z",
        result: GuildDetailResponseResult(
            guild: GuildInfo(
                guildId: "guild-12345",
                name: "Knight's Order",
                isPrivate: false,
                profileImageUrl: "https://velog.velcdn.com/images/dbqls200/post/4f90cb9e-7ea9-43dd-9d28-ae3918f95a0d/image.png"
            ),
            categories: [
                GuildCategory(
                    categoryId: "category-67890",
                    name: "General Discussions",
                    isPrivate: false
                ),
                GuildCategory(
                    categoryId: "category-12345",
                    name: "Special Events",
                    isPrivate: false
                ),
                GuildCategory(
                    categoryId: "category-54321",
                    name: "Admin Announcements",
                    isPrivate: true
                )
            ],
            channels: [
                GuildChannel(
                    channelId: "channel-56789",
                    name: "General Chat",
                    topic: "This is a general discussion channel.",
                    channelType: "TEXT",
                    isPrivate: false
                ),
                GuildChannel(
                    channelId: "channel-98765",
                    name: "Event Planning",
                    topic: "Plan and discuss upcoming events.",
                    channelType: "TEXT",
                    isPrivate: false
                ),
                GuildChannel(
                    channelId: "channel-11223",
                    name: "Admin Chat",
                    topic: "Private channel for admin discussions.",
                    channelType: "TEXT",
                    isPrivate: true
                ),
                GuildChannel(
                    channelId: "channel-33445",
                    name: "Game Nights",
                    topic: "Chat about upcoming game nights and schedules.",
                    channelType: "TEXT",
                    isPrivate: false
                )
            ]
        )
    )

    
    init() {
//        fetchGuildDetail()
        dummyData()
    }
    
    func dummyData() {
        self.guild = dummyGuildDetailResponse.result.guild
        self.categories = dummyGuildDetailResponse.result.categories
        self.channels = dummyGuildDetailResponse.result.channels
    }
    
    // MARK: 함수 - 길드 세부 정보 불러오기
    func fetchGuildDetail() {
        if let guildId = guildId {
            GuildServiceAPIManager.shared.fetchGuildInfo(guildId: guildId) { result in
                switch result {
                case .success(let susscessResponse):
                    DispatchQueue.main.async {
                        self.guild = susscessResponse.result.guild
                        self.categories = susscessResponse.result.categories
                        self.channels = susscessResponse.result.channels
                    }
                    
                case .failure(let errorResponse):
                    DispatchQueue.main.async {
                        self.errorMessage = errorResponse.localizedDescription
                    }
                    print("GuildDetailViewModel - fetchGuildDetail() - 에러 발생: \(errorResponse)")
                }
            }
        }
    }
}
