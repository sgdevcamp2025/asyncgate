//
//  SignUpViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

class SignUpViewModel: ObservableObject {
    @Published var email: String = ""
    @Published var password: String = ""
    @Published var name: String = ""
    @Published var nickname: String = ""
    @Published var birth: String = ""
    
    @Published var errorMessage: String?
    @Published var isSignUpSuccessful: Bool = false
    
    func registerUser() {
        signUp(email: email, password: password, name: name, nickname: nickname, birth: birth) { success, message in
            DispatchQueue.main.async {
                if success {
                    self.isSignUpSuccessful = true
                } else {
                    self.errorMessage = message
                }
            }
        }
    }
}
