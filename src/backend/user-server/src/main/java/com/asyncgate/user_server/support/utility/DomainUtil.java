package com.asyncgate.user_server.support.utility;

import com.asyncgate.user_server.domain.Member;
import com.asyncgate.user_server.domain.AuthenticationCode;
import com.asyncgate.user_server.domain.TemporaryMember;
import com.asyncgate.user_server.entity.MemberEntity;
import com.asyncgate.user_server.entity.redis.AuthenticationCodeEntity;
import com.asyncgate.user_server.entity.redis.TemporaryMemberEntity;

public class DomainUtil {
    public static class MemberMapper {
        public static MemberEntity toEntity(final Member member) {
            return MemberEntity.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .deviceToken(member.getDeviceToken())
                    .birth(member.getBirth())
                    .build();
        }

        public static Member toDomain(final MemberEntity entity) {
            return Member.builder()
                    .id(entity.getId())
                    .email(entity.getEmail())
                    .password(entity.getPassword())
                    .name(entity.getName())
                    .nickname(entity.getNickname())
                    .deviceToken(entity.getDeviceToken())
                    .birth(entity.getBirth())
                    .build();
        }
    }

    public static class TemporaryMemberMapper {
        public static TemporaryMemberEntity toEntity(final TemporaryMember member) {
            return TemporaryMemberEntity.builder()
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .name(member.getName())
                    .nickname(member.getNickname())
                    .deviceToken(member.getDeviceToken())
                    .birth(member.getBirth())
                    .build();
        }

        public static TemporaryMember toDomain(final TemporaryMemberEntity entity) {
            return TemporaryMember.builder()
                    .email(entity.getEmail())
                    .password(entity.getPassword())
                    .name(entity.getName())
                    .nickname(entity.getNickname())
                    .deviceToken(entity.getDeviceToken())
                    .birth(entity.getBirth())
                    .build();
        }
    }

    public static class AuthenticationCodeMapper {
        public static AuthenticationCodeEntity toEntity(final AuthenticationCode code) {
            return AuthenticationCodeEntity.builder()
                    .id(code.getId())
                    .code(code.getCode())
                    .build();
        }

        public static AuthenticationCode toDomain(final AuthenticationCodeEntity entity) {
            return AuthenticationCode.builder()
                    .id(entity.getId())
                    .code(entity.getCode())
                    .build();
        }
    }
}
