//
//  AuthEmailCodeViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/17/25.
//

import SwiftUI

// MARK: ViewModel - 이메일 인증
class AuthEmailCodeViewModel: ObservableObject {
    // Request 변수 - 이메일, 인증코드
    @Published var email: String = ""
    @Published var authenticationCode: String = ""
    
    // Response 변수 - 에러 메시지(String?), 인증코드 일치여부 확인(Bool)
    @Published var errorMessage: String?
    @Published var isEmailCodeAuthenticated: Bool = false
    
    // MARK: 함수 - 이메일 인증코드 일치 여부 확인
    func isEmailCodeMatched() {
        UserNetworkManager.shared.authEmailCode(email: email, authenticationCode: authenticationCode) { result in
            switch result {
            case .success(_):
                DispatchQueue.main.async {
                    self.isEmailCodeAuthenticated = true
                }
                
            case .failure(let SignUpErrorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = SignUpErrorResponse.error
                }
            }
        }
    }
}

