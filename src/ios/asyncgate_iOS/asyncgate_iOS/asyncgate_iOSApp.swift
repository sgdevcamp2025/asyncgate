//
//  asyncgate_iOSApp.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/4/25.
//

import SwiftUI

@main
struct asyncgate_iOSApp: App {
    private let accessTokenViewModel = AccessTokenViewModel.shared
    
    var body: some Scene {
        WindowGroup {
            if accessTokenViewModel.accessToken != nil {
                GuildMainView()
            } else {
                SignMainView()
            }
        }
    }
}
