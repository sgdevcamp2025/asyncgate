//
//  UsingButton.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: Button - 반복적으로 사용되는 버튼 스타일 컴포넌트
struct UsingButton: View {
    var text: String
    var backgroundColor: Color
    var textColor: Color
    var size: CGFloat
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .foregroundStyle(backgroundColor)
                .frame(height: 40)
            
            Text(text)
                .font(Font.pretendardSemiBold(size: size))
                .foregroundStyle(textColor)
        }
    }
}
