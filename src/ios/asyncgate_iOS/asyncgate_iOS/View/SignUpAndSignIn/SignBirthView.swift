//
//  SignBirthView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 회원가입 - 생년월일 설정 View
struct SignBirthView: View {
    @ObservedObject var signUpModel: SignUpViewModel
    var date: Date = Date()
    
    var body: some View {
        VStack {
            Text("몇 살이신가요?")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "생년월일", placeholder: formattedDate, text: $signUpModel.birth)
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            Button {
                signUpModel.registerUser()
            } label: {
                SignButton(text: "계정 만들기")
            }
            
            if let errorMessage = signUpModel.errorMessage {
                Text(errorMessage)
                    .foregroundColor(.red)
            }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
        .navigationDestination(isPresented: $signUpModel.isSignUpSuccessful) {
            VerifyAuthenticationView(signUpModel: signUpModel)
                }
    }
    
    var formattedDate: String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy.MM.dd"
        return dateFormatter.string(from: date)
    }
    
}
