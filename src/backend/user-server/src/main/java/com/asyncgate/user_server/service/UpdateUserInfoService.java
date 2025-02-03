package com.asyncgate.user_server.service;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.repository.MemberRepository;
import com.asyncgate.user_server.support.utility.S3Util;
import com.asyncgate.user_server.usecase.UpdateUserInfoUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateUserInfoService implements UpdateUserInfoUseCase {

    private final MemberRepository memberRepository;
    private final S3Util s3Util;

    @Value("${cloud.aws.s3.profile.default.url}")
    private String defaultProfileImageUrl;

    @Override
    @Transactional
    public void execute(final String userId, final String name, final String nickname, final MultipartFile profileImage) {
        String profileImageUrl = getProfileImageUrl(profileImage);

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserServerException(FailType.MEMBER_NOT_FOUND));

        member.update(name, nickname, profileImageUrl);

        memberRepository.save(member);
    }

    private String getProfileImageUrl(final MultipartFile profileImage) {
        if (profileImage != null && !profileImage.isEmpty()) {
            return s3Util.uploadFile(profileImage, Member.class.getName());
        } else {
            return defaultProfileImageUrl;
        }
    }
}
