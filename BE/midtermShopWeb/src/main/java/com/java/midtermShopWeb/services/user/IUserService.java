package com.java.midtermShopWeb.services.user;

import com.java.midtermShopWeb.dtos.UpdateUserDTO;
import com.java.midtermShopWeb.dtos.UserDTO;
import com.java.midtermShopWeb.exceptions.DataNotFoundException;
import com.java.midtermShopWeb.exceptions.InvalidPasswordException;
import com.java.midtermShopWeb.models.User;
import com.java.midtermShopWeb.responses.user.LoginResponse;
import com.java.midtermShopWeb.responses.user.UserResponse;
import com.java.midtermShopWeb.responses.user.isAdminResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    LoginResponse login(String phoneNumber, String password, HttpServletRequest request) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    User getUserDetailsFromRefreshToken(String token) throws Exception;
    User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception;

    Page<User> findAll(String keyword, Pageable pageable) throws Exception;
    void resetPassword(Long userId, String newPassword)
            throws Exception;
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException;
    UserResponse getUserById (Long id) throws DataNotFoundException;
    isAdminResponse isAdmin(String phoneNumber) throws DataNotFoundException;
}
