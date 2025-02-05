//
//  AuthenticationService.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Alamofire

// MARK: 함수 - 회원가입 함수
func signUp(email: String, password: String, name: String, nickname: String, birth: String, completion: @escaping (Bool, String?) -> Void) {
    let url = "(hostUrl)/sign-up"
    
    let parameters: [String: Any] = [
        "email": email,
        "password": password,
        "name": name,
        "nickname": nickname,
        "birth": birth
    ]
    
    AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
        .validate()
        .responseDecodable(of: SignUPResponse.self) { response in
            switch response.result {
            case .success(let signUpResponse):
                if signUpResponse.success {
                    completion(true, nil)
                } else {
                    completion(false, signUpResponse.message)
                }
            case .failure(let error):
                completion(false, error.localizedDescription)
            }
        }
}

// MARK: 함수 - 로그인 함수
func signIn(email: String, password: String, completion: @escaping (String?) -> Void) {
    let url = "hostUrl/sign-in"
    
    let parameters: [String: Any] = [
        "email": email,
        "password": password
    ]
    
    AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
        .validate()
        .responseDecodable(of: SignInResponse.self) { response in
            switch response.result {
            case .success(let data):
                completion(data.result.accessToken)
                
            case .failure(_):
                completion(nil)
            }
        }
}

