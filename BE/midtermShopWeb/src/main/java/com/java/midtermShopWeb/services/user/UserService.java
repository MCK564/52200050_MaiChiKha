package com.java.midtermShopWeb.services.user;

import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.converter.Converter;
import com.java.midtermShopWeb.dtos.UpdateUserDTO;
import com.java.midtermShopWeb.dtos.UserDTO;
import com.java.midtermShopWeb.exceptions.DataNotFoundException;
import com.java.midtermShopWeb.exceptions.ExpiredTokenException;
import com.java.midtermShopWeb.exceptions.PermissionDenyException;
import com.java.midtermShopWeb.models.CartProduct;
import com.java.midtermShopWeb.models.Role;
import com.java.midtermShopWeb.models.Token;
import com.java.midtermShopWeb.models.User;
import com.java.midtermShopWeb.repositories.CartProductRepository;
import com.java.midtermShopWeb.repositories.RoleRepository;
import com.java.midtermShopWeb.repositories.TokenRepository;
import com.java.midtermShopWeb.repositories.UserRepository;
import com.java.midtermShopWeb.responses.user.LoginResponse;
import com.java.midtermShopWeb.responses.user.UserResponse;
import com.java.midtermShopWeb.responses.user.isAdminResponse;
import com.java.midtermShopWeb.services.token.TokenService;
import com.java.midtermShopWeb.utils.JwtUtils;
import com.java.midtermShopWeb.utils.LocalizationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final JwtUtils jwtUtils;
    private final Converter converter;
    private final LocalizationUtils localizationUtils;
    private final AuthenticationManager authenticationManager;
    private final CartProductRepository cartProductRepository;


    @Override
    public User createUser(UserDTO userDTO) throws Exception {
       if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())){
           throw new Exception(MessageKeys.PHONE_NUMBER_EXISTED);
       }
        Role role =roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(
                        localizationUtils.getLocalizedMesage(MessageKeys.ROLE_DOES_NOT_EXISTS)));
        if(role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("Không được phép đăng ký tài khoản Admin");
        }
       if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
           throw new Exception(MessageKeys.PASSWORD_NOT_MATCH);
       }
       User user = converter.fromUserDTO(userDTO);
        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
       return userRepository.saveAndFlush(user);
    }

    @Override
    public LoginResponse login(String phoneNumber, String password , HttpServletRequest request) throws Exception {
        LoginResponse loginResponse = new LoginResponse();
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()-> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
        if(!passwordEncoder.matches(password, user.getPassword())){
            loginResponse.setMessage(MessageKeys.WRONG_PHONE_PASSWORD);
        }
        else{
            if (user.getFacebookAccountId() == 0
                    && user.getGoogleAccountId() == 0) {
                if(!passwordEncoder.matches(password, user.getPassword())) {
                    throw new BadCredentialsException(localizationUtils.getLocalizedMesage(MessageKeys.WRONG_PHONE_PASSWORD));
                }
            }
            if(!user.isActive()) {
                throw new DataNotFoundException(localizationUtils.getLocalizedMesage(MessageKeys.USER_IS_LOCKED));
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    phoneNumber,password,user.getAuthorities()
            );
            authenticationManager.authenticate(authenticationToken);
            loginResponse.setId(user.getId());
            loginResponse.setToken(jwtUtils.generateToken(user));
            loginResponse.setFullname(user.getFullName());
            loginResponse.setMessage(MessageKeys.LOGIN_SUCCESSFULLY);
            loginResponse.setRefreshToken(loginResponse.getToken());
            loginResponse.setRoles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
            List<CartProduct> cartProducts = cartProductRepository.findAllByUserId(user.getId());
            loginResponse.setQuantity(cartProducts.size());

            //thêm mới token vào database
            String userAgent = request.getHeader("User-Agent");
            tokenService.addToken(user,loginResponse.getToken(),isMobileDevice(userAgent));
        }
        return loginResponse;
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if(jwtUtils.isTokenExpired(token)){
            throw new ExpiredTokenException(MessageKeys.TOKEN_IS_EXPIRED);
        }
        String phoneNumber = jwtUtils.extractPhoneNumber(token);
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(()->new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
    }

    @Override
    public User getUserDetailsFromRefreshToken(String token) throws Exception {
        Token existingToken = tokenRepository.findByRefreshToken(token);
        return getUserDetailsFromToken(existingToken.getToken());
    }

    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
        User updatedUser = converter.fromExistingUserToUser(existingUser,updatedUserDTO);
        return userRepository.saveAndFlush(updatedUser);
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
       return userRepository.findAll(keyword,pageable);
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(()-> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));

        if(newPassword.equals(existingUser.getPassword())){
            throw new Exception(MessageKeys.NEW_PASSWORD_EQUAL_OLD_PASSWORD);
        }
        existingUser.setPassword(newPassword);
        userRepository.saveAndFlush(existingUser);
    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException {
        User existingUser = userRepository.findById(userId).orElseThrow(
                ()-> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
        existingUser.setActive(active);
        userRepository.saveAndFlush(existingUser);
    }

    @Override
    public UserResponse getUserById(Long id) throws DataNotFoundException {
       User existingUser = userRepository.findById(id).orElseThrow(
               ()-> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND));
       return UserResponse.fromUser(existingUser);
    }

    @Override
    public isAdminResponse isAdmin(String phoneNumber) throws DataNotFoundException {
        User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                ()-> new DataNotFoundException(MessageKeys.DATA_NOT_FOUND)

        );
        return isAdminResponse.builder()
                .isAdmin(existingUser.getRole().getName().equals("admin"))
                .quantity(cartProductRepository.findAllByUserId(existingUser.getId()).size())
                .build();
    }

    private boolean isMobileDevice(String userAgent) {
        return userAgent.toLowerCase().contains("mobile");
    }
}
