//
//  ChattingView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/16/25.
//

import SwiftUI

// FIXME: UI만 만들어놓음
struct ChattingView: View {
    @State private var chat: String = ""
    
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
                ForEach(0..<4, id: \.self) { _ in
                    Chat(profileImage: "", nickName: "KDK")
                }
            }
            
            HStack {
                Button {
                    
                } label: {
                    chattingIconButtonStyle
                }
                
                TextField("채팅을 입력하세요", text: $chat)
                    .frame(height: 43)
                    .padding(.leading, 15)
                    .background(Color(hex: "#25272E"))
                    .clipShape(RoundedRectangle(cornerRadius: 20))
            }
            
        }
        .padding(15)
        .applyChattingBG()
        .navigationBarBackButtonHidden(true)
    }
    
    var chattingChannelNameButtonStyle: some View {
        HStack {
            Text("#")
                .foregroundStyle(Color.colorDart400)
                .font(Font.pretendardBold(size: 20))
            
            ChannelNameButtonStyle(channelName: "중꺽마")
            
        }
    }
    
    var chattingIconButtonStyle: some View {
        ZStack {
            Circle()
                .frame(width: 43, height: 43)
                .foregroundStyle(Color(hex: "#25272E"))
            
            Image(systemName: "plus")
                .resizable()
                .frame(width: 20, height: 20)
                .foregroundStyle(Color(hex: "#C7C8CE"))
        }
    }
}


#Preview {
    ChattingView()
}
