package com.java.midtermShopWeb.services.token;



import com.java.midtermShopWeb.models.Token;
import com.java.midtermShopWeb.models.User;
import org.springframework.stereotype.Service;

@Service
public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
    void resetSystem() throws Exception;
}
