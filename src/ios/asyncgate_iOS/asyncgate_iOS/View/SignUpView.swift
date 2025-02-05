//
//  SignUpView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

struct SignUpView: View {
    @StateObject var signUpModel = SignUpModel()
    
    var body: some View {
        NavigationStack {
            SignUpEmailView(signUpModel: signUpModel)
                .applyBackground()
            
        }
    }
}

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

struct SignUpNickNameView: View {
    @ObservedObject var signUpModel: SignUpModel
    
    var body: some View {
        VStack {
            Text("이름은 무엇인가요?")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "별명", text: $signUpModel.nickname)
                .padding(.top, 24)
                .padding(.bottom, 30)
            
            NavigationLink(destination: SignUpNameAndPasswordView(signUpModel: signUpModel)) {
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

struct SignUpNameAndPasswordView: View {
    @ObservedObject var signUpModel: SignUpModel
    
    var body: some View {
        VStack {
            Text("이제 계정을 만들어보세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
            
            SignTextField(stepCaption: "사용자명", text: $signUpModel.name)
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

struct SignBirthView: View {
    @ObservedObject var signUpModel: SignUpModel
    
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

#Preview {
    SignUpView()
}

