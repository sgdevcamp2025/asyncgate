//
//  Chat.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/28/25.
//

import SwiftUI

struct Chat: View {
    var profileImage: String
    var nickName: String
    var message: String
    
    var body: some View {
        HStack {
            VStack {
                Image(profileImage)
                    .resizable()
                    .frame(width: 45, height: 45)
                    .foregroundStyle(Color(hex: "#C7C8CE"))
                
                Spacer()
            }
            
            VStack(alignment: .leading) {
                HStack {
                    Text(nickName)
                        .font(Font.pretendardSemiBold(size: 15))
                        .foregroundStyle(Color.colorWhite)
                    
                    Text("날짜")
                        .font(Font.pretendardRegular(size: 13))
                        .foregroundStyle(Color(hex: "#C7C8CE"))
                    
                    Text("시간")
                        .font(Font.pretendardRegular(size: 13))
                        .foregroundStyle(Color(hex: "#C7C8CE"))
                }
                
                Text(message)
                    .font(Font.pretendardRegular(size: 15))
                    .foregroundStyle(Color.colorWhite)
            }
            
        }
    }
}
