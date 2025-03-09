package com.asyncgate.user_server.security.resolver;

import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.security.constant.Constants;
import com.asyncgate.user_server.security.annotation.MemberID;
import com.asyncgate.user_server.security.exception.CommonException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * HTTP 요청에서 Member ID를 추출하는 HandlerMethodArgumentResolver
 */
@Component
public class HttpMemberIDArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(String.class) // 🔥 String으로 변경
                && parameter.hasParameterAnnotation(MemberID.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Object memberId = webRequest.getAttribute(
                Constants.MEMBER_ID_ATTRIBUTE_NAME,
                WebRequest.SCOPE_REQUEST
        );

        if (memberId == null) {
            throw new CommonException(FailType.ACCESS_DENIED);
        }
        return memberId.toString();
    }
}
