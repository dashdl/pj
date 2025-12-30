package com.example.shopserver.service;

import com.example.shopserver.pojo.entity.Favorite;
import com.example.shopserver.pojo.entity.Business;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface FavoriteService {
    
    /**
     * 添加收藏
     */
    void addFavorite(Favorite favorite);
    
    /**
     * 取消收藏
     */
    void removeFavorite(String username, Integer businessId);
    
    /**
     * 查询用户收藏列表
     */
    List<Business> getFavoritesByUsername(String username);
    
    /**
     * 查询用户指定收藏夹的商品
     */
    List<Business> getFavoritesByUsernameAndFolder(String username, String folderName);
    
    /**
     * 检查是否已收藏
     */
    Boolean checkFavorite(String username, Integer businessId);
    
    /**
     * 查询用户收藏夹列表
     */
    List<String> getFoldersByUsername(String username);
    
    /**
     * 更新收藏夹名称
     */
    void updateFolderName(String username, String oldFolderName, String newFolderName);
    
    /**
     * 查询商品收藏数量
     */
    Integer getFavoriteCountByBusinessId(Integer businessId);
}
