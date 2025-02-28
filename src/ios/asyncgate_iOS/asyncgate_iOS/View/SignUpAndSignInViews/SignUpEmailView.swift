//
//  SignUpEmailView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 회원가입 - 이메일 설정 View
struct SignUpEmailView: View {
    @ObservedObject var signUpViewModel: SignUpViewModel
    
    var body: some View {
        VStack {
            Text("이메일 주소를 입력하세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            VStack(alignment: .leading) {
                Text("이메일")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                TextField("", text: $signUpViewModel.email)
                    .keyboardType(.emailAddress)
                    .foregroundColor(.white)
                    .padding()
                    .background(Color.colorDart500)
                    .cornerRadius(4)
                    .overlay(
                        Group {
                            if signUpViewModel.email.isEmpty {
                                Text("이메일")
                                    .foregroundStyle(Color.colorDart400)
                                    .padding(.leading, 15)
                            }
                        }
                        , alignment: .leading
                    )
                
                if let meesage = signUpViewModel.emailRequestMessage {
                    Text(meesage)
                        .font(Font.pretendardBold(size: 13))
                        .foregroundColor(Color.colorRed)
                }
            }
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            Button {
                signUpViewModel.isDuplicatedEmail()
            } label: {
                SignButtonStyle(text: "다음")
            }
            .navigationDestination(isPresented: $signUpViewModel.isNotEmailDuplicated) {
                SignUpNickNameView(signUpViewModel: signUpViewModel)
            }
            
            Spacer()
        }
        .padding(15)
    }
}
