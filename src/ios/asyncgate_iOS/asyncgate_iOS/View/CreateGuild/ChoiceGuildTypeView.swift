//
//  ChoiceGuildTypeView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

// MARK: 길드 타입 설정 View
struct ChoiceGuildTypeView: View {
    var body: some View {
        VStack {
            Text("이 서버에 대해 더 자세히 말해주세요.")
                .font(Font.pretendardBold(size: 24))
                .foregroundColor(Color.colorWhite)
                .padding(.bottom, 10)
            
            Text("설정을 돕고자 질문을 드려요. 혹시 서버가 친구 몇 명만을 위한 서버인가요, 아니면 다른 더 큰 커뮤니티를 위한 서버인가요?")
                .font(Font.pretendardSemiBold(size: 14))
                .foregroundColor(Color.colorDart400)
                .multilineTextAlignment(.center)
                .padding(.bottom, 30)
            
            NavigationLink(destination: CreateGuildLastView()) {
                CreateGuildButtonStyle(imageName: "club",text: "클럽, 혹은 커뮤니티용 서버", imageWidth: 48, imageHeight: 44)
            }
            
            NavigationLink(destination: CreateGuildLastView()) {
                CreateGuildButtonStyle(imageName: "forMe", text: "나와 친구들을 위한 서버", imageWidth: 48, imageHeight: 44)
            }
            
            Spacer()
        }
        .padding()
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
    }
}

#Preview {
    ChoiceGuildTypeView()
}
