package com.example.shopserver.service.impl;

import com.example.shopserver.mapper.BusinessMapper;
import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private BusinessMapper businessMapper;

    /**
     * 查询全部商品列表
     * @return
     */
    @Override
    public List<Business> getAllBusinesses() {
        return businessMapper.selectAllBusinesses();
    }

    /**
     * 根据商品类型查询商品列表
     * @param type
     * @return
     */
    @Override
    public List<Business> getBusinessesByType(String type) {
        return businessMapper.selectBusinessesByType(type);
    }

    /**
     * 根据商品id查询商品列表
     * @param id
     * @return
     */
    @Override
    public List<Business> getBusinessesById(Integer id) {
        List<Business> aaa = businessMapper.selectBusinessesById(id);
        System.out.println("#######################"+aaa);
        return businessMapper.selectBusinessesById(id);
    }

    /**
     * 添加商品
     * @param business
     */
    @Override
    public void addBusiness(Business business) {
        businessMapper.insertBusiness(business);
    }

    /**
     * 更新商品
     * @param business
     * @param paramsId
     */
    @Override
    // public void updateBusiness(Business business, Integer paramsId) {
    //     businessMapper.updateBusiness(business, paramsId);
    // }
    public void updateBusiness(Business business) {
        businessMapper.updateBusiness(business);
    }

    /**
     * 删除商品
     * @param id
     */
    @Override
    public void deleteBusiness(Integer id) {
        businessMapper.deleteBusiness(id);
    }

    /**
     * 下架商品
     * @param id
     */
    @Override
    public void offshelfBusiness(Integer id) {
        businessMapper.updateBusinessStatusToOffshelf(id);
    }

    /**
     * 上架商品
     * @param id
     */
    @Override
    public void passBusiness(Integer id) {
        businessMapper.updateBusinessStatusToPass(id);
    }

    /**
     * 查询我的发布
     * @param user
     * @return
     */
    @Override
    public List<Business> getMypush(String user) {
        return businessMapper.selectMypush(user);
    }

    /**
     * 查询我的卖出
     * @param user
     * @return
     */
    @Override
    public List<Business> getMyselled(String user) {
        return businessMapper.selectMyselled(user);
    }

    /**
     * 处理搜索请求
     * @param keyword
     * @return
     */
    @Override
    public List<Business> searchBusiness(String keyword) {
        return businessMapper.selectBusinessesByKeyword(keyword);
    }
}
