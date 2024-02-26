package com.lab03.hibernate.DAO;

import com.lab03.hibernate.model.Manufacture;
import com.lab03.hibernate.model.Phone;
import com.lab03.hibernate.utill.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.InvalidObjectException;
import java.util.List;

public class ManufactureDAO implements  iDAO<Manufacture> {
    public static ManufactureDAO getInstance(){
        return new ManufactureDAO();
    }
    @Override
    public boolean addOrUpdate(Manufacture object) {
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
    public Manufacture get(String id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.get(Manufacture.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Manufacture> getAll() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<Manufacture> query = session.createQuery("from Manufacture ",Manufacture.class);
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
            Manufacture manufactureToRemove = session.get(Manufacture.class,id);
            if(manufactureToRemove!=null){
                session.delete(manufactureToRemove);
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
    public boolean remove(Manufacture object) {
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

    public boolean AllManuHaveMoreThan100Employees(){
        List<Manufacture> allManufactures = getAll();
        for(Manufacture x : allManufactures){
            if(x.getEmployee()<100)return false;
        }
        return true;
    }

    public int getNumberOfAllEmployees(){
        List<Manufacture> allManufactures = getAll();
      int totalEmployees = 0;
      for(Manufacture x: allManufactures){
          totalEmployees+=x.getEmployee();
      }
      return totalEmployees;

    }

    public Manufacture getTheLastFromTheListOfUSAManufacture() throws InvalidObjectException {
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            String hql = "from Manufacture where location = 'USA'";
            Query<Manufacture> query = session.createQuery(hql);
            return query.stream().toList().get(query.stream().toList().size()-1);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
