package com.java.midtermShopWeb.repositories;



import com.java.midtermShopWeb.models.Token;
import com.java.midtermShopWeb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);
    Token findByToken(String token);
    Token findByRefreshToken(String token);
    boolean existsByToken(String token);
}

