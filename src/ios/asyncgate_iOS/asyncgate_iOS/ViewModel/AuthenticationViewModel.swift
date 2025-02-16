//
//  AuthenticationViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI
import Alamofire

// MARK: 이메일 인증코드 일치여부 확인 class
class AuthenticationViewModel: ObservableObject {
    @Published var isAuthenticated: Bool = false
    @Published var errorMessage: String? = nil
    
    // MARK: 함수 - 이메일 인증코드 확인 함수
    func verifyAuthenticationCode(email: String, authenticationCode: String) {
        let url = "hostUrl/validation/authentication-code"
        
        let parameters: [String: Any] = [
            "email": email,
            "authentication_code": authenticationCode
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: AuthenticationResponse.self){ response in
                switch response.result {
                case .success(let authenticationResponse):
                    if authenticationResponse.success {
                        self.isAuthenticated = true
                    } else {
                        print("이메일 인증 실패: \(authenticationResponse.message ?? "에러")")
                        self.isAuthenticated = false
                    }
                    
                case .failure(let error):
                    print("이메일 인증 에러 발생: \(error.localizedDescription)")
                    self.isAuthenticated = false
                }
            }
    }
    
}
