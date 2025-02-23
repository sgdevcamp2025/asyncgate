//
//  ChannelGuildServiceAPIManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import Alamofire

class ChannelGuildServiceAPIManager {
    static let shared = ChannelGuildServiceAPIManager()
    
    // ViewModel 호출 - 엑세스 토큰 사용
    private let accessTokenViewModel = AccessTokenViewModel.shared
    
    private let hostUrl = Config.shared.hostUrl
    
    // MARK: 함수 - 채널 생성
    func createGuildChannel(name: String, guildId: String, categoryId: String, channelType: String, isPrivate: Bool, completion: @escaping (Result<ChannelResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/channel"
        
        let parameters: [String: Any] = [
            "name": name,
            "guildId": guildId,
            "categoryId": categoryId,
            "channelType": channelType,
            "private": isPrivate
        ]
        
        print("guildId: \(guildId)")
        print("categoryId: \(categoryId)")
        print("isPrivate: \(isPrivate)")
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .post, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: ChannelResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        print("ABOUT RESPONSE: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                                print("ABOUT RESPONSE: \(response)")
                            } catch {
                                completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "ChannelGuildServiceAPIManager - createGuildChannel() - 에러 발생", requestId: "")))
                                print("ABOUT RESPONSE: \(response)")
                            }
                        } else {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 1, error: "ChannelGuildServiceAPIManager - createGuildChannel() - 서버 응답 없음", requestId: "")))
                            print("ABOUT RESPONSE: \(response)")
                        }
                    }
                }
        }
    }
    
    // MARK: 함수 - 채널 수정
    func updateGuildChannel(guildId: String, categoryId: String, channelId: String, name: String, topic: String, isPrivate: Bool, completion: @escaping (Result<ChannelResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/channel/\(guildId)/\(categoryId)/\(channelId)"
        
        let parameters: [String: Any] = [
            "name": name,
            "topic": topic,
            "private": isPrivate
        ]
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .patch, parameters: parameters, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: ChannelResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        print("ABOUT RESPONSE: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                                print("ABOUT RESPONSE: \(response)")
                            } catch {
                                completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "GuildServiceAPIManager - updateGuildChannel() - 에러 발생", requestId: "")))
                                print("ABOUT RESPONSE: \(response)")
                            }
                        } else {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 1, error: "GuildServiceAPIManager - updateGuildChannel() - 서버 응답 없음", requestId: "")))
                            print("ABOUT RESPONSE: \(response)")
                        }
                    }
                }
        }
    }
    
    // MARK: 함수 - 채널 삭제
    func deleteGuildChannel(guildId: String, categoryId: String, channelId: String, completion: @escaping (Result<SuccessResultStringResponse, ErrorResponse>) -> Void) {
        let url = "https://\(hostUrl)/guilds/channel/\(guildId)/\(categoryId)/\(channelId)"
        
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
                        print("ABOUT RESPONSE: \(response)")
                        
                    case .failure(_):
                        if let data = response.data {
                            do {
                                let errorResponse = try JSONDecoder().decode(ErrorResponse.self, from: data)
                                completion(.failure(errorResponse))
                                print("ABOUT RESPONSE: \(response)")
                            } catch {
                                completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 0, error: "GuildServiceAPIManager - createGuildCategory() - 에러 발생", requestId: "")))
                                print("ABOUT RESPONSE: \(response)")
                            }
                        } else {
                            completion(.failure(ErrorResponse(timeStamp: "", path: "", status: 1, error: "GuildServiceAPIManager - createGuildCategory() - 서버 응답 없음", requestId: "")))
                            print("ABOUT RESPONSE: \(response)")
                        }
                    }
                }
        }
    }
}
