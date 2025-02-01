package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.support.annotation.UseCase;
import org.springframework.web.multipart.MultipartFile;

@UseCase
public interface UpdateUserInfoUseCase {
    /**
     * 사용자 정보 수정
     * @param userId
     * @param name
     * @param nickname
     * @param profileImage
     */
    void execute(final String userId, final String name, final String nickname, final MultipartFile profileImage);
}
