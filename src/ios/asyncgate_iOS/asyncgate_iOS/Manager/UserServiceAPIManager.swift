//
//  UserServiceAPIManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Alamofire

// MARK: Manager - User Service API 매니저
class UserNetworkManager {
    static let shared = UserNetworkManager()
    
    // ViewModel 호출 - 엑세스 토큰 사용
    private let accessTokenViewModel = AccessTokenViewModel.shared
    
    // MARK: 함수 - 서버 연동 확인
    func health(completion: @escaping (Result<SuccessResultStringResponse, ErrorResponse>) -> Void) {
        let url = "hostUrl/users/health"
        
        AF.request(url, method: .get, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SuccessResultStringResponse.self) { response in
                switch response.result {
                case .success(let healthResponse):
                    completion(.success(healthResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "health - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "health - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 회원가입
    func signUp(email: String, passWord: String, name: String, nickName: String, birth: String, completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "hostUrl/users/sign-up"
        
        let parameters: [String: Any] = [
            "email": email,
            "password": passWord,
            "name": name,
            "nickname": nickName,
            "birth": birth
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SuccessEmptyResultResponse.self) { response in
                switch response.result {
                case .success(let signUpResponse):
                    completion(.success(signUpResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "회원가입 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "회원가입 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 이메일 중복 확인
    func checkDuplicatedEmail(email: String, completion: @escaping (Result<CheckDuplicatedEmailResponse, ErrorResponse>) -> Void) {
        let url = "/users/validation/email"
        
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
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "이메일 중복 확인 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "이메일 중복 확인 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 이메일 인증
    func authEmailCode(email: String, authenticationCode: String, completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "hostUrl/users/sign-up"
        
        let parameters: [String: Any] = [
            "email": email,
            "authentication_code": authenticationCode,
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SuccessEmptyResultResponse.self) { response in
                switch response.result {
                case .success(let signUpResponse):
                    completion(.success(signUpResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "func 이메일 인증 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "func 이메일 인증 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 로그인
    func signIn(email: String, passWord: String, completion: @escaping (Result<SignInResponse, ErrorResponse>) -> Void) {
        let url = "hostUrl/users/sign-in"
        
        let parameters: [String: Any] = [
            "email": email,
            "password": passWord,
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SignInResponse.self) { response in
                switch response.result {
                case .success(let signInResponse):
                    completion(.success(signInResponse))
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "로그인 - 오류 발생", requestId: "")))
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "로그인 - 서버 응답 없음", requestId: "")))
                    }
                }
            }
    }
    
    // MARK: 함수 - 유저 정보 수정
    func updateUserInfo(name: String, nickName: String, profileImage: String, completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "hostUrl/users/info"
        
        let parameters: [String: Any] = [
            "name": name,
            "nickname": nickName,
            "profile_image": profileImage,
        ]
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)"
                // FIXME: 이미지 전달 가능한 값으로 수정
            ]
            
            // FIXME: 추후 이미지 전달 가능하게 수정
            AF.request(url, method: .patch, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessEmptyResultResponse.self) { response in
                    switch response.result {
                    case .success(let signUpResponse):
                        completion(.success(signUpResponse))
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                            } catch {
                                completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "유저정보 수정 - 오류 발생", requestId: "")))
                            }
                        } else {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "유저정보 수정 - 서버 응답 없음", requestId: "")))
                        }
                    }
                }
        }
        else {
            print("UserNetworkManager - updateUserInfo - accessToken 없음!")
        }
    }
    
    // MARK: 함수 - 회원 탈퇴
    func deleteUser(completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "hostUrl/users/auth"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)"
            ]
            
            AF.request(url, method: .delete, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessEmptyResultResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                            } catch {
                                completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "회원 탈퇴 - 오류 발생", requestId: "")))
                            }
                        } else {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "회원 탈퇴 - 서버 응답 없음", requestId: "")))
                        }
                    }
                }
        }
    }
}
