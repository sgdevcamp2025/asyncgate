//
//  Color.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/4/25.
//

import SwiftUI

extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 6:
            (a, r, g, b) = (255, (int >> 16) & 0xFF, (int >> 8) & 0xFF, int & 0xFF)
        case 8:
            (a, r, g, b) = ((int >> 24) & 0xFF, (int >> 16) & 0xFF, (int >> 8) & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (255, 0, 0, 0)
        }
        self.init(.sRGB, red: Double(r) / 255, green: Double(g) / 255, blue: Double(b) / 255, opacity: Double(a) / 255)
    }
}

extension Color {
    static let colorBlack = Color(hex: "#1B1D1F")
    static let colorDart800 = Color(hex: "#1E1F22")
    static let colorDart700 = Color(hex: "#2B2D31")
    static let colorDart600 = Color(hex: "#2E3035")
    static let colorDart500 = Color(hex: "#383A40")
    static let colorDart400 = Color(hex: "#80848E")
    static let colorWhite = Color(hex: "#FFFFFF")
    static let colorMain = Color(hex: "#5865F2")
}
