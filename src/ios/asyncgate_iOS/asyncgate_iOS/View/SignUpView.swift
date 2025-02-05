//
//  SignUpView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

struct SignUpView: View {
    @State var text: String = ""
    
    var body: some View {
        ZStack {
            Color.colorBlack
                .ignoresSafeArea()
            
            EmailView
        }
    }
    
    var EmailView: some View {
        VStack {
            Text("이메일 주소를 입력하세요")
                .font(Font.pretendardBold(size: 24))
                .foregroundColor(Color.colorWhite)
            
            VStack(alignment: .leading) {
                Text("이메일")
                    .font(Font.pretendardRegular(size: 14))
                    .foregroundColor(Color.colorDart400)
                
                TextField("이메일", text: $text)
                    .foregroundStyle(Color.colorDart600)
                    .padding()
                    .background(Color.colorDart500)
                    .cornerRadius(4)
            }
            
            Button {
                
            } label: {
                ZStack {
                    RoundedRectangle(cornerRadius: 4)
                        .foregroundStyle(Color.colorMain)
                        .frame(height: 40)
                    
                    Text("다음")
                        .font(Font.pretendardSemiBold(size: 14))
                        .foregroundStyle(Color.colorWhite)
                }
            }
        }
        .padding()
    }
}

#Preview {
    SignUpView()
}

