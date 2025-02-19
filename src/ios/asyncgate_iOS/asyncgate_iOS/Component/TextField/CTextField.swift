//
//  CTextField.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

// MARK: TextField - 입력하는 텍스트필드
struct CTextField: View {
    var stepCaption: String
    var placeholder: String
    @Binding var text: String
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(stepCaption)
                .font(Font.pretendardSemiBold(size: 16))
                .foregroundColor(Color.colorDart400)
            
            TextField("", text: $text)
                .foregroundColor(.white)
                .padding()
                .background(Color(hex: "#111216"))
                .cornerRadius(20)
                .overlay(
                    Group {
                        if text.isEmpty {
                            Text(text)
                                .foregroundStyle(Color.colorDart400)
                                .padding(.leading, 15)
                        }
                    }
                    , alignment: .leading
                )
        }
    }
}
