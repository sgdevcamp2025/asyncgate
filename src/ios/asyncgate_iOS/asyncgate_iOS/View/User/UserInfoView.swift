//
//  UserInfoView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/20/25.
//

import SwiftUI

struct UserInfoView: View {
    @State private var isEditProfile: Bool = false
    
    var body: some View {
        VStack(alignment: .leading) {
            profileImage
            
            Button {
                isEditProfile.toggle()
            } label: {
                UsingButtonStyle(text: "프로필 편집하기", backgroundColor: Color.colorBlurple, textColor: Color.colorWhite, size: 14)
            }
            
            Spacer()
        }
        .padding()
        .applyBackground()
        .fullScreenCover(isPresented: $isEditProfile) {
            UpdateUserInfoView()
        }
    }
    
    var profileImage: some View {
        ZStack {
            Circle()
                .stroke(
                        Color.colorGray,
                        style: StrokeStyle(lineWidth: 2, dash: [6, 8])
                    )
                .foregroundStyle(Color.colorBG)
                .frame(width: 100, height: 100)
                .padding(.bottom, 10)
            
            Image(systemName: "person.fill")
                .resizable()
                .frame(width: 50, height: 50)
                .foregroundStyle(Color.colorWhite)
        }
    }
}

#Preview {
    UserInfoView()
}
