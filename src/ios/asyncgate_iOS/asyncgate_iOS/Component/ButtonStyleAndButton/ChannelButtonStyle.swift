//
//  ChannelButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

struct ChannelButtonStyle: View {
    var channelName: String
    var channelType: String
    
    var body: some View {
        ZStack {
            Rectangle()
                .frame(width: 300, height: 30)
                .foregroundStyle(Color.colorBG)
            
            HStack {
                if channelType == "VOICE" {
                    Image(systemName: "speaker.wave.2.fill")
                        .resizable()
                        .frame(width: 16, height: 16)
                        .foregroundStyle(Color(hex: "#818491"))
                } else {
                    Text("#")
                        .font(Font.pretendardRegular(size: 20))
                }

                Text(channelName)  
                    .foregroundStyle(Color.colorGrayImage)
                    .font(Font.pretendardSemiBold(size: 14))
                
                Spacer()
            }
        }
    }
}
