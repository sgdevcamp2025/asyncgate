//
//  CreateGuildView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

struct CreateGuildView: View {
    @Environment(\.dismiss) var dismiss
    
    var body: some View {
        NavigationStack {
            VStack {
                Text("서버를 만들어보세요")
                    .font(Font.pretendardBold(size: 28))
                    .foregroundColor(Color.colorWhite)
                    .padding(.bottom, 10)
                
                Text("서버는 나와 친구들이 함께 어울리는 공간입니다.\n 내 서버를 만들고 대화를 시작해보세요.")
                    .font(Font.pretendardSemiBold(size: 14))
                    .foregroundColor(Color.colorDart400)
                    .multilineTextAlignment(.center)
                    .padding(.bottom, 30)
                
                NavigationLink(destination: ChoiceGuildTypeView()) {
                    CreateGuildButtonStyle(imageName: "keyGrab",text: "직접 만들기", imageWidth: 48, imageHeight: 44)
                }
                
                Spacer()
                
                VStack {
                    Text("이미 초대장을 받으셨나요?")
                        .font(Font.pretendardSemiBold(size: 20))
                        .foregroundColor(Color.colorGrayCaption)
                        .padding(.bottom, 10)
                    
                    Button {
                        // FIXME: 수정 예정
                    } label: {
                                        UsingButtonStyle(text: "서버 참가하기", backgroundColor: Color.colorBlurple, textColor: Color.colorWhite, size: 14)
                    }
                }
                
            }
            .padding()
            .applyBackground()
            .navigationBarBackButtonHidden(true)
            .navigationBarItems(leading:
                                    Button {
                dismiss()
            } label: {
                Image(systemName: "xmark")
                    .font(.system(size: 15, weight: .bold))
                    .foregroundStyle(Color.colorWhite)
            })
        }
    }
}

#Preview {
    CreateGuildView()
}
