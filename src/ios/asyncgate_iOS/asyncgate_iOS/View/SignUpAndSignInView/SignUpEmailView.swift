//
//  SignUpEmailView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 회원가입 - 이메일 설정 View
struct SignUpEmailView: View {
    @ObservedObject var signUpModel: SignUpModel
    
    var body: some View {
        VStack {
            Text("이메일 주소를 입력하세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            VStack(alignment: .leading) {
                Text("이메일")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                TextField("이메일", text: $signUpModel.email)
                    .keyboardType(.emailAddress)
                    .foregroundStyle(Color.colorWhite)
                    .padding()
                    .background(Color.colorDart500)
                    .cornerRadius(4)
            }
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            NavigationLink(destination: SignUpNickNameView(signUpModel: signUpModel)) {
                SignButton(text: "다음")
            }
            
            Spacer()
        }
        .padding(15)
    }
}
