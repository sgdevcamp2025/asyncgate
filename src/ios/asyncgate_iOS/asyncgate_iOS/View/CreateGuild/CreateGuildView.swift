//
//  CreateGuildView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

struct CreateGuildView: View {
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
            
            CreateGuildButton(imageName: "person.badge.key.fill",text: "직접 만들기", imageWidth: 30, imageHeight: 30)
            
            Spacer()
            
            VStack {
                Text("이미 초대장을 받으셨나요?")
                    .font(Font.pretendardSemiBold(size: 20))
                    .foregroundColor(Color.colorDart400)
                    .padding(.bottom, 10)
                
                Button {
                    // FIXME: 수정 예정
                } label: {
                    UsingButton(text: "서버 참가하기", backgroundColor: Color.colorBlurple, textColor: Color.colorWhite, size: 14)
                }
            }
            
        }
        .padding()
        .applyBackground()
    }
}

#Preview {
    CreateGuildView()
}
