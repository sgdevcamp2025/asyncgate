//
//  Config.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import Foundation

// MARK: API 주소 및 번들ID (.gitignore)
class Config {
    static let shared = Config()
    let hostUrl: String
    let bundleId: String

    private init() {
        hostUrl = Bundle.main.infoDictionary?["hostUrl"] as? String ?? ""
        bundleId = Bundle.main.infoDictionary?["bundleId"] as? String ?? ""
    }
}
