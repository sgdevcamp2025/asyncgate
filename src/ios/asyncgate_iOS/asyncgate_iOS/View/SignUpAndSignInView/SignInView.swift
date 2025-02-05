//
//  SignInView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 로그인 화면
struct SignInView: View {
    @State private var email: String = ""
    @State private var password: String = ""
    
    var body: some View {
        VStack {
            Text("돌아오신 것을 환영해요!")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            Text("다시 만나다니 너무 반가워요!")
                .font(Font.pretendardBold(size: 16))
                .foregroundColor(Color.colorWhite)
          
            SignTextField(stepCaption: "계정정보", text: $email)
                .padding(.top, 24)
                .padding(.bottom, 10)
            
            VStack(alignment: .leading) {
                Text("비밀번호")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                PasswordField(password: $password)
            }
                .padding(.bottom, 30)
            
//            NavigationLink(destination: SignBirthView(signUpModel: signUpModel)) {
//                SignButton(text: "로그인")
//            }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
    }
}
