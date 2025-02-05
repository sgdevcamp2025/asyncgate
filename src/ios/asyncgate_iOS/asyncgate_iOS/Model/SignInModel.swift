//
//  SignInModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: 이메일, 패스워드로 로그인용 Class
class SignInModel: ObservableObject {
    @Published var email: String = ""
    @Published var password: String = ""
    
    private let auth = Authentication()
    
    func logInInUser() {
        print("로그인 시도: \(email), \(password)")
        
        signIn(email: email, password: password) { token in
            if let token = token {
                print("로그인 성공, token: \(token)")
                
                self.auth.saveToken(token)
                
            } else {
                print("로그인 실패")
            }
        }
    }
}
