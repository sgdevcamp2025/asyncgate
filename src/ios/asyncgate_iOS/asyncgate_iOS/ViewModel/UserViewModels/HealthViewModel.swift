//
//  HealthViewModel.swift
//  asyncgate_iOS
//
//  Created by kdk on 2/18/25.
//

import SwiftUI

// MARK: ViewModel - 길드 생성
class HealthViewModel: ObservableObject {
    // Request 변수
    @Published var httpStatus: Int = 0
    @Published var message: String = ""
    @Published var time: String = ""
    @Published var result: String = ""
    
    @Published var errorMessage: String = ""
    
    func checkHealth() {
        UserNetworkManager.shared.health { result in
            switch result {
            case .success(let successResponse):
                DispatchQueue.main.async {
                    self.httpStatus = successResponse.httpStatus
                    self.message = successResponse.message
                    self.time = successResponse.time
                    self.result = successResponse.result
                }
                
                print("HealthViewModel - checkHealth() - 서버 연결 성공 \(successResponse)")
                
            case .failure(let errorResponse):
                DispatchQueue.main.async {
                    self.errorMessage = "\(errorResponse)"
                }
                print("HealthViewModel - checkHealth() - 에러 발생 \(errorResponse)")
            }
        }
    }
}
