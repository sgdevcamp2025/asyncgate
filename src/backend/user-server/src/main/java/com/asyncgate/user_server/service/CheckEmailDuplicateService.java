package com.asyncgate.user_server.service;

import com.asyncgate.user_server.dto.response.CheckEmailDuplicateResponse;
import com.asyncgate.user_server.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckEmailDuplicateService {

    private final MemberRepository memberRepository;

    public CheckEmailDuplicateResponse execute(final String email) {
        return CheckEmailDuplicateResponse.builder()
                .isDuplicate(isDuplicatedEmail(email))
                .build();
    }

    private Boolean isDuplicatedEmail(
            final String email
    ) {
        return memberRepository.isExistByEmail(email);
    }
}
