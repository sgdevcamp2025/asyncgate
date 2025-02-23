//
//  UserServiceAPIManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Alamofire

// MARK: Manager - User Service API 매니저
class UserServiceAPIManager {
    static let shared = UserServiceAPIManager()

    // 호출 - 엑세스 토큰 사용 및 API 주소
    private let accessTokenViewModel = AccessTokenViewModel.shared
    private let hostUrl = Config.shared.hostUrl
    
    // MARK: 함수 - 서버 연동 확인
    func health(completion: @escaping (Result<SuccessResultStringResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/health"
        
        AF.request(url, method: .get, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SuccessResultStringResponse.self) { response in
                switch response.result {
                case .success(let healthResponse):
                    completion(.success(healthResponse))
                    print("Response status code: \(response)")
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                            print("Response status code: \(response)")
                            
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                            print("Response status code: \(response)")
                                                    
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                        print("Response code: \(response)")
                                                
                    }
                }
            }
    }
    
    // MARK: 함수 - 임시 회원가입
    func signUp(email: String, passWord: String, name: String, nickName: String, birth: String, completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/sign-up"
        
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
                    print("알ㄴ알ㅇㄹㅇ: \(response)")
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                            print("Response ww code: \(response)")
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                        print("Response ss code: \(response)")
                    }
                }
            }
    }
    
    // MARK: 함수 - 이메일 중복 검사
    func checkDuplicatedEmail(email: String, completion: @escaping (Result<CheckDuplicatedEmailResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/validation/email"
        
        let parameters: [String: Any] = [
            "email": email
        ]
        
        AF.request(url, method: .post, parameters: parameters, encoding: URLEncoding.queryString)
            .validate()
            .responseDecodable(of: CheckDuplicatedEmailResponse.self) { response in
                switch response.result {
                case .success(let checkDuplicatedEmailResponse):
                    completion(.success(checkDuplicatedEmailResponse))
                    print("ㄴㄴㄴㄴㄴㄴ: \(response)")
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                            print("Response wwwwwwwww code: \(response)")
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                        print("Response llllllll code: \(response)")
                    }
                }
            }
    }
    
    // MARK: 함수 - 이메일 인증번호 인증
    func authEmailCode(email: String, authenticationCode: String, completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/validation/authentication-code"
        
        let parameters: [String: Any] = [
            "email": email,
            "authentication_code": authenticationCode,
        ]
        
        print("email: \(email)")
        print("authenticationCode: \(authenticationCode)")
        
        AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default)
            .validate()
            .responseDecodable(of: SuccessEmptyResultResponse.self) { response in
                switch response.result {
                case .success(let signUpResponse):
                    completion(.success(signUpResponse))
                    print("Response status code: \(response)")
                  
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                            print("Response status code: \(response)")
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                            print("Response status code: \(response)")
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                        print("Response status code: \(response)")
                    }
                }
            }
    }
    
    // MARK: 함수 - 로그인
    func signIn(email: String, passWord: String, completion: @escaping (Result<SignInResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/sign-in"
        
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
                    print("Response ㅈㅈㅈㅈㅂㅂㅂ code: \(response)")
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                            print("Response ㄴㄴㄴ code: \(response)")
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                            print("Response ㅈㅈㅈ code: \(response)")
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                        print("Response ㄷㄷㄷ code: \(response)")
                    }
                }
            }
    }
    
    // MARK: 함수 - 유저 정보 수정
    func updateUserInfo(name: String, nickName: String, profileImage: UIImage?, completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/info"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
                "Content-Type": "multipart/form-data"
            ]
            
            AF.upload(multipartFormData: { multipartFormData in
                multipartFormData.append(name.data(using: .utf8)!, withName: "name")
                multipartFormData.append(nickName.data(using: .utf8)!, withName: "nickName")
              
                if let image = profileImage?.pngData() {
                    multipartFormData.append(image, withName: "profile_image", fileName: "\(image).png", mimeType: "image/png")
                }
            }, to: url, usingThreshold: UInt64.init(), method: .patch, headers: headers)
            .validate()
            .responseDecodable(of: SuccessEmptyResultResponse.self) { response in
                switch response.result {
                case .success(let successResponse):
                    completion(.success(successResponse))
                    print("Response code: \(response)")
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                            completion(.failure(errorResponse))
                            print("Response ㅈㅈㅈ code: \(response)")
                        } catch {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                            print("Response ㅁㅁㅁㅁㅁ code: \(response)")
                        }
                    } else {
                        completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 1, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                        print("Response ㄴ린ㅇㄹㅇ널 code: \(response)")
                    }
                }
            }
        }
    }
    
    // MARK: 함수 - 회원 탈퇴
    func deleteUser(completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/auth"
        
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
                        print("Response ㅈㅈㅈ code: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                                print("Response ㅈㅈㅈ code: \(response)")
                            } catch {
                                completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                            }
                        } else {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                        }
                    }
                }
        }
    }
    
    // MARK: 함수 - 디바이스 토큰 업데이트
    func updateDeviceToken(deviceToken: String, completion: @escaping (Result<SuccessEmptyResultResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/users/device-token"
        
        let parameters: [String: Any] = [
            "device_token": deviceToken
            ]
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)"
            ]
            
            AF.request(url, method: .patch, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessEmptyResultResponse.self) { response in
                    switch response.result {
                    case .success(let signUpResponse):
                        completion(.success(signUpResponse))
                        print("디바이스 토큰 업데이트: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                            } catch {
                                completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "오류가 발생했습니다.", requestId: "")))
                                print("Response ww code: \(response)")
                            }
                        } else {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", requestId: "")))
                            print("Response ss code: \(response)")
                        }
                    }
                }
        }
    }
}
