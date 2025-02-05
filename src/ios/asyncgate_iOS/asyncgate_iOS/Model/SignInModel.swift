//
//  SignInModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

class SignInModel: ObservableObject {
    @Published var email: String = ""
    @Published var password: String = ""
    
    private let auth = Authentication()
    
    func logInInUser() {
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
