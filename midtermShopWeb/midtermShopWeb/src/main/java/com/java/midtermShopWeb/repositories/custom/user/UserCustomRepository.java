package com.java.midtermShopWeb.repositories.custom.user;

import com.java.midtermShopWeb.models.User;

import java.util.List;
import java.util.Map;


public interface UserCustomRepository {
    List<User> findByCustomCondition(Map<String,Object> conditions);



}
