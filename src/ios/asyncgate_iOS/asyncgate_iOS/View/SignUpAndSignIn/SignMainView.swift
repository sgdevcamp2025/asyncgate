//
//  SignMainView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

struct SignMainView: View {
    var body: some View {
        NavigationStack {
            VStack(alignment: .center) {
                Spacer()
                
                Image("DiscordLogo")
                    .resizable()
                    .frame(width: 85, height: 65)
                    .padding(.bottom, 27)
                
                Text("DISCORD에 오신 걸 환영합니다")
                    .foregroundStyle(Color.colorWhite)
                    .font(Font.pretendardBold(size: 30))
                    .multilineTextAlignment(.center)
                    .padding(.bottom, 9)
                
                Text("어울리고, 게임하고, 가볍게 대화하세요. 아래를 탭해 시작해요!")
                    .foregroundStyle(Color.colorWhite)
                    .font(Font.pretendardSemiBold(size: 18))
                    .multilineTextAlignment(.center)
                
                Spacer()
                
                VStack {
                    NavigationLink(destination: SignUpView()) {
                        UsingButton(text: "가입하기", backgroundColor: Color.colorWhite, textColor: Color.colorBlack, size: 15)
                    }
                    .padding(.bottom, 13)
                    
                    NavigationLink(destination: SignInView()) {
                        UsingButton(text: "로그인", backgroundColor: Color.colorBlurple, textColor: Color.colorWhite, size: 15)
                    }
                }
                .padding(.bottom, 25)
            }
            .padding(30)
            .applyBackground()
        }
    }
}

#Preview {
    SignMainView()
}
