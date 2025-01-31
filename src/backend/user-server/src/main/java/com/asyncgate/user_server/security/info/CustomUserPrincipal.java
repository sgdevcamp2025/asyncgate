package com.asyncgate.user_server.security.info;

import com.asyncgate.user_server.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Spring Security에서 사용하는 UserDetails를 구현한 클래스
 */
@Builder
@RequiredArgsConstructor
public class CustomUserPrincipal implements UserDetails {

    @Getter
    private final String id;
    @Getter
    private final String email;
    private final String password;

    public static CustomUserPrincipal create(MemberEntity member) {
        return CustomUserPrincipal.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }

    @Override
    public String getUsername() {
        return id;
    }

    // 현재 role이 없으므로 빈 리스트 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}