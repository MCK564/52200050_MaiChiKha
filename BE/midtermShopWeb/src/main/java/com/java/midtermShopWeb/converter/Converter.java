package com.java.midtermShopWeb.converter;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.midtermShopWeb.builders.ProductSearchBuilder;
import com.java.midtermShopWeb.constants.MessageKeys;
import com.java.midtermShopWeb.dtos.ProductDTO;
import com.java.midtermShopWeb.dtos.UpdateUserDTO;
import com.java.midtermShopWeb.dtos.UserDTO;
import com.java.midtermShopWeb.exceptions.DataNotFoundException;
import com.java.midtermShopWeb.models.Product;
import com.java.midtermShopWeb.models.User;
import com.java.midtermShopWeb.repositories.CategoryRepository;
import com.java.midtermShopWeb.repositories.RoleRepository;
import com.java.midtermShopWeb.repositories.UserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import static com.java.midtermShopWeb.utils.MapUtils.getObject;

@Component
@RequiredArgsConstructor
public class Converter {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public Product convertToProduct(ProductDTO productDTO){
        return modelMapper.map(productDTO,Product.class);
    }

   public Product convertToProduct(Product product, ProductDTO productDTO){
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setDescription(productDTO.getDescription());
        product.setCategory(categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(()->
        new RuntimeException("Category not found")));
        return product;
   }

   public ProductSearchBuilder convertToProductSearchBuilder(Map<String, Object> conditions, int page, int limit){
        return ProductSearchBuilder.builder()
                .name(getObject(conditions,"name",String.class))
                .keyword(getObject(conditions,"keyword",String.class))
                .priceFrom(getObject(conditions,"priceFrom",Double.class))
                .priceTo(getObject(conditions,"priceTo",Double.class))
                .order(getObject(conditions,"order",String.class))
                .sortBy(getObject(conditions,"sortBy",String.class))
                .description(getObject(conditions,"description",String.class))
                .limit(limit)
                .page(page)
                .categoryId(getObject(conditions,"categoryId",Long.class))
                .build();
   }

   public User fromUserDTO(UserDTO userDTO) throws DataNotFoundException {
        User user = User.builder()
                .fullName(userDTO.getFullName())
                .password(userDTO.getPassword())
                .dateOfBirth(userDTO.getDateOfBirth())
                .role(roleRepository.findById(userDTO.getRoleId())
                        .orElseThrow(()->new DataNotFoundException(MessageKeys.DATA_NOT_FOUND)))
                .address(userDTO.getAddress())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .phoneNumber(userDTO.getPhoneNumber())
                .active(true)
                .build();
        return user;
   }

   public User fromExistingUserToUser(User user, UpdateUserDTO userDTO) throws DataNotFoundException {user.setFullName(userDTO.getFullName());
       user.setPassword(userDTO.getPassword());
       user.setDateOfBirth(userDTO.getDateOfBirth());
       user.setAddress(userDTO.getAddress());
       user.setFacebookAccountId(userDTO.getFacebookAccountId());
       user.setGoogleAccountId(userDTO.getGoogleAccountId());
       user.setPhoneNumber(userDTO.getPhoneNumber());
       return user;
   }



}
