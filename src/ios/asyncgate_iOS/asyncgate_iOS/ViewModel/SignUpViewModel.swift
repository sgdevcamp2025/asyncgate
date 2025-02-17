//
//  SignUpViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: ViewModel - 회원가입
class SignUpViewModel: ObservableObject {
    // Request 변수 - 회원가입용
    @Published var email: String = ""
    @Published var passWord: String = ""
    @Published var name: String = ""
    @Published var nickName: String = ""
    @Published var birth: String = ""
    
    // Response 변수
    @Published var errorMessage: String?
    @Published var emailRequestMessage: String?
    @Published var isVerificationCodeRequested: Bool = false
    @Published var isNotEmailDuplicated: Bool = false
    
    
    // MARK: 함수 - 회원가입 진행
    func signUpUser() {
        UserNetworkManager.shared.signUp(email: email, passWord: passWord, name: name, nickName: nickName, birth: birth) { result in
            switch result {
                case .success(_):
                DispatchQueue.main.async {
                    self.isVerificationCodeRequested = true
                    self.emailRequestMessage = "인증번호가 발송되었습니다."
                }
                
            case .failure(let signUpErrorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = signUpErrorResponse.error
                }
                print("SignUpViewModel - signUpUser() error : \(signUpErrorResponse)")
            }
        }
    }
    
    // MARK: 함수 - 이메일 중복 여부 확인
    func isDuplicatedEmail() {
        UserNetworkManager.shared.checkDuplicatedEmail(email: email) { result in
            switch result {
                case .success(let checkDuplicatedEmailResponse):
                if checkDuplicatedEmailResponse.result.isDuplicate {
                    DispatchQueue.main.async {
                        self.isNotEmailDuplicated = false
                    }
                } else {
                    DispatchQueue.main.async {
                        self.isNotEmailDuplicated = true
                        self.emailRequestMessage = "중복된 이메일입니다."
                    }
                }
               
            case .failure(let signUpErrorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = signUpErrorResponse.error
                }
                print("SignUpViewModel - isDuplicatedEmail() error : \(signUpErrorResponse)")
            }
        }
    }
}
