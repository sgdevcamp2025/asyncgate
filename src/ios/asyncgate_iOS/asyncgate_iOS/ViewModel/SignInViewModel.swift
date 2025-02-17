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
    
    // ViewModel - 엑세스 토큰 저장
    private let auth = AccessTokenViewModel()
    
    // MARK: 함수 - 로그인 시도
    func signInUser() {
        print("로그인 시도: \(email), \(password)")
        
        UserNetworkManager.shared.signIn(email: email, passWord: password) { result in
            switch result {
            case .success(let signInResponse):
                DispatchQueue.main.async {
                    self.auth.saveToken(signInResponse.result.accessToken)
                    self.isSignInSuccess = true
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
