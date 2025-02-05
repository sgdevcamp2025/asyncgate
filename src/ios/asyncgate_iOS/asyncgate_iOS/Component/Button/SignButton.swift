//
//  SignButton.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: Button - 회원가입/로그인 시 사용되는 버튼 컴포넌트
struct SignButton: View {
    var text: String
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 4)
                .foregroundStyle(Color.colorMain)
                .frame(height: 40)
            
            Text(text)
                .font(Font.pretendardSemiBold(size: 15))
                .foregroundStyle(Color.colorWhite)
        }
    }
}
