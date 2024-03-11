package com.java.lab05.DAO;

import com.java.lab05.model.Product;
import com.java.lab05.utill.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;


public class ProductDAO implements iDAO<Product>{
    public static  ProductDAO getInstance(){
        return new ProductDAO();
    }

    @Override
    public List<Product> getAll() {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Query<Product> query =session.createQuery("from Product", Product.class);
            return query.stream().toList();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product getOneById(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            return session.get(Product.class,id);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean isExistById(Long id) {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            if(session.get(Product.class,id)!=null){
                return true;
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeById(Long id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            if (product != null) {
                session.delete(product);
                session.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean add(Product object) {
        Transaction transaction = null;
        try(Session session =  HibernateUtils.getSessionFactory().openSession() ){
            transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
            return true;
        }catch(Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean isExistByName(String name){
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
           Query<Product> query = session.createQuery("from Product where name = :name", Product.class);
           query.setParameter("name",name);
           Product product = query.uniqueResult();
           if(product!=null)return true;
           return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean merge(Product object) {
        return false;
    }

    public Product getByUsername(String name){
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            Query<Product> query = session.createQuery("from Product where name = :name", Product.class);
            query.setParameter("name",name);
            Product product = query.uniqueResult();
            return query.uniqueResult();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
