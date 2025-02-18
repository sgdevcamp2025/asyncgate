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
    
    // ViewModel 호출 - 엑세스 토큰 사용
    private let accessTokenViewModel = AccessTokenViewModel.shared
    
    // MARK: 함수 - 길드 생성
    func createGuild(name: String, isPrivate: Bool, profileImage: UIImage?, completion: @escaping (Result<SuccessCreateGuildResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "hostUrl/guilds/guilds"
        
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
                    
                case .failure(_):
                    if let data = response.data {
                        do {
                            let errorResponse = try JSONDecoder().decode(OnlyHttpStatusResponse.self, from: data)
                            completion(.failure(errorResponse))
                        } catch {
                            completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                        }
                    } else {
                        completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                    }
                }
            }
        }
    }
    
    
    // MARK: Guild API
    
    // MARK: 함수 - 내 길드 목록 조회
    func loadMyGuildList(completion: @escaping (Result<SuccessLoadGuildListResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "hostUrl/guilds/guilds"
        
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
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(OnlyHttpStatusResponse.self, from: data)
                                completion(.failure(errorResponse))
                            } catch {
                                completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                            }
                        } else {
                            completion(.failure(OnlyHttpStatusResponse(httpStatus: 0)))
                        }
                    }
                }
        }
    }
}

