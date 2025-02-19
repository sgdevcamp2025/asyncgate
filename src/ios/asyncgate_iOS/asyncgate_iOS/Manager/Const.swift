//
//  Const.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import Foundation

class Config {
    static let shared = Config()
    let hostUrl: String

    private init() {
        hostUrl = Bundle.main.infoDictionary?["hostUrl"] as? String ?? ""
    }
}
