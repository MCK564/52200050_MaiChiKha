package com.lab03.hibernate.DAO;


import com.lab03.hibernate.model.Manufacture;
import com.lab03.hibernate.model.Phone;
import com.lab03.hibernate.utill.HibernateUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PhoneDAO implements iDAO<Phone>{

    public static PhoneDAO getInstance(){
        return new PhoneDAO();
    }



    @Override
    public boolean addOrUpdate(Phone object) {
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

    @Override
    public Phone get(String id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Phone.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Phone> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Phone> query = session.createQuery("from Phone",Phone.class);
            return query.stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean remove(String id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
          Transaction transaction =session.beginTransaction();
          Phone phoneToRemove = session.get(Phone.class,id);
          if(phoneToRemove!=null){
              session.delete(phoneToRemove);
              transaction.commit();
              return true;
          }
          return false;
        } catch (Exception e) {
            e.printStackTrace();
           return false;
        }
    }

    @Override
    public boolean remove(Phone object) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction transaction =session.beginTransaction();
                session.delete(object);
                transaction.commit();
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Phone> getPhonesWithHighestSellingPrice(){
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            String hql = "from Phone where price = (Select max(price) from Phone)";
            Query<Phone> query = session.createQuery(hql);
            return query.stream().toList();

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Phone> getAllAndSortByCountry() {
        List<Phone> allPhones = getAll();
        if (allPhones != null) {
            List<Phone> mutablePhones = new ArrayList<>(allPhones);
            mutablePhones.sort(Comparator
                    .comparing(Phone::getCountry).reversed()
                    .thenComparing(Phone::getPrice));
            return mutablePhones;
        } else {
            return new ArrayList<>();
        }
    }

    public boolean hasPhoneMoreThan50MVND(){
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            String hql = "from Phone where price > 1000";
            Query<Phone> query = session.createQuery(hql);
            return query.stream().toList().size()>0;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Phone theFirstHasColorPinkAndPriceIsOver750(){
       try(Session session = HibernateUtils.getSessionFactory().openSession()){
           String hql = "from Phone where price>750 and color='Đen'";
           Query<Phone> query = session.createQuery(hql);
           List<Phone> result = query.stream().toList();
           if(result.size()>0)
               return result.get(0);
           return null;
       }catch(Exception e){
           e.printStackTrace();
          return null;
       }
    }
}
