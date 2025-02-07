//
//  NotificationView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

struct NotificationView: View {
    var body: some View {
        VStack {
            HStack {
                Text("알림")
                    .foregroundStyle(Color.colorWhite)
                    .font(Font.pretendardBold(size: 20))
                
                Spacer()
                
                Button {
                    
                } label: {
                    Image(systemName: "ellipsis.circle")
                        .resizable()
                        .frame(width: 30, height: 30)
                }
            }
            
            Spacer()
            
            Text("아직 아무것도 없어요")
                .foregroundStyle(Color.colorWhite)
                .font(Font.pretendardBold(size: 18))
                .padding(.bottom, 10)
            
            Text("나중에 돌아와서 이벤트, 스트림 등의 알림을 확인하세요.")
                .foregroundStyle(Color.colorWhite)
                .font(Font.pretendardRegular(size: 16))
            
            Spacer()

        }
        .padding(15)
        .applyBackground()
    }
}

#Preview {
    NotificationView()
}
