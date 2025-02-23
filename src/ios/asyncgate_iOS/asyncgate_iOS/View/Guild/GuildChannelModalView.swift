//
//  GuildChannelModalView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/21/25.
//

import SwiftUI

struct GuildChannelModalView: View {
    @Environment(\.presentationMode) var presentation
    
    @ObservedObject var guildDetailViewModel: GuildDetailViewModel
    @ObservedObject var guildChannelViewModel: GuildChannelViewModel
    
    @State private var isShowUpdateChannelView: Bool = false
    
    let maxLength: Int = 4
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                ZStack {
                    if let imageUrlString = guildDetailViewModel.guild?.profileImageUrl, let imageUrl = URL(string: imageUrlString) {
                        AsyncImage(url: imageUrl) { image in
                            image.image?.resizable()
                                .clipShape(RoundedRectangle(cornerRadius: 10))
                                .frame(width: 50, height: 50)
                        }
                        
                    } else {
                        RoundedRectangle(cornerRadius: 10)
                            .frame(width: 50, height: 50)
                            .foregroundStyle(Color.colorNewGuildButton)
                        
                        if let guildName = guildDetailViewModel.guild?.name {
                            Text(guildName.count > maxLength ? guildName.prefix(maxLength) + "..." : guildName)
                                .font(Font.pretendardSemiBold(size: 12))
                                .foregroundStyle(Color.colorWhite)
                        }
                    }
                }
                
                Text(guildChannelViewModel.name)
                        .font(Font.pretendardSemiBold(size: 20))
                        .foregroundStyle(Color.colorWhite)
                        .padding(.top, 10)
                
                Spacer()
            }
            .padding(.bottom, 20)
           
            Button {
                isShowUpdateChannelView = true
            } label: {
                CreateGuildButtonStyle(imageName: "gearshape.fill", text: "채널 편집", imageWidth: 24, imageHeight: 24, isBehindChevron: true, isSystemImage: true)
            }
            
            Button {
                guildChannelViewModel.deleteChannel()
                presentation.wrappedValue.dismiss()
            } label: {
                CreateGuildButtonStyle(imageName: "", text: "채널 삭제하기", imageWidth: 0, imageHeight: 0, isBehindChevron: true, isSystemImage: true, isDeleteButton: true)
            }
           
            Spacer()
        }
        .padding()
        .applyBackground()
        .padding(.top, 20)
        .fullScreenCover(isPresented: $isShowUpdateChannelView) {
            UpdateChannelView(guildChannelViewModel: guildChannelViewModel, guildDetailViewModel: guildDetailViewModel)
        }
    }
}
