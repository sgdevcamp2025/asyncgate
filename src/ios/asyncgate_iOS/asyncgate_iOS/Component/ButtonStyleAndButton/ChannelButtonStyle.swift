//
//  ChannelButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

struct ChannelButtonStyle: View {
    var channelName: String
    
    var body: some View {
        ZStack {
            Rectangle()
                .frame(width: 300, height: 30)
                .foregroundStyle(Color.colorBG)
            
            HStack {
                Text("#")
                    .font(Font.pretendardSemiBold(size: 16))
                
                Text(channelName)
                    .foregroundStyle(Color.colorGrayImage)
                    .font(Font.pretendardSemiBold(size: 14))
                
                Spacer()
            }
        }
    }
    
}
