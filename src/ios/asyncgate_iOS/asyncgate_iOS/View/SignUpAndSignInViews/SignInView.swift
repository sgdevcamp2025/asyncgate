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
                .padding(.top, 10)
                .padding(.bottom, 5)
            
            Text("다시 만나다니 너무 반가워요!")
                .font(Font.pretendardBold(size: 16))
                .foregroundColor(Color.colorWhite)
            
            VStack(alignment: .leading) {
                Text("계정 정보")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                TextField("", text: $signInViewModel.email)
                    .keyboardType(.emailAddress)
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.colorDart500)
                    .cornerRadius(4)
                    .overlay(
                        Group {
                            if signInViewModel.email.isEmpty {
                                Text("이메일")
                                    .foregroundStyle(Color.colorDart400)
                                    .padding(.leading, 15)
                            }
                        }
                        , alignment: .leading
                    )
            }
            
            PasswordField(password: $signInViewModel.password)
                .padding(.bottom, 30)
            
            Button {
                signInViewModel.signInUser()
            } label: {
                SignButtonStyle(text: "로그인")
            }
            .navigationDestination(isPresented: $signInViewModel.isSignInSuccess) {
                ContentView()
            }
            
            if let message = signInViewModel.errorMessage {
                Text(message)
                    .font(Font.pretendardBold(size: 14))
                    .foregroundColor(Color.colorRed)
            }
            
            Spacer()
        }
        .padding()
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
        .fullScreenCover(isPresented: $signInViewModel.isSignInSuccess) {
            ContentView()
        }
    }
}

#Preview {
    SignInView()
}
