//
//  SignInView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 로그인 화면
struct SignInView: View {
    @StateObject var signInViewModel = SignInViewModel()
    
    var body: some View {
        VStack {
            Text("돌아오신 것을 환영해요!")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
                .padding(.bottom, 5)
            
            Text("다시 만나다니 너무 반가워요!")
                .font(Font.pretendardBold(size: 16))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "계정 정보", placeholder: "이메일 또는 전화번호", text: $signInViewModel.email)
                .padding(.top, 24)
                .padding(.bottom, 10)
            
            
            PasswordField(password: $signInViewModel.password)
            
                .padding(.bottom, 30)
            
            SignButtonStyle(text: "로그인")
                .onTapGesture {
                    signInViewModel.signInUser()
                }
                .navigationDestination(isPresented: $signInViewModel.isSignInSuccess) {
                    ContentView()
                }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
    }
}

#Preview {
    SignInView()
}
