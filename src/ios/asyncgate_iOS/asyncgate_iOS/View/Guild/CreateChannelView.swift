//
//  CreateChannelView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/19/25.
//

import SwiftUI

struct CreateChannelView: View {
    @Environment(\.dismiss) var dismiss
    @ObservedObject var guildChannelViewModel: GuildChannelViewModel
    @ObservedObject var guildDetailViewModel: GuildDetailViewModel
    
    var guildId: String?
    
    var body: some View {
        NavigationStack {
            VStack(alignment: .leading) {
                HStack {
                    Button {
                        dismiss()
                    } label: {
                        Image(systemName: "xmark")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundStyle(Color.colorWhite)
                    }
                    Spacer()
                    
                    Text("채널 만들기")
                        .font(Font.pretendardBold(size: 18))
                        .foregroundColor(Color.colorWhite)
            
                    Spacer()
                    
                    Button {
                       if let guildId = guildDetailViewModel.guildId {
                            guildChannelViewModel.guildId = guildId
                            guildChannelViewModel.createChannel()
                            dismiss()
                        }
                    } label: {
                        Text("만들기")
                            .font(Font.pretendardSemiBold(size: 16))
                            .foregroundStyle(Color(hex: "#6469A2"))
                    }
                }
                .padding(.bottom, 10)
                
                Divider()
                    .padding(.bottom, 10)
                
                CTextField(stepCaption: "채널 이름", placeholder: "새로운 채널", text: $guildChannelViewModel.name)
                
                Text("채널 유형")
                    .font(Font.pretendardSemiBold(size: 16))
                    .foregroundColor(Color.colorDart400)
                
                Button {
                    guildChannelViewModel.channelType = "TEXT"
                } label: {
                    ZStack {
                        RoundedRectangle(cornerRadius: 20)
                            .foregroundStyle(Color(hex: "#26272F"))
                            .frame(height: 60)
                        
                        HStack {
                            Text("#")
                                .font(Font.pretendardRegular(size: 30))
                                .foregroundStyle(Color(hex: "#C7C8CE"))
                            
                            VStack(alignment: .leading) {
                                Text("텍스트")
                                    .font(Font.pretendardSemiBold(size: 16))
                                    .foregroundStyle(Color(hex: "#C7C8CE"))
                                
                                Text("이미지, GIF, 스티커, 의견, 농담을 올려보세요")
                                    .font(Font.pretendardSemiBold(size: 11))
                                    .foregroundStyle(Color.colorDart400)
                                    .multilineTextAlignment(.leading)
                            }
                            .padding(.leading, 2)
                            
                            Spacer()
                            
                            if guildChannelViewModel.channelType == "TEXT" {
                                selectedButtonStyle
                            } else {
                                notSelectedButtonStyle
                            }
                        }
                        .padding()
                    }
                }
                
                Button {
                    guildChannelViewModel.channelType = "VOICE"
                } label: {
                    ZStack {
                        RoundedRectangle(cornerRadius: 20)
                            .foregroundStyle(Color(hex: "#26272F"))
                            .frame(height: 60)
                        
                        HStack {
                            Image(systemName: "speaker.1.fill")
                                .resizable()
                                .frame(width: 22, height: 22)
                                .foregroundStyle(Color(hex: "#C7C8CE"))
                         
                            VStack(alignment: .leading) {
                                Text("음성")
                                    .font(Font.pretendardSemiBold(size: 16))
                                    .foregroundStyle(Color(hex: "#C7C8CE"))
                                
                                Text("음성, 영상, 화면 공유로 함께 어울리세요")
                                    .font(Font.pretendardSemiBold(size: 11))
                                    .foregroundStyle(Color.colorDart400)
                                    .multilineTextAlignment(.leading)
                            }
                            .padding(.leading, 2)
                            
                            Spacer()
                            
                            if guildChannelViewModel.channelType == "TEXT" {
                                notSelectedButtonStyle
                            } else {
                                selectedButtonStyle
                            }
                        }
                        .padding()
                    }
                }

                Text("채널을 비공개로 만들면 선택한 멤버들과 역할만 이 채널을 볼 수 있어요.")
                    .font(Font.pretendardSemiBold(size: 14))
                    .foregroundColor(Color.colorDart400)
                    .multilineTextAlignment(.leading)
                    .padding(.top, 10)
                    .padding(.bottom, 10)
                
                CToggle(text: "비공개 채널", isPrivate: $guildChannelViewModel.isPrivate)
                
                Spacer()
            }
            .padding()
            .applyBackground()
            .navigationBarBackButtonHidden(true)
        }
    }
    
    // ButtonStyle - 텍스트/음성 선택 여부 - 선택 O
    var selectedButtonStyle: some View {
        ZStack {
            Circle()
                .foregroundStyle(Color.colorWhite)
                .frame(width: 30, height: 30)
            
            Image(systemName: "record.circle.fill")
                .resizable()
                .frame(width: 30, height: 30)
                .foregroundStyle(Color(hex: "#5865F2"))
        }
    }
    
    // ButtonStyle - 텍스트/음성 선택 여부 - 선택 X
    var notSelectedButtonStyle: some View {
        Image(systemName: "circle")
            .resizable()
            .frame(width: 30, height: 30)
            .foregroundStyle(Color(hex: "#C7C8CE"))
    }
}

