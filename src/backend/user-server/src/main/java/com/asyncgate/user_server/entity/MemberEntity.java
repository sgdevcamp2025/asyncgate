package com.asyncgate.user_server.entity;

import com.asyncgate.user_server.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @Column(name = "id")
    private String id;

    /* -------------------------------------------- */
    /* Security Column ---------------------------- */
    /* -------------------------------------------- */
    @Column(name = "email", length = 100, nullable = false, updatable = false)
    private String email;

    @Column(name = "password", length = 320, nullable = false)
    private String password;

    @Column(name = "device_token", length = 320)
    private String deviceToken;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "profile_img_url", length = 320)
    private String profileImgUrl;

    @Column(name = "birth", nullable = false)
    private LocalDate birth;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    private MemberEntity(String id, String email, String password, String deviceToken, String name, String nickname, String profileImgUrl, LocalDate birth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.deviceToken = deviceToken;
        this.name = name;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.birth = birth;
    }
}
