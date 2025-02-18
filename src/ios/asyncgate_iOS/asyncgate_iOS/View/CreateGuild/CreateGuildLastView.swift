//
//  CreateGuildLastView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI
import PhotosUI

// MARK: View - 서버 이름 및 이미지 선택 후 길드 생성
struct CreateGuildLastView: View {
    @ObservedObject var createGuildViewModel: CreateGuildViewModel
    @State var selectedPhoto: PhotosPickerItem?
    
    var body: some View {
        VStack {
            Text("서버를 만들어보세요")
                .font(Font.pretendardBold(size: 28))
                .foregroundColor(Color.colorWhite)
                .padding(.bottom, 10)
            
            Text("서버는 나와 친구들이 함께 어울리는 공간입니다.\n 내 서버를 만들고 대화를 시작해보세요.")
                .font(Font.pretendardSemiBold(size: 14))
                .foregroundColor(Color.colorDart400)
                .multilineTextAlignment(.center)
                .padding(.bottom, 37)
            
            PhotosPicker(
                selection: $selectedPhoto,
                matching: .images
            ) {
                choiceImageButtonStyle
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
                            if createGuildViewModel.name == "" {
                                Text("서버 이름")
                                    .foregroundStyle(Color.colorDart400)
                                    .padding(.leading, 15)
                            }
                        }
                        , alignment: .leading
                    )
            }
            .padding(.top, 13)
            .padding(.bottom, 30)
            
            Button {
                createGuildViewModel.createGuild()
            } label: {
                UsingButtonStyle(text: "서버 만들기", backgroundColor: Color.colorBlurple, textColor: Color.colorWhite, size: 14)
            }
            .navigationDestination(isPresented: $createGuildViewModel.isCreatedGuild) {
                ContentView()
            }
            
            if let errorMessage = createGuildViewModel.errorMessage {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .font(Font.pretendardRegular(size: 14))
                    .padding(.top, 10)
            }
            
            Spacer()
        }
        .onChange(of: selectedPhoto) { _, newValue in
            loadPhoto(from: newValue)
        }
        .padding()
        .applyBackground()
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(leading: BackButton(color: .white))
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
    
    // ButtonStyle - 이미지 선택하기 버튼 스타일
    var choiceImageButtonStyle: some View {
        ZStack {
            if let image = createGuildViewModel.profileImage {
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

#Preview {
    @Previewable @StateObject var createGuildViewModel = CreateGuildViewModel()
    CreateGuildLastView(createGuildViewModel: createGuildViewModel)
}
