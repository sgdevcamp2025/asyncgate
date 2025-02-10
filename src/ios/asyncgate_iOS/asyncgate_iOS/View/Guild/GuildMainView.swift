//
//  GuildMainView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

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
                                .foregroundColor(Color(hex: "#282930"))
                            
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
                                    
                                    ScrollView {
                                        
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
                        
                                }
                        } // ㄴ
    
                    }
                    
                }
                .applyGuildBackground()
            }
            .fullScreenCover(isPresented: $isShowCreateGuildView) {
                CreateGuildView()
            }
        }
    }
    
    var GuildSerachButton: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .frame(width: 200, height: 32)
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
