//
//  UserNetworkManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Alamofire

class UserNetworkManager {
    static let shared = UserNetworkManager()
    
    // MARK: 함수 - 회원가입 함수
    func signUp(email: String, passWord: String, name: String, nickName: String, birth: String, completion: @escaping (Result<SignUpResponse, SignUpErrorResponse>) -> Void) {
        let url = "hostUrl/sign-up"
        
        let parameters: [String: Any] = [
            "email": email,
            "password": passWord,
            "name": name,
            "nickname": nickName,
            "birth": birth
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SignUpResponse.self) { response in
                switch response.result {
                case .success(let signUpResponse):
                    completion(.success(signUpResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(SignUpErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "func 회원가입 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "func 회원가입 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 이메일 중복 확인 함수
    func checkDuplicatedEmail(email: String, completion: @escaping (Result<CheckDuplicatedEmailResponse, SignUpErrorResponse>) -> Void) {
        let url = "hostUrl/sign-up"
        
        let parameters: [String: Any] = [
            "email": email
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: URLEncoding.queryString)
            .validate()
            .responseDecodable(of: CheckDuplicatedEmailResponse.self) { response in
                switch response.result {
                case .success(let checkDuplicatedEmailResponse):
                    completion(.success(checkDuplicatedEmailResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(SignUpErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "이메일 중복 확인 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "이메일 중복 확인 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 이메일 인증 함수
    func authEmailCode(email: String, authenticationCode: String, completion: @escaping (Result<SignUpResponse, SignUpErrorResponse>) -> Void) {
        let url = "hostUrl/sign-up"
        
        let parameters: [String: Any] = [
            "email": email,
            "authentication_code": authenticationCode,
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SignUpResponse.self) { response in
                switch response.result {
                case .success(let signUpResponse):
                    completion(.success(signUpResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(SignUpErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "func 이메일 인증 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "func 이메일 인증 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 로그인 함수
    func signIn(email: String, passWord: String, completion: @escaping (Result<SignUpResponse, SignUpErrorResponse>) -> Void) {
        let url = "hostUrl/sign-up"
        
        let parameters: [String: Any] = [
            "email": email,
            "password": passWord,
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SignUpResponse.self) { response in
                switch response.result {
                case .success(let signUpResponse):
                    completion(.success(signUpResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(SignUpErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "func 이메일 인증 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(SignUpErrorResponse(timeStamp: "", path: "", status: 0, error: "func 이메일 인증 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
}
