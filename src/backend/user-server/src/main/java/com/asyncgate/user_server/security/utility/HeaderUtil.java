package com.asyncgate.user_server.security.utility;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Authorization 헤더를 파싱하는 유틸리티 클래스
 */
public class HeaderUtil {

    public static Optional<String> refineHeader(final HttpServletRequest request, final String header, final String prefix) {
        String unpreparedToken = request.getHeader(header);

        if (!StringUtils.hasText(unpreparedToken)) {
            return Optional.empty();
        }

        // prefix가 존재하면 제거하고, 없으면 그대로 반환
        if (unpreparedToken.startsWith(prefix)) {
            return Optional.of(unpreparedToken.substring(prefix.length()));
        }

        return Optional.of(unpreparedToken);
    }
}