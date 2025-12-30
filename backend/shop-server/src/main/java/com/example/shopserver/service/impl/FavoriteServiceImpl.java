package com.example.shopserver.service.impl;

import com.example.shopserver.mapper.FavoriteMapper;
import com.example.shopserver.pojo.entity.Favorite;
import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏服务实现类
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public void addFavorite(Favorite favorite) {
        favoriteMapper.insertFavorite(favorite);
    }

    @Override
    public void removeFavorite(String username, Integer businessId) {
        favoriteMapper.deleteFavorite(username, businessId);
    }

    @Override
    public List<Business> getFavoritesByUsername(String username) {
        return favoriteMapper.selectFavoritesByUsername(username);
    }

    @Override
    public List<Business> getFavoritesByUsernameAndFolder(String username, String folderName) {
        return favoriteMapper.selectFavoritesByUsernameAndFolder(username, folderName);
    }

    @Override
    public Boolean checkFavorite(String username, Integer businessId) {
        Integer count = favoriteMapper.checkFavorite(username, businessId);
        return count != null && count > 0;
    }

    @Override
    public List<String> getFoldersByUsername(String username) {
        return favoriteMapper.selectFoldersByUsername(username);
    }

    @Override
    public void updateFolderName(String username, String oldFolderName, String newFolderName) {
        favoriteMapper.updateFolderName(username, oldFolderName, newFolderName);
    }

    @Override
    public Integer getFavoriteCountByBusinessId(Integer businessId) {
        return favoriteMapper.selectFavoriteCountByBusinessId(businessId);
    }
}
