//
//  AuthenticationViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import KeychainAccess

// MARK: 로그인 후 엑세스 토큰 KeyChain에 저장
class AccessTokenViewModel: ObservableObject {
    let keychain = Keychain(service: "kk.asyncgate-iOS")
    
    @Published var accessToken: String?

    init() {
        loadToken()
    }
    
    func saveToken(_ token: String) {
        do {
            try keychain.set(token, key: "accessToken")
            self.accessToken = token
        } catch {
            print("Keychain save error: \(error)")
        }
    }
    
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
    
    func deleteToken() {
        do {
            try keychain.remove("accessToken")
            self.accessToken = nil
        } catch {
            print("Keychain remove error: \(error)")
        }
    }
}
