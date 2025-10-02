package com.example.bankcards.security.repository;

import com.example.bankcards.security.model.TokenStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenStoreEntity, Long> {
    Optional <TokenStoreEntity> findByLogin(String login);

}
