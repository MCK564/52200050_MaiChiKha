package com.lab03.hibernate;




import com.lab03.hibernate.DAO.ManufactureDAO;
import com.lab03.hibernate.DAO.PhoneDAO;
import com.lab03.hibernate.model.Manufacture;
import com.lab03.hibernate.model.Phone;

import java.io.InvalidObjectException;
import java.util.List;



public final class Program {


    public static  void main(String[] args) throws InvalidObjectException {
//        List<Phone> phones = PhoneDAO.getInstance().getAll();
//        if(!phones.isEmpty())phones.forEach(System.out::println);
//        System.out.println(PhoneDAO.getInstance().get("1"));

//        List<Manufacture> manufactures = ManufactureDAO.getInstance().getAll();
//        if(!manufactures.isEmpty())manufactures.forEach(System.out::println);

//            List<Phone> highestPricePhones = PhoneDAO.getInstance().getPhonesWithHighestSellingPrice();
//            highestPricePhones.forEach(System.out::println);

//        List<Phone> phones = PhoneDAO.getInstance().getAllAndSortByCountry();
//        if(!phones.isEmpty())phones.forEach(System.out::println);

      //  System.out.println(PhoneDAO.getInstance().hasPhoneMoreThan50MVND());
        //System.out.println(PhoneDAO.getInstance().theFirstHasColorPinkAndPriceIsOver750());

      //  -------------------------------ManufactureDAO-------------------------------

        //System.out.println(ManufactureDAO.getInstance().AllManuHaveMoreThan100Employees());
       // System.out.println(ManufactureDAO.getInstance().getNumberOfAllEmployees());
        System.out.println(ManufactureDAO.getInstance().getTheLastFromTheListOfUSAManufacture());
    }
}
