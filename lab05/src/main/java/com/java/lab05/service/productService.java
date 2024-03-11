package com.java.lab05.service;

import com.java.lab05.DAO.ProductDAO;
import com.java.lab05.DAO.UserDAO;
import com.java.lab05.DTO.ProductDTO;
import com.java.lab05.constant.Message;
import com.java.lab05.exception.ObjectExisting;
import com.java.lab05.model.Product;


import java.util.List;

public class productService {
    public List<Product> getAllProducts()
    {
        try{
            List<Product> products = ProductDAO.getInstance().getAll();
//            return ProductResponse.builder()
//                    .products(products)
//                    .totalItem((long) products.size())
//                    .build();
            return products;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Product addProduct(ProductDTO productDTO) throws ObjectExisting {
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .build();
        if(productDTO.getId()==null){
            if(ProductDAO.getInstance().isExistByName(productDTO.getName()))
            {
               throw new ObjectExisting(Message.EXISTING_PRODUCT_NAME);
            }
            if(ProductDAO.getInstance().add(newProduct)){
                return ProductDAO.getInstance().getByUsername(newProduct.getName());
            }
            return null;
        }
        else{
            newProduct.setId(productDTO.getId());
            ProductDAO.getInstance().add(newProduct);
            if(ProductDAO.getInstance().add(newProduct)){
                return ProductDAO.getInstance().getByUsername(newProduct.getName());
            }
            return null;
        }
    }

    public String removeProduct(Long id){
        if(ProductDAO.getInstance().removeById(id)){
            return Message.DELETE_SUCCESSFULLY;
        }
        return Message.DELETE_FAILED;
    }
    public Product getOneById(Long id){
        try{
            return ProductDAO.getInstance().getOneById(id);
        }catch(Exception e){
            return null;
        }
    }
}
