//
//  SignUpView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/5/25.
//

import SwiftUI

// MARK: View - 회원가입 Main View
struct SignUpView: View {
    @StateObject var signUpModel = SignUpViewModel()
    
    var body: some View {
        NavigationStack {
            SignUpEmailView(signUpModel: signUpModel)
                .applyBackground()
                .navigationBarBackButtonHidden(true)
                .navigationBarItems(leading: BackButton(color: .white))
        }
    }
}

#Preview {
    SignUpView()
}
