package com.asyncgate.guild_server.repository;

import com.asyncgate.guild_server.domain.DirectMember;
import com.asyncgate.guild_server.support.utility.DomainUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DirectMemberRepositoryImpl implements DirectMemberRepository {

    private final DirectMemberJpaRepository jpaRepository;
    private final DirectMemberQueryDslRepository queryDslRepository;

    @Override
    public void saveAll(final List<DirectMember> directMembers) {
        jpaRepository.saveAll(
                directMembers.stream()
                        .map(DomainUtil.DirectMemberMapper::toEntity)
                        .toList()
        );
    }

    @Override
    public List<DirectMember> getDirectMessageList(final String currentUserId) {
        return queryDslRepository.getDirectMessageList(currentUserId).stream()
                .map(
                        DomainUtil.DirectMemberMapper::toDomain
                )
                .toList();
    }

    @Override
    public List<String> getDirectMemberIds(final String directId) {
        return queryDslRepository.getMemberIdsByDirectId(directId);
    }
}
