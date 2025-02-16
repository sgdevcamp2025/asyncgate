//
//  channelNameButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/16/25.
//

import SwiftUI

struct channelNameButtonStyle: View {
    var channelName: String
    
    var body: some View {
        HStack {
            Text(channelName)
                .foregroundStyle(Color.colorWhite)
                .font(Font.pretendardBold(size: 17))
            
            Image(systemName: "chevron.right")
                .resizable()
                .frame(width: 5, height: 6)
                .foregroundColor(Color(hex: "#9597A3"))
        }
    }
}
