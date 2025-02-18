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
    @StateObject var createGuildViewModel = CreateGuildViewModel()
    
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
    }
}

#Preview {
    @Previewable @StateObject var guildDetailViewModel = GuildDetailViewModel()

    GuildModalView(guildDetailViewModel: guildDetailViewModel)
}
