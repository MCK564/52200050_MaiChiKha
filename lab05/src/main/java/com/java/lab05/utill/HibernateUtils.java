package com.java.lab05.utill;



import com.java.lab05.model.Product;
import com.java.lab05.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateUtils {
    private static SessionFactory factory = null;

    static {
        try {
            Configuration cfg = new Configuration();
            // Cấu hình kết nối cơ sở dữ liệu từ application.properties
            Properties props = new Properties();
            props.put(Environment.URL, "jdbc:mysql://localhost:3307/lab05");
            props.put(Environment.USER, "root");
            props.put(Environment.PASS, "Mck050604@");
            props.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            props.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
            props.put(Environment.SHOW_SQL, "true");
            props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            props.put(Environment.HBM2DDL_AUTO, "update");

            cfg.setProperties(props);

            // Đăng ký các entity class (model package) để Hibernate quản lý
           cfg.addAnnotatedClass(Product.class);
           cfg.addAnnotatedClass(User.class);
            // Thêm các entity class khác nếu cần


            // Tạo SessionFactory từ Configuration và ServiceRegistry
            factory = cfg.buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    public static void close() {
        if (factory != null) {
            factory.close();
        }
    }
}
