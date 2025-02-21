//
//  GuildCategoryModalView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/21/25.
//

import SwiftUI

struct GuildCategoryModalView: View {
    @Environment(\.presentationMode) var presentation
    
    @ObservedObject var guildDetailViewModel: GuildDetailViewModel
    @ObservedObject var guildCategoryViewModel: GuildCategoryViewModel
    @ObservedObject var guildChannelViewModel: GuildChannelViewModel
    
    @State private var isShowCreateCategoryView: Bool = false
    @State private var isShowCreateChannelView: Bool = false
    @State private var isShowUpdateGuildView: Bool = false
    
    var categoryId: String?
    
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
                let _ = print("GuildCategoryModelView!!! - categoryId: \(categoryId ?? "NONONONONONONONONONONO")")
                Text(guildCategoryViewModel.name)
                        .font(Font.pretendardSemiBold(size: 20))
                        .foregroundStyle(Color.colorWhite)
                        .padding(.top, 10)
                
                Spacer()
            }
            .padding(.bottom, 20)
           
            Button {
                isShowCreateCategoryView = true
            } label: {
                CreateGuildButtonStyle(imageName: "gearshape.fill", text: "카테고리 편집하기", imageWidth: 24, imageHeight: 24, isBehindChevron: true, isSystemImage: true)
            }
            
            Button {
                isShowCreateChannelView = true
                let _ = print("GuildCategoryModelView!!!!!!!!!!!! - categoryId: \(categoryId ?? "NONddddONO")")
            } label: {
                CreateGuildButtonStyle(imageName: "plus", text: "채널 만들기", imageWidth: 24, imageHeight: 24, isBehindChevron: true, isSystemImage: true)
            }
           
            Spacer()
        }
        .padding()
        .applyBackground()
        .padding(.top, 20)
        .fullScreenCover(isPresented: $isShowCreateCategoryView) {
            CreateCategoryView(guildCategoryViewModel: guildCategoryViewModel, guildDetailViewModel: guildDetailViewModel)
        }
        .fullScreenCover(isPresented: $isShowCreateChannelView) {
            CreateChannelView(guildChannelViewModel: guildChannelViewModel, guildDetailViewModel: guildDetailViewModel, categoryId: categoryId)
        }
    }
}

#Preview {
    @Previewable @StateObject var guildDetailViewModel = GuildDetailViewModel()
    @Previewable @StateObject var guildCategoryViewModel = GuildCategoryViewModel()
    @Previewable @StateObject var guildChannelViewModel = GuildChannelViewModel()
    
    GuildCategoryModalView(guildDetailViewModel: guildDetailViewModel, guildCategoryViewModel: guildCategoryViewModel, guildChannelViewModel: guildChannelViewModel)
}
