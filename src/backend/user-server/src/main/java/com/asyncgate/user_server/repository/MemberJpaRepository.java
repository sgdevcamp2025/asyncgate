package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, String> {

    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findById(String id);
}