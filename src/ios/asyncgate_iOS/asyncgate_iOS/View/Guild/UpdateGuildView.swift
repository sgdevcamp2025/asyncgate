//
//  UpdateGuildView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/20/25.
//

import SwiftUI
import PhotosUI

// MARK: View - 길드 수정 뷰
struct UpdateGuildView: View {
    @Environment(\.dismiss) var dismiss
    @ObservedObject var createGuildViewModel: CUDGuildViewModel
    @ObservedObject var guildListViewModel: GuildListViewModel
    
    @State var selectedPhoto: PhotosPickerItem?
    
    var currentGuildName: String?
    
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
                    
                    Text("서버 수정하기")
                        .font(Font.pretendardBold(size: 18))
                        .foregroundColor(Color.colorWhite)
            
                    Spacer()
                    
                    Button {
                        createGuildViewModel.patchGuild()
                        dismiss()
                    } label: {
                        Text("수정하기")
                            .font(Font.pretendardSemiBold(size: 16))
                            .foregroundStyle(Color(hex: "#6469A2"))
                    }
                }
                .padding(.bottom, 10)
                
                Divider()
                    .padding(.bottom, 10)
                
                PhotosPicker(
                    selection: $selectedPhoto,
                    matching: .images
                ) {
                    ChoiceImageButtonStyle(image: createGuildViewModel.profileImage)
                }
                
                VStack(alignment: .leading) {
                    Text("서버 이름")
                        .font(Font.pretendardSemiBold(size: 16))
                        .foregroundColor(Color.colorDart400)
                    
                    TextField("", text: $createGuildViewModel.name)
                        .foregroundColor(.white)
                        .padding()
                        .background(Color(hex: "#111216"))
                        .cornerRadius(20)
                        .overlay(
                            Group {
                                if let currentGuildName = currentGuildName {
                                    Text(currentGuildName)
                                        .foregroundStyle(Color.colorDart400)
                                        .padding(.leading, 15)
                                }
                            }
                            , alignment: .leading
                        )
                }
                .padding(.top, 13)
                .padding(.bottom, 6)
                
                CToggle(text: "개인용 서버 여부", isPrivate: $createGuildViewModel.isPrivate)
                    .padding(.bottom, 10)
                
                if let errorMessage = createGuildViewModel.errorMessage {
                    Text(errorMessage)
                        .foregroundColor(.red)
                        .font(Font.pretendardRegular(size: 14))
                        .padding(.top, 10)
                }
             
                Spacer()
            }
            .applyBackground()
        }
        .onChange(of: selectedPhoto) { _, newValue in
            loadPhoto(from: newValue)
        }
        .applyBackground()
        .padding()
        .navigationBarBackButtonHidden(true)
    }
    
    // 함수 - PhotosPickerItem 타입의 이미지를 UIImage로 변경하는 함수
    private func loadPhoto(from photo: PhotosPickerItem?) {
        guard let photo = photo else { return }
        
        photo.loadTransferable(type: Data.self) { result in
            if case .success(let data) = result, let data, let image = UIImage(data: data) {
                DispatchQueue.main.async {
                    createGuildViewModel.profileImage = image
                }
            }
        }
    }
}

