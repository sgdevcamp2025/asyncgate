//
//  CToggle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

struct CToggle: View {
    var text: String
    @Binding var isPrivate: Bool
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .foregroundStyle(Color(hex: "#26272F"))
                .frame(height: 70)
            
            HStack {
                Image(systemName: "lock.fill")
                    .resizable()
                    .frame(width: 20, height: 25)
                    .foregroundStyle(Color(hex: "#C7C8CE"))
                
                Text(text)
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundStyle(Color(hex: "#E4E5E8"))
                    .padding(.leading, 5)
                
                Toggle("", isOn: $isPrivate)
                    .tint(Color.colorBlurple)
                    .padding()
            }
            .padding()
        }
    }
}
