//
//  BackgroundModifier.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: Modifier - 뷰에 배경색상 일괄 지정
struct BackgroundModifier: ViewModifier {
    func body(content: Content) -> some View {
        ZStack {
            Color(hex: "#1C1D23")
                .ignoresSafeArea()
            
            content
        }
    }
}

// MARK: Extension - View에서 적용하여 사용가능하도록 지정
extension View {
    func applyBackground() -> some View {
        self.modifier(BackgroundModifier())
    }
}

// MARK: Modifier - 뷰에 배경색상 일괄 지정
struct GuildBackgroundModifier: ViewModifier {
    func body(content: Content) -> some View {
        ZStack {
            Color(hex: "#131318")
                .ignoresSafeArea()
            
            content
        }
    }
}

// MARK: Extension - View에서 적용하여 사용가능하도록 지정
extension View {
    func applyGuildBackground() -> some View {
        self.modifier(GuildBackgroundModifier())
    }
}

