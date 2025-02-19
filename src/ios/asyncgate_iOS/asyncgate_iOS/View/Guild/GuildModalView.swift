//
//  GuildModalView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

struct GuildModalView: View {
    @Environment(\.presentationMode) var presentation
    
    @ObservedObject var guildDetailViewModel: GuildDetailViewModel
    @StateObject var createGuildViewModel = CUDGuildViewModel()
    
    @State private var isShowCreateCategoryView: Bool = false
    @State private var isShowCreateChannelView: Bool = false
    
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
                            
                            let _ = print("\(imageUrl)")
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
            
            Button {
                createGuildViewModel.patchGuild()
            } label: {
                CreateGuildButtonStyle(imageName: "", text: "길드 수정하기", imageWidth: 0, imageHeight: 0)
            }
            
            Button {
                createGuildViewModel.deleteGuildDetail()
            } label: {
                CreateGuildButtonStyle(imageName: "", text: "길드 삭제하기", imageWidth: 0, imageHeight: 0)
            }
            
            
            Spacer()
        }
        .padding()
        .padding(.top, 20)
        .applyBackground()
        .fullScreenCover(isPresented: $isShowCreateCategoryView) {
            CreateCategoryView(guildId: guildDetailViewModel.guildId)
        }
        .fullScreenCover(isPresented: $isShowCreateChannelView) {
            CreateChannelView(guildId: guildDetailViewModel.guildId)
        }
    }
}

#Preview {
    @Previewable @StateObject var guildDetailViewModel = GuildDetailViewModel()

    GuildModalView(guildDetailViewModel: guildDetailViewModel)
}
