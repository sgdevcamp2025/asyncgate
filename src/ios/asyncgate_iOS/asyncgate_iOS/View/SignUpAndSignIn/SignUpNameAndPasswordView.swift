//
//  SignUpNameAndPasswordView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 회원가입 - 사용자명 및 비밀번호 설정 View
struct SignUpNameAndPasswordView: View {
    @ObservedObject var signUpModel: SignUpModel
    
    var body: some View {
        VStack {
            Text("이제 계정을 만들어보세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "사용자명", placeholder: "", text: $signUpModel.name)
                .padding(.top, 24)
                .padding(.bottom, 10)
            
            VStack(alignment: .leading) {
                Text("비밀번호")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                PasswordField(password: $signUpModel.password)
            }
                .padding(.bottom, 30)
            
            NavigationLink(destination: SignBirthView(signUpModel: signUpModel)) {
                SignButton(text: "다음")
            }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
    }
}
