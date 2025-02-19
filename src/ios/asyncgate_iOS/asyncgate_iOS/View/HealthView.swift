//
//  HealthView.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

struct HealthView: View {
    @StateObject var healthViewModel = HealthViewModel()
    
    var body: some View {
        VStack {
            Text("\(healthViewModel.httpStatus)")
            Text(healthViewModel.result)
            Text(healthViewModel.time)
            Text(healthViewModel.errorMessage)
            Text(healthViewModel.message)
            
            Button {
                healthViewModel.checkHealth()
            } label: {
                Text("dddddddd")
            }
        }
    }
}
