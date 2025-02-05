package org.example.eduapp.repository;

import org.example.eduapp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = "select * from token as t where t.user_id=?1", nativeQuery = true)
    Optional<Token> findByUserId(Long id);

    Optional<Token> findByToken(String token);
}
