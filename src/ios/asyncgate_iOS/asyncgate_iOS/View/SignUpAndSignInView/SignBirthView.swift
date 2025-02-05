//
//  SignBirthView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 회원가입 - 생년월일 설정 View
struct SignBirthView: View {
    @ObservedObject var signUpModel: SignUpModel
    var date: Date = Date()
    
    var body: some View {
        VStack {
            Text("몇 살이신가요?")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "생년월일", text: $signUpModel.birth)
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            NavigationLink(destination: SignUpNameAndPasswordView(signUpModel: signUpModel)) {
                SignButton(text: "계정 만들기")
            }
            
            Spacer()
        }
        .padding(15)
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
    }
    
}
