//
//  VerifyAuthenticationView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

struct VerifyAuthenticationView: View {
    @ObservedObject var signUpModel: SignUpViewModel
    @StateObject var authViewModel = AuthenticationViewModel()
    
    @State private var authenticationCode: String = ""
    
    
    var body: some View {
        VStack {
            Text("이메일로 전송받은 인증 코드를 입력하세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "인증 코드", placeholder: "", text: $authenticationCode)
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            Button {
                authViewModel.verifyAuthenticationCode(email: signUpModel.email, authenticationCode: authenticationCode)
            } label: {
                SignButton(text: "확인")
            }
            
            if authViewModel.isAuthenticated {
                Text("인증되었습니다.")
                    .font(Font.pretendardBold(size: 15))
                    .foregroundColor(Color.colorGreen)
            } else if let errorMessage = authViewModel.errorMessage {
                Text(errorMessage)
                    .font(Font.pretendardBold(size: 15))
                    .foregroundColor(Color.colorRed)
            }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
    }
}
