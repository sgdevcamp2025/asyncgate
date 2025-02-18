//
//  CategoryButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

struct CategoryButtonStyle: View {
    var categoryName: String
    
    var body: some View {
        ZStack {
            Rectangle()
                .frame(width: 300, height: 30)
                .foregroundStyle(Color.colorBG)
            
            HStack {
                Image(systemName: "chevron.down")
                // chevron.right로 누르면 수정
                    .resizable()
                    .frame(width: 6, height: 4)
                    .foregroundStyle(Color.colorWhite)
                
                Text(categoryName)
                    .foregroundStyle(Color.colorGrayImage)
                    .font(Font.pretendardSemiBold(size: 14))
                
                Spacer()
            }
        }
    }
    
}
