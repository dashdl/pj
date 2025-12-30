package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.Favorite;
import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/favorite")
@CrossOrigin(origins = "*")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;

    /**
     * 添加收藏
     */
    @PostMapping("/add")
    public Result addFavorite(@RequestBody Favorite favorite) {
        try {
            // 检查是否已收藏
            if (favoriteService.checkFavorite(favorite.getUsername(), favorite.getBusinessId())) {
                return Result.error("商品已在收藏夹中");
            }
            favoriteService.addFavorite(favorite);
            return Result.success("收藏成功");
        } catch (Exception e) {
            return Result.error("收藏失败: " + e.getMessage());
        }
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/remove")
    public Result removeFavorite(@RequestParam String username, @RequestParam Integer businessId) {
        try {
            favoriteService.removeFavorite(username, businessId);
            return Result.success("取消收藏成功");
        } catch (Exception e) {
            return Result.error("取消收藏失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户收藏列表
     */
    @GetMapping("/list/{username}")
    public Result getFavoritesByUsername(@PathVariable String username) {
        try {
            List<Business> favorites = favoriteService.getFavoritesByUsername(username);
            return Result.success(favorites);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户指定收藏夹的商品
     */
    @GetMapping("/folder/{username}/{folderName}")
    public Result getFavoritesByUsernameAndFolder(@PathVariable String username, @PathVariable String folderName) {
        try {
            List<Business> favorites = favoriteService.getFavoritesByUsernameAndFolder(username, folderName);
            return Result.success(favorites);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/check")
    public Result checkFavorite(@RequestParam String username, @RequestParam Integer businessId) {
        try {
            Boolean isFavorite = favoriteService.checkFavorite(username, businessId);
            return Result.success(isFavorite);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户收藏夹列表
     */
    @GetMapping("/folders/{username}")
    public Result getFoldersByUsername(@PathVariable String username) {
        try {
            List<String> folders = favoriteService.getFoldersByUsername(username);
            return Result.success(folders);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 更新收藏夹名称
     */
    @PutMapping("/folder/rename")
    public Result updateFolderName(@RequestParam String username, 
                                   @RequestParam String oldFolderName, 
                                   @RequestParam String newFolderName) {
        try {
            favoriteService.updateFolderName(username, oldFolderName, newFolderName);
            return Result.success("收藏夹重命名成功");
        } catch (Exception e) {
            return Result.error("重命名失败: " + e.getMessage());
        }
    }

    /**
     * 查询商品收藏数量
     */
    @GetMapping("/count/{businessId}")
    public Result getFavoriteCountByBusinessId(@PathVariable Integer businessId) {
        try {
            Integer count = favoriteService.getFavoriteCountByBusinessId(businessId);
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
}
