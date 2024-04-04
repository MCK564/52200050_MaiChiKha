package com.java.midtermShopWeb.utils;

import java.util.Map;

public class MapUtils {
    public static <T> T getObject(Map<String, Object> conditions, String key, Class<T> tclass){
        Object ob = conditions.getOrDefault(key, null);
        if(ob!=null){
           if( tclass.getTypeName().equals("java.lang.Long")){
               ob = (!"".equals(ob) ? Long.valueOf(ob.toString()):null);
           }
           else if(tclass.getTypeName().equals("java.lang.String")) {
               ob = (!"".equals(ob) ? ob.toString():null);
           }
           else if(tclass.getTypeName().equals("java.lang.Double")) {
               ob = (!"".equals(ob) ? Double.valueOf(ob.toString()) : null);
           }
           else if(tclass.getTypeName().equals("java.lang.Integer")){
               ob = (!"".equals(ob) ? Integer.valueOf(ob.toString()) :null);
           }
            return tclass.cast(ob);
        }
        return null;
    }
}
