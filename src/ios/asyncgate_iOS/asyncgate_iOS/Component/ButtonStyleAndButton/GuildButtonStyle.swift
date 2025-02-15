//
//  GuildButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/16/25.
//

import SwiftUI

struct GuildButtonStyle: View {
    var text: String
    
    var body: some View {
        ZStack {
            Circle()
                .frame(width: 46, height: 46)
                .foregroundStyle(Color.colorNewGuildButton)
            
            Text(text)
                .font(Font.pretendardSemiBold(size: 15))
                .foregroundStyle(Color.colorWhite)
        }
    }
}

