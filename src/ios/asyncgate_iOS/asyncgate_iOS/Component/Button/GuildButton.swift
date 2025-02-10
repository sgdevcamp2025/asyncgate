//
//  GuildButton.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/10/25.
//

import SwiftUI

struct GuildButton: View {
    var imageName: String
    var color: Color
    
    var body: some View {
        ZStack {
            Circle()
                .frame(width: 46, height: 46)
                .foregroundStyle(Color.colorNewGuildButton)
            
            Image(systemName: imageName)
                .frame(width: 30, height: 30)
                .foregroundStyle(color)
        }
    }
}
