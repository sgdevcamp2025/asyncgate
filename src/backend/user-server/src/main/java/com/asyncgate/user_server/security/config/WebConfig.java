package com.asyncgate.user_server.security.config;

import com.asyncgate.user_server.security.resolver.HttpMemberIDArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HttpMemberIDArgumentResolver memberIDArgumentResolver;

    public WebConfig(HttpMemberIDArgumentResolver memberIDArgumentResolver) {
        this.memberIDArgumentResolver = memberIDArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberIDArgumentResolver);
    }
}