//
//  ChoiceImageButtonStyle.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/20/25.
//

import SwiftUI

// MARK: ButtonStyle - 이미지 선택하기 버튼 스타일
struct ChoiceImageButtonStyle: View {
    var image: UIImage?
    
    var body: some View {
        ZStack {
            if let image = image {
                Image(uiImage: image)
                    .resizable()
                    .clipShape(Circle())
                    .frame(width: 80, height: 80)
                
            } else {
                Circle()
                    .stroke(
                            Color.colorGray,
                            style: StrokeStyle(lineWidth: 2, dash: [6, 8])
                        )
                    .foregroundStyle(Color.colorBG)
                    .frame(width: 80, height: 80)
                
                VStack {
                    Image(systemName: "camera.fill")
                        .foregroundStyle(Color.colorGray)
                        .padding(.bottom, 2)
                    
                    Text("올리기")
                        .foregroundStyle(Color.colorGray)
                        .font(Font.pretendardRegular(size: 12))
                }
            }
        }
    }
}
