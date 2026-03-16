package com.example.Coffee.Shop.Management.repository;


import com.example.Coffee.Shop.Management.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token, Long> {


    public Optional<Token> findByToken(String token);

    @Query("SELECT t FROM Token t WHERE t.user.id = :id AND (t.isExpired = false OR t.isRevoked = false)")
    List<Token> findAllValidTokenByUser(@Param("id") Long id);


}