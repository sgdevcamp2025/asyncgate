//
//  GuildMemberServiceAPIManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import Alamofire

class GuildMemberServiceAPIManager {
    static let shared = GuildServiceAPIManager()
    
    // ViewModel 호출 - 엑세스 토큰 사용
    private let accessTokenViewModel = AccessTokenViewModel.shared
    
    private let hostUrl = Config.shared.hostUrl
    
    // MARK: 함수 - 길드 초대 전송
    func sendGuildInvitation(guildId: String, targetUserId: String, completion: @escaping (Result<SuccessResultStringResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/\(guildId)/invitations?targetUserId=\(targetUserId)"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .post, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessResultStringResponse.self) { response in
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
    
    // MARK: 함수 - 길드 초대 수락
    func acceptGuildInvitation(guildId: String, completion: @escaping (Result<SuccessResultStringResponse, OnlyHttpStatusResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/\(guildId)/invitations/accept)"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .patch, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessResultStringResponse.self) { response in
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
    
    // MARK: 함수 - 길드 초대 거절
    func rejectGuildInvitation(guildId: String, completion: @escaping (Result<SuccessResultStringResponse, Error>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/\(guildId)/invitations/accept)"
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .patch, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: SuccessResultStringResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        
                    case .failure(let error):
                        completion(.failure(error))
                    }
                }
        }
    }
    
    // MARK: 함수 - 길드 초대 취소
    func deleteGuildInvitation(guildId: String, completion: @escaping (Result<SuccessResultStringResponse, Error>) -> Void) {
        let url = "https://\(hostUrl)/guilds/guilds/\(guildId)/invitations"
        
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
                        
                    case .failure(let error):
                        completion(.failure(error))
                    }
                }
        }
    }
}
