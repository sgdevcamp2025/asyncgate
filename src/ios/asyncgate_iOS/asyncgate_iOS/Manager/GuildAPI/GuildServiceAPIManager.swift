//
//  GuildServiceAPIManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/17/25.
//

import Alamofire

// MARK: Manager - Guild Service API 매니저
class GuildServiceAPIManager {
    static let shared = GuildServiceAPIManager()
    
    // 호출 - 엑세스 토큰 사용 및 API 주소
    private let accessTokenViewModel = AccessTokenViewModel.shared
    private let hostUrl = Config.shared.hostUrl
    
    // MARK: 함수 - 길드 생성
    func createGuild(name: String, isPrivate: Bool, profileImage: UIImage?, completion: @escaping (Result<SuccessCreateGuildResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
                "Content-Type": "multipart/form-data"
            ]
            
            AF.upload(multipartFormData: { multipartFormData in
                multipartFormData.append(name.data(using: .utf8)!, withName: "name")
                multipartFormData.append("\(isPrivate)".data(using: .utf8)!, withName: "isPrivate")
                
                if let image = profileImage?.pngData() {
                    multipartFormData.append(image, withName: "profileImage", fileName: "\(image).png", mimeType: "image/png")
                }
            }, to: url, usingThreshold: UInt64.init(), method: .post, headers: headers)
            .validate()
            .responseDecodable(of: SuccessCreateGuildResponse.self) { response in
                switch response.result {
                case .success(let successResponse):
                    completion(.success(successResponse))
                    print("Response  code: \(response)")
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(OnlyHttpStatusResponse.self, from: data)
                            completion(.failure(errorResponse))
                            print("Response ㅈㅈㅈ code: \(response)")
                        } catch {
                            completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                            print("Response ㅁㅁㅁㅁㅁ code: \(response)")
                        }
                    } else {
                        completion(.failure(OnlyHttpStatusResponse(httpStatus: 1)))
                        print("Response ㄴ린ㅇㄹㅇ널 code: \(response)")
                    }
                }
            }
        }
    }
    
    // MARK: 함수 - 길드 정보 수정
    func updateGuild(guildId: String, name: String, isPrivate: Bool, profileImage: UIImage?, completion: @escaping (Result<SuccessCreateGuildResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/\(guildId)"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
                "Content-Type": "multipart/form-data"
            ]
            
            AF.upload(multipartFormData: { multipartFormData in
                multipartFormData.append(name.data(using: .utf8)!, withName: "name")
                multipartFormData.append("\(isPrivate)".data(using: .utf8)!, withName: "isPrivate")
                
                if let image = profileImage?.pngData() {
                    multipartFormData.append(image, withName: "profileImage", fileName: "\(image).png", mimeType: "image/png")
                }
            }, to: url, usingThreshold: UInt64.init(), method: .patch, headers: headers)
            .validate()
            .responseDecodable(of: SuccessCreateGuildResponse.self) { response in
                switch response.result {
                case .success(let successResponse):
                    completion(.success(successResponse))
                    print("Response status code: \(response)")
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(OnlyHttpStatusResponse.self, from: data)
                            completion(.failure(errorResponse))
                            print("Response status code: \(response)")
                        } catch {
                            completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                            print("Response status code: \(response)")
                        }
                    } else {
                        completion(.failure(OnlyHttpStatusResponse(httpStatus: 1)))
                        print("Response status code: \(response)")
                    }
                }
            }
        }
    }
    
    // MARK: 함수 - 길드 삭제
    func deleteGuild(guildId: String, completion: @escaping (Result<SuccessResultStringResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/\(guildId)"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .delete, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessResultStringResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        print("Response status code: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(OnlyHttpStatusResponse.self, from: data)
                                completion(.failure(errorResponse))
                                print("Response status code: \(response)")
                            } catch {
                                completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                                print("Response status code: \(response)")
                            }
                        } else {
                            completion(.failure(OnlyHttpStatusResponse(httpStatus: 1)))
                            print("Response status code: \(response)")
                        }
                    }
                }
        }
    }
    
    // MARK: 함수 - 내 길드 목록 조회
    func loadMyGuildList(completion: @escaping (Result<SuccessLoadGuildListResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .get, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessLoadGuildListResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        print("Response status code: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(OnlyHttpStatusResponse.self, from: data)
                                completion(.failure(errorResponse))
                                print("Response status code: \(response)")
                            } catch {
                                completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                                print("Response status code: \(response)")
                            }
                        } else {
                            completion(.failure(OnlyHttpStatusResponse(httpStatus: 1)))
                            print("Response status code: \(response)")
                        }
                    }
                }
        }
    }
    
    // MARK: 함수 - 랜덤 길드 조회
    func loadMyGuildListRandom(limit: Int, completion: @escaping (Result<SuccessLoadGuildListResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/rand?limit=\(limit)"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .get, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessLoadGuildListResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        print("Response status code: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(OnlyHttpStatusResponse.self, from: data)
                                completion(.failure(errorResponse))
                                print("Response status code: \(response)")
                            } catch {
                                completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                                print("Response status code: \(response)")
                            }
                        } else {
                            completion(.failure(OnlyHttpStatusResponse(httpStatus: 1)))
                            print("Response status code: \(response)")
                        }
                    }
                }
        }
    }
    
    // MARK: 함수 - 길드 단일 조회
    func fetchGuildInfo(guildId: String, completion: @escaping (Result<GuildDetailResponse, FourErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/\(guildId)"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .get, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: GuildDetailResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        print("Response status code: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(FourErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                            } catch {
                                completion(.failure(FourErrorResponse(timeStamp: "", status: 0, error: "오류가 발생했습니다.", path: "")))
                                print("Response status code: \(response)")
                            }
                        } else {
                            completion(.failure(FourErrorResponse(timeStamp: "", status: 0, error: "서버와 연결할 수 없습니다. 다시 시도해주세요.", path: "")))
                            print("Response status code: \(response)")
                        }
                    }
                }
        }
    }
}

