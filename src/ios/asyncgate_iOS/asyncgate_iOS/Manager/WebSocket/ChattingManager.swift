//
//  ChattingManager.swift
//  asyncgate_iOS
//
//  Created by kdk on 3/9/25.
//

import Alamofire

// MARK: Manager
class ChattingManager {
    static let shared = ChattingManager()
    
    // 호출 - 엑세스 토큰 사용 및 API 주소
    private let accessTokenViewModel = AccessTokenViewModel.shared
    private let hostUrl = Config.shared.hostUrl
    
    // MARK: 함수 - 채팅 불러오기
    func getChattingList(page: Int, size: Int, channelId: String, completion: @escaping (Result<ChatResponse, Error>) -> Void) {
        let url = "https://\(hostUrl)/chats/chat/direct?page=\(page)&size=\(size)&channel-id=\(channelId)"
        
        print(url)
        
        if let accessToken = accessTokenViewModel.accessToken {
            let headers: HTTPHeaders = [
                "Authorization": "Bearer \(accessToken)",
            ]
            
            AF.request(url, method: .get, encoding: JSONEncoding.default, headers: headers)
                .validate()
                .responseDecodable(of: ChatResponse.self) { response in
                    switch response.result {
                    case .success(let successResponse):
                        completion(.success(successResponse))
                        print("결과 확인확인 \(successResponse)")
                        
                    case .failure(let error):
                        completion(.failure(error))
                        print("에러발생발생발생 \(error)")
                    }
                }
        }
    }
}
