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
    
    let keychain = Keychain(service: "kk.asyncgate-iOS")
    
    @Published var accessToken: String?

    private init() {
        loadToken()
        print("\(accessToken ?? "")")
    }
    
    // MARK: 함수 - 엑세스 토큰 KeyChain에 저장
    func saveToken(_ token: String) {
        do {
            try keychain.set(token, key: "accessToken")
            self.accessToken = token
            
        } catch {
            print("Keychain save error: \(error)")
        }
    }
    
    // MARK: 함수 - 엑세스 토큰 가져오기
    func loadToken() {
        do {
            if let token = try keychain.getString("accessToken") {
                self.accessToken = token
            }
            
        } catch {
            print("Keychain load error: \(error)")
            self.accessToken = nil
        }
    }
    
    // MARK: 함수 - 엑세스 토큰 삭제
    func deleteToken() {
        do {
            try keychain.remove("accessToken")
            self.accessToken = nil
            
        } catch {
            print("Keychain remove error: \(error)")
        }
    }
}
