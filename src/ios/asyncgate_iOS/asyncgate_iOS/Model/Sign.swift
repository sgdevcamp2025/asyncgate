//
//  Sign.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import Foundation

struct SignInResponse: Codable {
    let result: SignInRequest
}

struct SignInRequest: Codable {
    let accessToken: String
}

struct SignUPResponse: Codable {
    let success: Bool
    let message: String
}
