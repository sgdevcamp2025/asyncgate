//
//  GuildMainView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

// MARK: MainView - 길드 메인 화면
struct GuildMainView: View {
    @StateObject var guildListViewModel = GuildListViewModel()
    @StateObject var guildDetailViewModel = GuildDetailViewModel()
    @StateObject var cudGuildViewModel = CUDGuildViewModel()
    @StateObject var guildCategoryViewModel = GuildCategoryViewModel()
    @StateObject var guildChannelViewModel = GuildChannelViewModel()
    
    @State private var isShowCreateGuildView: Bool = false
    @State private var isShowGuildModalView: Bool = false
    @State private var isShowCategoryModalView: Bool = false
    @State private var isShowChannelModalView: Bool = false
    
    @State private var needToFetchDetails: Bool = false
    @State private var needToFetchGuildList: Bool = false
    
    var body: some View {
        NavigationStack {
            VStack {
                HStack {
                    ScrollView {
                        VStack(alignment: .center) {
                            Button {
                                guildChannelViewModel.updateChannel()
                            } label: {
                                SecondaryActionButtonStyle(imageName: "message.fill", color: Color.colorGrayImage)
                            }
                            
                            Rectangle()
                                .frame(width: 40, height: 1)
                                .foregroundStyle(Color(hex: "#282930"))
                                .padding(.bottom, 4)
                            
                            ForEach(guildListViewModel.myGuildList, id: \.self) { guild in
                                Button {
                                    guildDetailViewModel.guildId = guild.guildId
                                } label: {
                                    GuildButtonStyle(name: guild.name, profileImageUrl: guild.profileImageUrl, isSelected: guildDetailViewModel.guildId == guild.guildId)
                                }
                            }
                            
                            Button {
                                isShowCreateGuildView.toggle()
                            } label: {
                                SecondaryActionButtonStyle(imageName: "plus", color: Color.colorGreen)
                            }
                        }
                    }
                    .padding(.leading, 4)
                    .padding(5)
                    
                    ZStack {
                        RoundedRectangle(cornerRadius: 20)
                            .foregroundStyle(Color.colorBG)
                            .frame(maxHeight: .infinity)
                        
                        VStack(alignment: .leading) {
                            Button {
                                if let guildId = guildDetailViewModel.guildId {
                                    guildChannelViewModel.guildId = guildId
                                    guildCategoryViewModel.guildId = guildId
                                }
                                isShowGuildModalView.toggle()
                            } label: {
                                ChannelNameButtonStyle(channelName: guildDetailViewModel.guild?.name ?? "")
                            }
                            .padding(.top, 20)
                            
                            HStack {
                                Button {
                                    guildChannelViewModel.deleteChannel()
                                } label: {
                                    GuildSerachButtonStyle
                                }
                                
                                Button {
                                    
                                } label: {
                                    GuildIconButtonStyle(imageName: "person.2.badge.plus.fill", width: 20, height: 14)
                                }
                                
                                Button {
                                    
                                } label: {
                                    GuildIconButtonStyle(imageName: "calendar", width: 16, height: 16)
                                }
                            }
                            
                            Divider()
                            
                            ScrollView {
                                let filteredChannels = guildDetailViewModel.channels.filter {
                                    guard let categoryId = $0.categoryId else { return true }
                                    return categoryId == "CATEGORY_ID_IS_NULL"
                                }
                                
                                if !filteredChannels.isEmpty {
                                    ForEach(filteredChannels, id: \.self) { channel in
                                        Button {
                                            if let guildId = guildDetailViewModel.guildId {
                                                guildChannelViewModel.guildId = guildId
                                            }
                                            guildChannelViewModel.channelId = channel.channelId
                                            guildChannelViewModel.name = channel.name
                                            isShowChannelModalView = true
                                        } label: {
                                            ChannelButtonStyle(channelName: channel.name, channelType: channel.channelType)
                                        }
                                    }
                                }
                                
                                ForEach(guildDetailViewModel.categories, id: \.self) { category in
                                    Button {
                                       
                                    } label: {
                                        CategoryButtonStyle(categoryName: category.name)
                                    }
                                    .simultaneousGesture(LongPressGesture().onEnded { _ in
                                        if let guildId = guildDetailViewModel.guildId {
                                            guildCategoryViewModel.guildId = guildId
                                        }
                                        guildChannelViewModel.categoryId = category.categoryId
                                        guildCategoryViewModel.categoryId = category.categoryId
                                        guildCategoryViewModel.name = category.name
                                        isShowCategoryModalView = true
                                        }
                                    )
                                    
                                    let filteredChannelList = guildDetailViewModel.channels.filter({ $0.categoryId == category.categoryId })
                                    
                                    if !filteredChannelList.isEmpty {
                                        ForEach(filteredChannelList, id: \.self) { channel in
                                            Button {
                                                if let guildId = guildDetailViewModel.guildId {
                                                    guildChannelViewModel.guildId = guildId
                                                }
                                                guildChannelViewModel.channelId = channel.channelId
                                                guildChannelViewModel.categoryId = category.categoryId
                                                guildChannelViewModel.name = channel.name
                                                isShowChannelModalView = true
                                            } label: {
                                                ChannelButtonStyle(channelName: channel.name, channelType: channel.channelType)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        .padding()
                    }
                }
                .applyGuildBackground()
                .navigationBarBackButtonHidden(true)
            }
            .onChange(of: guildListViewModel.firstGuildId) {
                guildDetailViewModel.guildId = guildListViewModel.firstGuildId
                needToFetchGuildList = true
                needToFetchDetails = true
            }
            .onChange(of: guildListViewModel.firstGuildId) {
                guildDetailViewModel.guildId = guildListViewModel.firstGuildId
                needToFetchDetails = true
            }
            .onChange(of: guildDetailViewModel.guildId) {
                needToFetchDetails = true
            }
            .onChange(of: cudGuildViewModel.isNeedRefresh) {
                needToFetchGuildList = true
                needToFetchDetails = true
            }
            .onChange(of: guildCategoryViewModel.isNeedRefresh) {
                needToFetchDetails = true
            }
            .onChange(of: guildChannelViewModel.isNeedRefresh) {
                needToFetchDetails = true
            }
            .onChange(of: [needToFetchGuildList, needToFetchDetails]) {
                DispatchQueue.main.async {
                    if needToFetchGuildList {
                        guildListViewModel.fetchMyGuildList()
                        needToFetchGuildList = false
                    }
                    
                    if needToFetchDetails {
                        guildDetailViewModel.fetchGuildDetail()
                        needToFetchDetails = false
                    }
                }
            }
            .fullScreenCover(isPresented: $isShowCreateGuildView) {
                CreateGuildView(isShowCreateGuildView: $isShowCreateGuildView)
                    .onDisappear {
                        cudGuildViewModel.reset()
                        needToFetchGuildList = true
                        needToFetchDetails = true
                    }
            }
            .sheet(isPresented: $isShowGuildModalView) {
                GuildModalView(guildListViewModel: guildListViewModel, guildDetailViewModel: guildDetailViewModel, cudGuildViewModel: cudGuildViewModel, guildCategoryViewModel: guildCategoryViewModel, guildChannelViewModel: guildChannelViewModel)
                    .onDisappear {
                        if cudGuildViewModel.isDeleteGuild {
                            guildDetailViewModel.guildId = guildListViewModel.firstGuildId ?? ""
                        }
                        needToFetchGuildList = true
                        cudGuildViewModel.reset()
                        needToFetchDetails = true
                    }
            }
            .sheet(isPresented: $isShowCategoryModalView) {
                GuildCategoryModalView(guildDetailViewModel: guildDetailViewModel, guildCategoryViewModel: guildCategoryViewModel, guildChannelViewModel: guildChannelViewModel)
                    .onDisappear {
                        guildChannelViewModel.reset()
                        guildCategoryViewModel.name = ""
                    }
            }
            .sheet(isPresented: $isShowChannelModalView) {
                GuildChannelModalView(guildDetailViewModel: guildDetailViewModel, guildChannelViewModel: guildChannelViewModel)
                    .onDisappear {
                        guildChannelViewModel.reset()
                    }
            }
        }
        .applyGuildBackground()
        .navigationBarBackButtonHidden(true)
    }
    
    // ButtonStyle - '검색하기' 버튼 스타일
    var GuildSerachButtonStyle: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .frame(height: 32)
                .foregroundStyle(Color(hex: "383A43"))
            HStack {
                Image(systemName: "magnifyingglass")
                    .resizable()
                    .frame(width: 18, height: 18)
                    .foregroundStyle(Color(hex: "#C7C8CE"))
                
                Text("검색하기")
                    .foregroundStyle(Color(hex: "#C6C7CD"))
                    .font(Font.pretendardSemiBold(size: 16))
            }
        }
    }
}

#Preview {
    GuildMainView()
}
