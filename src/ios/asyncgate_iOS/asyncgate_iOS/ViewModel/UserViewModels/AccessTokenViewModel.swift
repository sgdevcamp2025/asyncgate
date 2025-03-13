//
//  AuthenticationViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import KeychainAccess
import SwiftUI

// MARK: ViewModel - 엑세스 토큰을 KeyChain으로 관리
class AccessTokenViewModel: ObservableObject {
    static let shared = AccessTokenViewModel()
    
    @Published var accessToken: String?
    
    private let bundleId: String
    let keychain: Keychain
    
    private init() {
        self.bundleId = Config.shared.bundleId
        self.keychain = Keychain(service: self.bundleId)
        
        loadToken()
    }
    
    // MARK: 함수 - 엑세스 토큰 KeyChain에 저장
    func saveToken(_ token: String) {
        do {
            try keychain.set(token, key: "accessToken")
            self.accessToken = token
            
        } catch {
            print("AccessTokenViewModel - Keychain save 에러: \(error)")
        }
    }
    
    // MARK: 함수 - 엑세스 토큰 가져오기
    func loadToken() {
        do {
            if let token = try keychain.getString("accessToken") {
                self.accessToken = token
            }
            
        } catch {
            print("AccessTokenViewModel - Keychain load 에러: \(error)")
            self.accessToken = nil
        }
    }
    
    // MARK: 함수 - 엑세스 토큰 삭제
    func deleteToken() {
        do {
            try keychain.remove("accessToken")
            self.accessToken = nil
            
        } catch {
            print("AccessTokenViewModel - Keychain delete 에러: \(error)")
        }
    }
}
