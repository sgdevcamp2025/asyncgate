//
//  UpdateChannelView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/21/25.
//

import SwiftUI

// MARK: View - 채널 수정
struct UpdateChannelView: View {
    @Environment(\.dismiss) var dismiss
    @ObservedObject var guildChannelViewModel: GuildChannelViewModel
    @ObservedObject var guildDetailViewModel: GuildDetailViewModel
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .leading) {
                HStack {
                    Button {
                        dismiss()
                    } label: {
                        Image(systemName: "xmark")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundStyle(Color.colorWhite)
                    }
                    Spacer()
                    
                    Text("채널 설정")
                        .font(Font.pretendardBold(size: 18))
                        .foregroundColor(Color.colorWhite)
            
                    Spacer()
                    
                    Button {
                       if let guildId = guildDetailViewModel.guildId {
                            guildChannelViewModel.guildId = guildId
                            guildChannelViewModel.updateChannel()
                            dismiss()
                        }
                    } label: {
                        Text("저장")
                            .font(Font.pretendardSemiBold(size: 16))
                            .foregroundStyle(Color(hex: "#6469A2"))
                    }
                }
                .padding(.bottom, 10)
                
                Divider()
                    .padding(.bottom, 10)
                
                CTextField(stepCaption: "채널 이름", placeholder: "새로운 채널", text: $guildChannelViewModel.name)
                
                Text("채널 주제")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                TextField("", text: $guildChannelViewModel.topic)
                    .foregroundColor(.white)
                    .padding()
                    .frame(height: 100)
                    .background(Color(hex: "#111216"))
                    .cornerRadius(20)
                    .overlay(
                        Group {
                            if guildChannelViewModel.topic.isEmpty {
                                Text("채널에 대한 설명을 입력하세요.")
                                    .foregroundStyle(Color.colorDart400)
                                    .padding(.leading, 15)
                            }
                        }
                        , alignment: .leading
                    )
                
                Text("카테고리")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack {
                        Button {
                            guildChannelViewModel.categoryId = "CATEGORY_ID_IS_NULL"
                        } label: {
                            let isSelected = guildChannelViewModel.categoryId == "CATEGORY_ID_IS_NULL"
                            
                            Text("없음")
                                .padding()
                                .font(Font.pretendardSemiBold(size: 14))
                                .background(
                                       RoundedRectangle(cornerRadius: 20)
                                           .fill(isSelected ? Color.colorBlurple : Color.colorDart400)
                                   )
                                .foregroundStyle(Color.colorWhite)
                        }
                        
                        ForEach(guildDetailViewModel.categories, id: \.self) { category in
                            Button {
                                guildChannelViewModel.categoryId = category.categoryId
                            } label: {
                                let isSelected = guildChannelViewModel.categoryId == category.categoryId
                               
                                Text(category.name)
                                    .padding()
                                    .font(Font.pretendardSemiBold(size: 14))
                                    .background(
                                           RoundedRectangle(cornerRadius: 20)
                                               .fill(isSelected ? Color.colorBlurple : Color.colorDart400)
                                       )
                                    .foregroundStyle(Color.colorWhite)
                            }
                        }
                    }
                }
                
                let _ = print("guildId: \(guildChannelViewModel.guildId)")
                let _ = print("categoryId: \(guildChannelViewModel.categoryId)")
                let _ = print("channelId: \(guildChannelViewModel.channelId)")
                
                Text("채널을 비공개로 만들면 선택한 멤버들과 역할만 이 채널을 볼 수 있어요.")
                    .font(Font.pretendardSemiBold(size: 14))
                    .foregroundColor(Color.colorDart400)
                    .multilineTextAlignment(.leading)
                    .padding(.top, 10)
                    .padding(.bottom, 10)
                
                CToggle(text: "비공개 채널", isPrivate: $guildChannelViewModel.isPrivate)
                
                Spacer()
            }
            .padding()
            .applyBackground()
            .navigationBarBackButtonHidden(true)
        }
    }
}

