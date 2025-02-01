package com.asyncgate.user_server.security.info;

import com.asyncgate.user_server.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
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

    // 임시 권한 user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_USER");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(adminAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}