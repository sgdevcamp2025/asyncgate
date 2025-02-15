//
//  GuildMainView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

// MARK: MainView - 길드 메인 화면
struct GuildMainView: View {
    @State private var isShowCreateGuildView: Bool = false
    
    var body: some View {
        NavigationStack {
            VStack {
                HStack {
                    ScrollView {
                        VStack(alignment: .center) {
                            Button {
                                //
                            } label: {
                                GuildButton(imageName: "message.fill", color: Color.colorGrayImage)
                            }
                            
                            Rectangle()
                                .frame(width: 40, height: 1)
                                .foregroundStyle(Color(hex: "#282930"))
                            
                            // FIXME: 길드 채널 목록 보여주기
                            
                            
                            Button {
                                isShowCreateGuildView.toggle()
                            } label: {
                                GuildButton(imageName: "plus", color: Color.colorGreen)
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
                                
                            } label: {
                                Text("중꺽마")
                                    .foregroundStyle(Color.colorWhite)
                                    .font(Font.pretendardBold(size: 17))
                                
                                Image(systemName: "chevron.right")
                                    .resizable()
                                    .frame(width: 5, height: 6)
                                    .foregroundColor(Color(hex: "#9597A3"))
                            }
                            .padding(.top, 20)
                            
                            HStack {
                                Button {
                                    
                                } label: {
                                    GuildSerachButton
                                }
                                
                                Button {
                                    
                                } label: {
                                    GuildIconButton(imageName: "person.2.badge.plus.fill", width: 20, height: 14)
                                }
                                
                                Button {
                                    
                                } label: {
                                    GuildIconButton(imageName: "calendar", width: 16, height: 16)
                                }
                            }
                            
                            Divider()
                            
                            ScrollView {
                                
                                Button {
                                    
                                } label: {
                                    categoryButtonStyle
                                }
                                
                                Button {
                                    
                                } label: {
                                    channelButtonStyle
                                }
                                
                            }
                        }
                        .padding()
                        
                    }
                    
                }
                .applyGuildBackground()
            }
            .fullScreenCover(isPresented: $isShowCreateGuildView) {
                CreateGuildView()
            }
        }
    }
    
    
    // ButtonStyle - '검색하기' 버튼 스타일
    var GuildSerachButton: some View {
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
    
    // ButtonStyle - 카테고리 버튼 스타일
    var categoryButtonStyle: some View {
        ZStack {
            Rectangle()
                .frame(width: 300, height: 30)
                .foregroundStyle(Color.colorBG)
            
            HStack {
                Image(systemName: "chevron.down")
                // chevron.right로 누르면 수정
                    .resizable()
                    .frame(width: 6, height: 4)
                    .foregroundStyle(Color.colorWhite)
                
                Text("Notice")
                    .foregroundStyle(Color.colorGrayImage)
                    .font(Font.pretendardSemiBold(size: 14))
                
                Spacer()
            }
        }
    }
    
    // ButtonStyle - 채널 버튼 스타일
    var channelButtonStyle: some View {
        ZStack {
            Rectangle()
                .frame(width: 300, height: 30)
                .foregroundStyle(Color.colorBG)
            
            HStack {
                Text("#")
                    .font(Font.pretendardSemiBold(size: 16))
                
                Text("Notice")
                    .foregroundStyle(Color.colorGrayImage)
                    .font(Font.pretendardSemiBold(size: 14))
                
                Spacer()
            }
        }
    }
}

#Preview {
    GuildMainView()
}
