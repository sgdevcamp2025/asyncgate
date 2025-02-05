//
//  SignText.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: Text - 현재 단계 설명 텍스트 컴포넌트
struct SignText {
    var stepDescription: String
    
    var body: some View {
        Text(stepDescription)
            .font(Font.pretendardBold(size: 28))
            .foregroundColor(Color.colorWhite)
    }
}
