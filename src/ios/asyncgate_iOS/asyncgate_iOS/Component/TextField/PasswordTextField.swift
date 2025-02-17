//
//  PasswordTextField.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: TextField(SecureField) - 공통으로 사용되는 비밀번호용 텍스트필드 컴포넌트
struct PasswordField: View {
    @Binding var password: String
    @State private var isSecure: Bool = true

    var body: some View {
        HStack {
            if isSecure {
                SecureField("", text: $password)
                    .foregroundColor(.white)
                    .background(Color.colorDart500)
                    .cornerRadius(4)
                    .overlay(
                        Group {
                            if password.isEmpty {
                                Text("비밀번호")
                                    .foregroundStyle(Color.colorDart400)
                                    .padding(.leading, 2)
                            }
                        }
                        , alignment: .leading
                    )
            } else {
                TextField("비밀번호", text: $password)
                
            }
            
            Button {
                isSecure.toggle()
            } label: {
                Image(systemName: isSecure ? "eye.slash" : "eye")
                    .foregroundStyle(Color.colorWhite)
            }
        }
        .foregroundStyle(Color.colorWhite)
        .padding()
        .background(Color.colorDart500)
        .cornerRadius(4)
    }
}

