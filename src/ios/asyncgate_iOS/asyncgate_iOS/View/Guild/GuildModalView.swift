//
//  GuildModalView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

// MARK: View - 길드 모달 뷰 (편집)
struct GuildModalView: View {
    @Environment(\.presentationMode) var presentation
    
    @ObservedObject var guildListViewModel: GuildListViewModel
    @ObservedObject var guildDetailViewModel: GuildDetailViewModel
    @ObservedObject var cudGuildViewModel: CUDGuildViewModel
    @ObservedObject var guildCategoryViewModel: GuildCategoryViewModel
    @ObservedObject var guildChannelViewModel: GuildChannelViewModel
    
    @State private var isShowCreateCategoryView: Bool = false
    @State private var isShowCreateChannelView: Bool = false
    @State private var isShowUpdateGuildView: Bool = false
    
    let maxLength: Int = 4
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                ZStack {
                    if let imageUrlString = guildDetailViewModel.guild?.profileImageUrl, let imageUrl = URL(string: imageUrlString) {
                        AsyncImage(url: imageUrl) { image in
                            image.image?.resizable()
                                .clipShape(RoundedRectangle(cornerRadius: 10))
                                .frame(width: 70, height: 70)
                        }
                        
                    } else {
                        RoundedRectangle(cornerRadius: 10)
                            .frame(width: 70, height: 70)
                            .foregroundStyle(Color.colorNewGuildButton)
                        
                        if let guildName = guildDetailViewModel.guild?.name {
                            Text(guildName.count > maxLength ? guildName.prefix(maxLength) + "..." : guildName)
                                .font(Font.pretendardSemiBold(size: 12))
                                .foregroundStyle(Color.colorWhite)
                        }
                    }
                }
                Spacer()
            }
            
            if let guildName = guildDetailViewModel.guild?.name {
                Text(guildName)
                    .font(Font.pretendardSemiBold(size: 24))
                    .foregroundStyle(Color.colorWhite)
                    .padding(.top, 10)
            }
            
            Text("카테고리 및 채널")
                .font(Font.pretendardSemiBold(size: 16))
                .foregroundColor(Color.colorDart400)
                .padding(.top, 10)
            
            Button {
                isShowCreateCategoryView = true
            } label: {
                CreateGuildButtonStyle(imageName: "", text: "카테고리 만들기", imageWidth: 0, imageHeight: 0)
            }
            
            Button {
                isShowCreateChannelView = true
            } label: {
                CreateGuildButtonStyle(imageName: "", text: "채널 만들기", imageWidth: 0, imageHeight: 0)
            }
            
            Text("길드")
                .font(Font.pretendardSemiBold(size: 16))
                .foregroundColor(Color.colorDart400)
                .padding(.top, 10)
            
            Button {
                if let guildId = guildDetailViewModel.guildId {
                    cudGuildViewModel.guildId = guildId
                }
                isShowUpdateGuildView = true
            } label: {
                CreateGuildButtonStyle(imageName: "", text: "길드 수정하기", imageWidth: 0, imageHeight: 0)
            }
            
            Button {
                if let guildId = guildDetailViewModel.guildId {
                    cudGuildViewModel.guildId = guildId
                    cudGuildViewModel.deleteGuildDetail()
                }
            } label: {
                CreateGuildButtonStyle(imageName: "", text: "길드 삭제하기", imageWidth: 0, imageHeight: 0)
            }
            
            
            Spacer()
        }
        .applyBackground()
        .padding()
        .padding(.top, 20)
        .fullScreenCover(isPresented: $isShowCreateCategoryView) {
            CreateCategoryView(guildCategoryViewModel: guildCategoryViewModel, guildDetailViewModel: guildDetailViewModel)
        }
        .fullScreenCover(isPresented: $isShowCreateChannelView) {
            CreateChannelView(guildChannelViewModel: guildChannelViewModel, guildDetailViewModel: guildDetailViewModel)
        }
        .fullScreenCover(isPresented: $isShowUpdateGuildView) {
            UpdateGuildView(cudGuildViewModel: cudGuildViewModel, guildListViewModel: guildListViewModel)
        }
    }
}
