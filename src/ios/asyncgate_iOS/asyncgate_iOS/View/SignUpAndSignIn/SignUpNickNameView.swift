//
//  SignUpNickNameView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 회원가입 - 닉네임 설정 View
struct SignUpNickNameView: View {
    @ObservedObject var signUpViewModel: SignUpViewModel
    
    var body: some View {
        VStack {
            Text("이름은 무엇인가요?")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "별명", placeholder: "", text: $signUpViewModel.nickName)
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            NavigationLink(destination: SignUpNameAndPasswordView(signUpViewModel: signUpViewModel)) {
                SignButtonStyle(text: "다음")
            }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
    }
}
