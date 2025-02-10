//
//  GuildIconButton.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/10/25.
//

import SwiftUI

struct GuildIconButton: View {
    var imageName: String
    var width: CGFloat
    var height: CGFloat
    
    var body: some View {
        ZStack {
            Circle()
                .frame(width: 32, height: 32)
                .foregroundStyle(Color(hex: "#383A43"))
            
            Image(systemName: imageName)
                .resizable()
                .frame(width: width, height: height)
                .foregroundStyle(Color(hex: "#C7C8CE"))
        }
    }
}
