//
//  ContentView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/4/25.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        TabView {
            GuildMainView()
            .tabItem {
              Image(systemName: "house.fill")
              Text("홈")
            }
            
            NotificationView()
            .tabItem {
              Image(systemName: "bell.fill")
              Text("알림")
            }
            
            UpdateUserInfoView()
            .tabItem {
              Image(systemName: "person.fill")
              Text("나")
            }
        }
    }
}

#Preview {
    ContentView()
}

