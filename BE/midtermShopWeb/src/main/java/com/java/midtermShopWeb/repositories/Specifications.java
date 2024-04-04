package com.java.midtermShopWeb.repositories;

import com.java.midtermShopWeb.builders.ProductSearchBuilder;
import com.java.midtermShopWeb.models.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class Specifications {
    public static Specification<Product> searchProductsByConditions(ProductSearchBuilder psb) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (psb.getCategoryId() != null && psb.getCategoryId() != 0) {
                    predicate = criteriaBuilder
                            .and(predicate, criteriaBuilder
                                    .equal(root.get("category").get("id"), psb.getCategoryId()));
                }
                if(psb.getKeyword() != null && ! psb.getKeyword().isEmpty()){
                    String likeKeyWord = "%" + psb.getKeyword()+"%";
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(
                            criteriaBuilder.like(root.get("name"),likeKeyWord),
                            criteriaBuilder.like(root.get("description"),likeKeyWord)
                    ));
                }
                if(psb.getPriceFrom()!=null && psb.getPriceFrom()!=0){
                    predicate = criteriaBuilder.and(predicate,criteriaBuilder.greaterThanOrEqualTo(root.get("price"),psb.getPriceFrom()));
                }
                if(psb.getPriceTo()!=null && psb.getPriceTo()!=0){
                    predicate = criteriaBuilder.and(predicate,criteriaBuilder.lessThanOrEqualTo(root.get("price"),psb.getPriceTo()));
                }

                return predicate;
            }
        };
    }}
