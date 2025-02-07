//
//  CreateGuildLastView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

struct CreateGuildLastView: View {
    @State private var guildName: String = ""
    
    var body: some View {
        VStack {
            Text("서버를 만들어보세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
                .padding(.bottom, 10)
            
            Text("서버는 나와 친구들이 함께 어울리는 공간입니다.\n 내 서버를 만들고 대화를 시작해보세요.")
                .font(Font.pretendardSemiBold(size: 14))
                .foregroundColor(Color.colorDart400)
                .multilineTextAlignment(.center)
                .padding(.bottom, 30)
            
            Button {
                
            } label: {
                Circle()
                    .foregroundStyle(Color.white)
                    .frame(width: 80, height: 80)
            }
            
            VStack(alignment: .leading) {
                Text("서버 이름")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                TextField("", text: $guildName)
                    .foregroundColor(.white)
                    .padding()
                    .background(Color(hex: "#111216"))
                    .cornerRadius(20)
                    .overlay(
                        Group {
                            if guildName.isEmpty {
                                Text("서버 이름")
                                    .foregroundStyle(Color.colorDart400)
                                    .padding(.leading, 15)
                            }
                        }
                        , alignment: .leading
                    )
            }
            .padding(.bottom, 30)
            
            Button {
                // FIXME: 수정 예정
            } label: {
                UsingButton(text: "서버 만들기", backgroundColor: Color.colorBlurple, textColor: Color.colorWhite, size: 14)
            }
            
            Spacer()
            
        }
        .padding()
        .applyBackground()
    }
}

#Preview {
    CreateGuildLastView()
}
