//
//  BackButton.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: Button - 뒤로가기 버튼
struct BackButton: View {
    @Environment(\.dismiss) var dismiss
    var color: Color = .white
    
    var body: some View {
        Button {
            dismiss()
        } label: {
            Image(systemName: "chevron.left")
                .font(.system(size: 15, weight: .bold))
                .foregroundColor(color)
        }
    }
}
