package com.java.midtermShopWeb.constants;

public class SQLString {
    public  static final String PRODUCT_JOIN_CATEGORIES = " INNER JOIN categories cg ON cg.id = p.category_id ";
    public static final String PRODUCT_JOIN_USERS = " INNER JOIN carts_products cp ON  ";
    public static final String WHERE = " WHERE 1=1 ";
}
