//
//  ContentView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/4/25.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        Text("폰트 이름 출력하기")
            .font(Font.PretendardExtraLight)
            .onAppear {
                for family in UIFont.familyNames {
                    print(family)
                    for font in UIFont.fontNames(forFamilyName: family) {
                        print("  - \(font)")
                    }
                }
            }
    }
}

#Preview {
    ContentView()
}
