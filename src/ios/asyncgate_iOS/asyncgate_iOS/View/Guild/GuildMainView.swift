//
//  GuildMainView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/7/25.
//

import SwiftUI

struct GuildMainView: View {
    @State private var isShowCreateGuildView: Bool = false
    
    var body: some View {
        NavigationStack {
            VStack {
                HStack {
                    ScrollView {
                        VStack(alignment: .leading) {
                            Button {
                                isShowCreateGuildView.toggle()
                            } label: {
                                ZStack {
                                    Circle()
                                        .frame(width: 46, height: 46)
                                        .foregroundStyle(Color.colorNewGuildButton)
                                    
                                    Image(systemName: "plus")
                                        .frame(width: 30, height: 30)
                                        .foregroundStyle(Color.colorGreen)
                                }
                            }
                        }
                    }
                    .padding(5)
                    .padding(.leading, 5)
                    
                    ZStack {
                        RoundedRectangle(cornerRadius: 20)
                            .foregroundStyle(Color.colorBG)
                            .frame(maxHeight: .infinity)
                    
                        ScrollView {
                            VStack {
                                Text("Hello, World!")
                            }
                        }
                        .frame(maxHeight: .infinity)
                    }
                }
            }
            .applyGuildBackground()
        }
        .fullScreenCover(isPresented: $isShowCreateGuildView) {
            CreateGuildView()
        }
    }
}

#Preview {
    GuildMainView()
}
