package com.java.lab05.service;

import com.java.lab05.DAO.ProductDAO;
import com.java.lab05.DAO.UserDAO;
import com.java.lab05.DTO.ProductDTO;
import com.java.lab05.DTO.RegisterDTO;
import com.java.lab05.constant.Message;
import com.java.lab05.model.Product;
import com.java.lab05.model.User;

public class userService {
    public boolean IsLoginSuccess (String username,String password){
        try{
            User existingUser = UserDAO.getInstance().getUserByUsername(username);
            if(existingUser != null){
                if(password.equals(existingUser.getPassword())){
                    return true;
                }
            } return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String Register(RegisterDTO registerDTO){
        try{
            if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())){
                return Message.UNACCEPTED_CONFIRM_PASSWORD;
            }
            if(UserDAO.getInstance().getUserByUsername(registerDTO.getEmail())!=null){
                return Message.EXISTING_EMAIL;
            }
            UserDAO.getInstance().add(User.builder().email(registerDTO.getEmail())
                            .fullName(registerDTO.getFullName())
                            .password(registerDTO.getPassword())
                    .build());
            return Message.CREATE_SUCCESSFULLY;
        }catch(Exception e){
            return e.getMessage();
        }
    }

    public String getFullNameByUsername(String username){
        try{
            User existingUser = UserDAO.getInstance().getUserByUsername(username);
            return existingUser.getFullName();
        }catch(Exception e){
            return null;
        }

    }

}
