//
//  Chat.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/28/25.
//

import SwiftUI

struct Chat: View {
    var profileImage: String
    var nickName: String
    
    var body: some View {
        HStack {
            VStack {
                Image(profileImage)
                    .resizable()
                    .frame(width: 45, height: 45)
                    .foregroundStyle(Color(hex: "#C7C8CE"))
                
                Spacer()
            }
            
            VStack(alignment: .leading) {
                HStack {
                    Text(nickName)
                        .font(Font.pretendardSemiBold(size: 15))
                        .foregroundStyle(Color.colorWhite)
                    
                    Text("날짜")
                        .font(Font.pretendardRegular(size: 13))
                        .foregroundStyle(Color(hex: "#C7C8CE"))
                    
                    Text("시간")
                        .font(Font.pretendardRegular(size: 13))
                        .foregroundStyle(Color(hex: "#C7C8CE"))
                }
                
                Text("안녕하세요? 제 말이 잘 들리시나요?")
                    .font(Font.pretendardRegular(size: 15))
                    .foregroundStyle(Color.colorWhite)
                Text("반갑습니다. 오늘도 즐거운 하루")
                    .font(Font.pretendardRegular(size: 15))
                    .foregroundStyle(Color.colorWhite)
                Text("안녕하세요? 안녕하세요? 안녕하세요? 안녕하세요? 인사를 잘 받는 건 예의입니다.")
                    .font(Font.pretendardRegular(size: 15))
                    .foregroundStyle(Color.colorWhite)
            }
            
        }
    }
}
