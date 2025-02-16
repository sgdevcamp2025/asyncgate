//
//  ChattingView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/16/25.
//

import SwiftUI

struct ChattingView: View {
    
    var body: some View {
        VStack {
            HStack {
                BackButton()
                
                Button {
                    
                } label: {
                    chattingChannelNameButtonStyle
                }
                
                Spacer()
                
                Button {
                    
                } label: {
                    GuildIconButtonStyle(imageName: "magnifyingglass", width: 16, height: 17)
                }
                
            }
            
            ScrollView {
                ForEach(0..<100, id: \.self) { _ in
                    Text("chatting")
                        .foregroundStyle(Color.colorWhite)
                        .font(Font.pretendardBold(size: 16))
                }
            }
        }
        .padding(15)
        .applyGuildBackground()
        .navigationBarBackButtonHidden(true)
    }
    
    var chattingChannelNameButtonStyle: some View {
        HStack {
            Text("#")
                .foregroundStyle(Color.colorDart400)
                .font(Font.pretendardBold(size: 20))
            
            channelNameButtonStyle(channelName: "중꺽마")
            
        }
    }
}


#Preview {
    ChattingView()
}
