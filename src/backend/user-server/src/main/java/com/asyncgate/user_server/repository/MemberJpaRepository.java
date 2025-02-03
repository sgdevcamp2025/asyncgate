package com.asyncgate.user_server.repository;

import com.asyncgate.user_server.entity.MemberEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, String> {

    @Modifying
    @Query("UPDATE MemberEntity m SET m.deleted = true WHERE m.id = :id")
    void softDeleteById(@Param("id") String id);

    @Query("SELECT m FROM MemberEntity m WHERE m.id = :id AND m.deleted = false")
    Optional<MemberEntity> findByNotDeletedId(@Param("id") String id);

    @Query("SELECT m FROM MemberEntity m WHERE m.email = :email AND m.deleted = false")
    Optional<MemberEntity> findByNotDeletedEmail(@Param("email") String email);
}