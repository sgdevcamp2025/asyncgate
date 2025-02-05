//
//  SignTextField.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: TextField - 공통으로 사용되는 텍스트필드 컴포넌트
struct SignTextField: View {
    var stepCaption: String
    @Binding var text: String
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(stepCaption)
                .font(Font.pretendardSemiBold(size: 16))
                .foregroundColor(Color.colorDart400)
            
            TextField(stepCaption, text: $text)
                .padding()
                .foregroundColor(.white)
                .background(Color.colorDart500)
                .cornerRadius(4)
        }
    }
}

