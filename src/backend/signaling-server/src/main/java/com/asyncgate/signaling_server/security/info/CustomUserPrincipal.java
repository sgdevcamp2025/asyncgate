package com.asyncgate.signaling_server.security.info;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Spring Security에서 사용하는 UserDetails를 구현한 클래스
 */
@Builder
@RequiredArgsConstructor
public class CustomUserPrincipal implements UserDetails {

    @Getter
    private final String id;

    public static CustomUserPrincipal create(String id) {
        return CustomUserPrincipal.builder()
                .id(id)
                .build();
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword() {
        return null;
    }

    // 임시 권한 user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_USER");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(adminAuthority);

        return authorities;
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