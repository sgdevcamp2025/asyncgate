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
    
    @State private var isShowCreateGuildView: Bool = false
    @State private var isShowGuildModalView: Bool = false
    
    var body: some View {
        NavigationStack {
            VStack {
                HStack {
                    ScrollView {
                        VStack(alignment: .center) {
                            Button {
                            
                            } label: {
                                SecondaryActionButtonStyle(imageName: "message.fill", color: Color.colorGrayImage)
                            }
                            
                            Rectangle()
                                .frame(width: 40, height: 1)
                                .foregroundStyle(Color(hex: "#282930"))
                            
                            ForEach(guildListViewModel.myGuildList, id: \.self) { guild in
                                Button {
                                    guildDetailViewModel.guildId = guild.guildId
                                } label: {
                                    GuildButtonStyle(name: guild.name, profileImageUrl: guild.profileImageUrl)
                                }
                            }
                            
                            Button {
                                isShowCreateGuildView.toggle()
                            } label: {
                                SecondaryActionButtonStyle(imageName: "plus", color: Color.colorGreen)
                            }
                        }
                    }
                    .padding(5)
                    .padding(.leading, 5)
                    
                    ZStack {
                        RoundedRectangle(cornerRadius: 20)
                            .foregroundStyle(Color.colorBG)
                            .frame(maxHeight: .infinity)
                        
                        VStack(alignment: .leading) {
                            Button {
                                isShowGuildModalView.toggle()
                            } label: {
                                ChannelNameButtonStyle(channelName: guildDetailViewModel.guild?.name ?? "")
                            }
                            .padding(.top, 20)
                            
                            HStack {
                                Button {
                                    
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
                                ForEach(guildDetailViewModel.categories, id: \.self) { category in
                                    Button {
                                       
                                    } label: {
                                        CategoryButtonStyle(categoryName: category.name)
                                    }
                                }
                                
                                ForEach(guildDetailViewModel.channels, id: \.self) { channel in
                                    Button {
                                       
                                    } label: {
                                        ChannelButtonStyle(channelName: channel.name)
                                    }
                                }
                                
                            }
                        }
                        .padding()
                    }
                }
                .applyGuildBackground()
            }
            .onChange(of: guildListViewModel.firstGuildId) {
                guildDetailViewModel.guildId = guildListViewModel.firstGuildId
            }
            .onChange(of: guildDetailViewModel.guildId) {
                guildDetailViewModel.fetchGuildDetail()
            }
            .fullScreenCover(isPresented: $isShowCreateGuildView) {
                CreateGuildView()
            }
            .sheet(isPresented: $isShowGuildModalView) {
                GuildModalView(guildDetailViewModel: guildDetailViewModel)
            }
            .navigationBarBackButtonHidden(true)
        }
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
