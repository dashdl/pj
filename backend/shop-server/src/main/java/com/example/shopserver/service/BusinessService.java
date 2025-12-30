package com.example.shopserver.service;

import com.example.shopserver.pojo.entity.Business;

import java.util.List;

public interface BusinessService {
    List<Business> getAllBusinesses();

    List<Business> getBusinessesByType(String type);

    List<Business> getBusinessesById(Integer id);

    void addBusiness(Business business);

    // void updateBusiness(Business business, Integer id);
    void updateBusiness(Business business);

    void deleteBusiness(Integer id);

    void offshelfBusiness(Integer id);

    void passBusiness(Integer id);

    List<Business> getMypush(String user);

    List<Business> getMyselled(String user);

    List<Business> searchBusiness(String keyword);
}
