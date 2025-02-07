//
//  CreateGuildButton.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

struct CreateGuildButton: View {
    var imageName: String
    var text: String
    var imageWidth: CGFloat
    var imageHeight: CGFloat
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .foregroundStyle(Color.colorDart600)
                .frame(height: 70)
            
            HStack {
                Image(systemName: imageName)
                    .resizable()
                    .foregroundStyle(Color.colorWhite)
                    .frame(width: imageWidth, height: imageHeight)
                    .padding(.trailing, 5)
                
                Text(text)
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundStyle(Color.colorWhite)
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .resizable()
                    .foregroundStyle(Color.colorWhite)
                    .frame(width: 5, height: 10)
            }
            .padding()
        }
    }
    
}
