//
//  UpdateUserInfoView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/17/25.
//

import SwiftUI
import PhotosUI

// MARK: View - 프로필 수정 뷰
struct UpdateUserInfoView: View {
    @Environment(\.dismiss) var dismiss
    @StateObject var userInfoViewModel = UserInfoViewModel()
    @State var selectedPhoto: PhotosPickerItem?
    
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
                    
                    Text("프로필 수정하기")
                        .font(Font.pretendardBold(size: 18))
                        .foregroundColor(Color.colorWhite)
                    
                    Spacer()
                    
                    Button {
                        userInfoViewModel.updateUserInfos()
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
                    ChoiceImageButtonStyle(image: userInfoViewModel.profileImage)
                }
                
                VStack(alignment: .leading) {
                    Text("이름")
                        .font(Font.pretendardSemiBold(size: 16))
                        .foregroundColor(Color.colorDart400)
                    
                    TextField("", text: $userInfoViewModel.name)
                        .foregroundColor(.white)
                        .padding()
                        .background(Color(hex: "#111216"))
                        .cornerRadius(20)
                    
                    Text("닉네임")
                        .font(Font.pretendardSemiBold(size: 16))
                        .foregroundColor(Color.colorDart400)
                        .padding(.top, 13)
                    
                    TextField("", text: $userInfoViewModel.nickName)
                        .foregroundColor(.white)
                        .padding()
                        .background(Color(hex: "#111216"))
                        .cornerRadius(20)
                }
                .padding(.top, 13)
                .padding(.bottom, 6)
                
                Spacer()
                
                Button {
                    userInfoViewModel.deleteUserInfo()
                    dismiss()
                } label: {
                    VStack {
                        Text("탈퇴하기")
                            .font(Font.pretendardSemiBold(size: 16))
                            .foregroundStyle(Color(hex: "#6469A2"))
                            .padding()
                        
                        Text("누르면 바로 탈퇴되니 주의하세요.")
                            .font(Font.pretendardSemiBold(size: 14))
                            .foregroundStyle(Color.colorDart400)
                    }
                }
                
                Spacer()
            }
            .applyBackground()
        }
        .onChange(of: selectedPhoto) { _, newValue in
            loadPhoto(from: newValue)
        }
        .padding()
        .applyBackground()
        .navigationBarBackButtonHidden(true)
    }
    
    // 함수 - PhotosPickerItem 타입의 이미지를 UIImage로 변경하는 함수
    private func loadPhoto(from photo: PhotosPickerItem?) {
        guard let photo = photo else { return }
        
        photo.loadTransferable(type: Data.self) { result in
            if case .success(let data) = result, let data, let image = UIImage(data: data) {
                DispatchQueue.main.async {
                    userInfoViewModel.profileImage = image
                }
            }
        }
    }
}

#Preview {
    UpdateUserInfoView()
}
