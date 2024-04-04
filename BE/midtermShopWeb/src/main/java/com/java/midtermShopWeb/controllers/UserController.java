package com.java.midtermShopWeb.controllers;

import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.BOEDTO;
import com.java.midtermShopWeb.dtos.RefreshTokenDTO;
import com.java.midtermShopWeb.dtos.UserDTO;
import com.java.midtermShopWeb.dtos.UserLoginDTO;
import com.java.midtermShopWeb.models.Token;
import com.java.midtermShopWeb.models.User;
import com.java.midtermShopWeb.responses.user.LoginResponse;
import com.java.midtermShopWeb.responses.user.UserListResponse;
import com.java.midtermShopWeb.responses.user.UserResponse;
import com.java.midtermShopWeb.services.token.TokenService;
import com.java.midtermShopWeb.services.user.UserService;
import com.java.midtermShopWeb.utils.JwtUtils;
import com.java.midtermShopWeb.utils.LocalizationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.java.midtermShopWeb.utils.LocalizationUtils;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final LocalizationUtils localizationUtils;
    private final JwtUtils jwtUtils;
    private final TokenService tokenService;

    @GetMapping("/search")
    public ResponseEntity<?> getAllUserByConditions(
            @RequestParam(defaultValue = "", required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit){
        try{
            PageRequest pageRequest = PageRequest.of(page,limit,
                    Sort.by("id").ascending());
            Page<UserResponse> userPage = userService.findAll(keyword,pageRequest).map(UserResponse::fromUser);

            int totalPages = userPage.getTotalPages();
            List<UserResponse> userResponses = userPage.getContent();
            return ResponseEntity.ok().body(UserListResponse.builder()
                    .users(userResponses)
                    .totalPages(totalPages)
                    .build());
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody() UserLoginDTO userLoginDTO,
            HttpServletRequest request,
            BindingResult result
            ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            return ResponseEntity.ok().body(userService.login(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword(),request));
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(MessageKeys.LOGIN_FAILED+" "+e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
            )throws Exception {
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
          if(userService.createUser(userDTO)!=null){
              return ResponseEntity.ok().body(MessageKeys.REGISTER_SUCCESSFULLY);
          }
          return ResponseEntity.badRequest().body(MessageKeys.REGISTER_FAILED);
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOnlyOneUser (@PathVariable("id")Long id){
        try{
            return ResponseEntity.ok().body(userService.getUserById(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/check")
    public ResponseEntity<?> isAdmin(HttpServletRequest request){
        try{
            final String authHeader = request.getHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                return ResponseEntity.status(401).body(MessageKeys.UNAUTHORIZED);
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtUtils.extractPhoneNumber(token);
            return ResponseEntity.ok(userService.isAdmin(phoneNumber));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(false);
        }
    }
    @PostMapping("/refreshToken")
    public ResponseEntity<LoginResponse> refreshToken(
            @Valid @RequestBody RefreshTokenDTO refreshTokenDTO
    ) {
        try {
            User userDetail = userService.getUserDetailsFromRefreshToken(refreshTokenDTO.getRefreshToken());
            Token jwtToken = tokenService.refreshToken(refreshTokenDTO.getRefreshToken(), userDetail);
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Refresh token successfully")
                    .token(jwtToken.getToken())
                    .tokenType(jwtToken.getTokenType())
                    .refreshToken(jwtToken.getRefreshToken())
                    .username(userDetail.getUsername())
                    .roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
                    .id(userDetail.getId())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMesage(MessageKeys.LOGIN_FAILED, e.getMessage()))
                            .build()
            );
        }
    }
    @PostMapping("/block-or-enable")
    public ResponseEntity<?> blockOrEnable(
            @RequestBody BOEDTO boedto){
        try{
            userService.blockOrEnable(boedto.getId(),boedto.getActive());
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }




}
