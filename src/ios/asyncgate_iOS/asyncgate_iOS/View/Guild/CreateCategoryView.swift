//
//  CreateCategoryView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

struct CreateCategoryView: View {
    @Environment(\.dismiss) var dismiss
    @StateObject var guildCategoryViewModel =  GuildCategoryViewModel()
    
    var guildId: String?
    
    var body: some View {
        NavigationStack {
            VStack {
                HStack {
                    Button {
                        dismiss()
                    } label: {
                        Image(systemName: "xmark")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundStyle(Color.colorWhite)
                    }
                    Spacer()
                    
                    Text("카테고리 만들기")
                        .font(Font.pretendardBold(size: 18))
                        .foregroundColor(Color.colorWhite)
            
                    Spacer()
                    
                    Button {
                        if let guildId = guildId {
                            guildCategoryViewModel.guildId = guildId
                            guildCategoryViewModel.createCategory()
                            dismiss()
                        }
                    } label: {
                        Text("만들기")
                            .font(Font.pretendardSemiBold(size: 16))
                            .foregroundStyle(Color(hex: "#6469A2"))
                    }
                }
                .padding(.bottom, 20)
                
                Divider()
                
                CTextField(stepCaption: "카테고리 이름", placeholder: "새로운 카테고리", text: $guildCategoryViewModel.name)

                Text("카테고리를 비공개로 만들면 서택한 멤버들과 역할만 이 카테고리를 볼 수 있어요. 이 설정은 이 카테고리에 동기화된 채널들에도 자동으로 적용돼요.")
                    .font(Font.pretendardSemiBold(size: 14))
                    .foregroundColor(Color.colorDart400)
                    .multilineTextAlignment(.leading)
                    .padding(.top, 10)
                    .padding(.bottom, 10)
                
                CToggle(text: "비공개 카테고리", isPrivate: $guildCategoryViewModel.isPrivate)
                
                Spacer()
            }
            .padding()
            .applyBackground()
            .navigationBarBackButtonHidden(true)
        }
    }
}

#Preview {
    CreateCategoryView()
}
