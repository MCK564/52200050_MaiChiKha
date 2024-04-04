package com.java.midtermShopWeb.mappers;

import com.java.midtermShopWeb.models.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    List<Order> findOrderByParams(@Param("conditions") Map<String, Object> conditions);
}
