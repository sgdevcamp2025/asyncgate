//
//  GuildButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/16/25.
//

import SwiftUI

struct GuildButtonStyle: View {
    let maxLength: Int = 3
   
    var name: String
    var profileImageUrl: String?
    var isSelected: Bool
    
    var body: some View {
        ZStack {
           if let imageUrlString = profileImageUrl, let imageUrl = URL(string: imageUrlString) {
                AsyncImage(url: imageUrl) { image in
                    image.image?.resizable()
                        .clipShape(isSelected ? AnyShape(RoundedRectangle(cornerRadius: 10)) : AnyShape(Circle()))
                        .frame(width: 46, height: 46)
                }
            } else {
                backgroundShape
            }
        }
    }
    
    // 길드 기본 버튼
    var backgroundShape: some View {
        ZStack {
            if isSelected {
                RoundedRectangle(cornerRadius: 14)
                    .frame(width: 46, height: 46)
                    .foregroundStyle(Color(hex: "#5865F2"))
            } else {
                Circle()
                    .frame(width: 46, height: 46)
                    .foregroundStyle(Color.colorNewGuildButton)
            }
            
            Text(name.count > maxLength ? name.prefix(maxLength) + "..." : name)
                .font(Font.pretendardSemiBold(size: 12))
                .foregroundStyle(Color.colorWhite)
        }
    }
}

#Preview {
    GuildButtonStyle(name: "", profileImageUrl: "", isSelected: true)
}
