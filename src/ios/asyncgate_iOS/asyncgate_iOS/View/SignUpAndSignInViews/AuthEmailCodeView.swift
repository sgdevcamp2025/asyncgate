//
//  AuthEmailCodeView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 이메일 인증 코드 확인 View
struct AuthEmailCodeView: View {
    @ObservedObject var signUpViewModel: SignUpViewModel
    @StateObject var authEmailCodeViewModel = AuthEmailCodeViewModel()
    
    var body: some View {
        VStack {
            Text("이메일로 전송받은 인증 코드를 입력하세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
                .multilineTextAlignment(.center)
            
            SignTextField(stepCaption: "인증 코드", placeholder: "", text: $authEmailCodeViewModel.authenticationCode)
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            Button {
                authEmailCodeViewModel.email = signUpViewModel.email
                authEmailCodeViewModel.isEmailCodeMatched()
            } label: {
                SignButtonStyle(text: "확인")
            }
            
            if authEmailCodeViewModel.isEmailCodeAuthenticated {
                Text("이메일 인증되었습니다.")
                    .font(Font.pretendardBold(size: 14))
                    .foregroundColor(Color.colorGreen)
                
            } else if let errorMessage = authEmailCodeViewModel.errorMessage {
                Text(errorMessage)
                    .font(Font.pretendardBold(size: 14))
                    .foregroundColor(Color.colorRed)
            }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
        .fullScreenCover(isPresented: $authEmailCodeViewModel.isEmailCodeAuthenticated) {
            SignInView()
        }
    }
}

