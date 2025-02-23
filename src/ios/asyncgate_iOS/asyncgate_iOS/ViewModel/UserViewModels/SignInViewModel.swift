//
//  SignInViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: ViewModel - 로그인
class SignInViewModel: ObservableObject {
    // Request 변수
    @Published var email: String = ""
    @Published var password: String = ""
    
    // Response 변수
    @Published var errorMessage: String?
    
    @Published var isSignInSuccess: Bool = false
    
    // ViewModel - 엑세스 토큰
    private let auth = AccessTokenViewModel.shared
    
    // MARK: 함수 - 로그인 시도
    func signInUser() {
        UserServiceAPIManager.shared.signIn(email: email, passWord: password) { result in
            switch result {
            case .success(let signInResponse):
                if (200...299).contains(signInResponse.httpStatus) {
                    DispatchQueue.main.async {
                        self.isSignInSuccess = true
                        if let accessToken = signInResponse.result?.accessToken {
                            self.auth.saveToken(accessToken)
                            print("\(self.auth.accessToken ?? "")")
                            self.isSignInSuccess = true
                        }
                    }
                } else {
                    DispatchQueue.main.async {
                        self.errorMessage = signInResponse.message
                    }
                }
                
            case .failure(let signInErrorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = signInErrorResponse.error
                }
                print("SignInViewModel - signInUser() error : \(signInErrorResponse)")
            }
        }
    }
}
