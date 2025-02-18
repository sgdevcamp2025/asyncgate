//
//  asyncgate_iOSApp.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/4/25.
//

import SwiftUI

@main
struct asyncgate_iOSApp: App {
    @StateObject var createGuildViewModel = CreateGuildViewModel()
    
    var body: some Scene {
        WindowGroup {
            CreateGuildLastView(createGuildViewModel: createGuildViewModel)
        }
    }
}
