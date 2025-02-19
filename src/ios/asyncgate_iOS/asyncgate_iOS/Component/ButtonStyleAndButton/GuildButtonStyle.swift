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
    
    var body: some View {
        ZStack {
          if let imageUrlString = profileImageUrl, let imageUrl = URL(string: imageUrlString), imageUrlString == "https://asyncgate5.s3.ap-northeast-2.amazonaws.com/default/default-profile.png" {
               
                    AsyncImage(url: imageUrl) { image in
                        image.image?.resizable()
                            .clipShape(Circle())
                            .frame(width: 46, height: 46)
                    }
                    
                    Text(name.count > maxLength ? name.prefix(maxLength) + "..." : name)
                        .font(Font.pretendardSemiBold(size: 12))
                        .foregroundStyle(Color.colorWhite)
           
            } else if let imageUrlString = profileImageUrl, let imageUrl = URL(string: imageUrlString) {
                AsyncImage(url: imageUrl) { image in
                    image.image?.resizable()
                        .clipShape(Circle())
                        .frame(width: 46, height: 46)
                }
            } else {
                Circle()
                    .frame(width: 46, height: 46)
                    .foregroundStyle(Color.colorNewGuildButton)
                
                Text(name.count > maxLength ? name.prefix(maxLength) + "..." : name)
                    .font(Font.pretendardSemiBold(size: 12))
                    .foregroundStyle(Color.colorWhite)
            }
        }
    }
}

