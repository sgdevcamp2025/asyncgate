//
//  GuildButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/16/25.
//

import SwiftUI

struct GuildButtonStyle: View {
    let maxLength: Int = 3
    var text: String
    
    var body: some View {
        ZStack {
            Circle()
                .frame(width: 46, height: 46)
                .foregroundStyle(Color.colorNewGuildButton)
            
            Text(text.count > maxLength ? text.prefix(maxLength) + "..." : text)
                .font(Font.pretendardSemiBold(size: 12))
                .foregroundStyle(Color.colorWhite)
        }
    }
}

