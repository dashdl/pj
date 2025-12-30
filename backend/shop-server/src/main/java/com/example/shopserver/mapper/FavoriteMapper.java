package com.example.shopserver.mapper;

import com.example.shopserver.pojo.entity.Favorite;
import com.example.shopserver.pojo.entity.Business;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    
    /**
     * 添加收藏
     */
    @Insert("INSERT INTO favorites(username, business_id, folder_name) VALUES (#{username}, #{businessId}, #{folderName})")
    void insertFavorite(Favorite favorite);

    /**
     * 取消收藏
     */
    @Delete("DELETE FROM favorites WHERE username = #{username} AND business_id = #{businessId}")
    void deleteFavorite(@Param("username") String username, @Param("businessId") Integer businessId);

    /**
     * 查询用户收藏列表
     */
    @Select("SELECT b.*, f.created_at as favorite_time, f.folder_name " +
            "FROM favorites f JOIN business b ON f.business_id = b.id " +
            "WHERE f.username = #{username} ORDER BY f.created_at DESC")
    List<Business> selectFavoritesByUsername(String username);

    /**
     * 查询用户指定收藏夹的商品
     */
    @Select("SELECT b.*, f.created_at as favorite_time, f.folder_name " +
            "FROM favorites f JOIN business b ON f.business_id = b.id " +
            "WHERE f.username = #{username} AND f.folder_name = #{folderName} ORDER BY f.created_at DESC")
    List<Business> selectFavoritesByUsernameAndFolder(@Param("username") String username, @Param("folderName") String folderName);

    /**
     * 检查是否已收藏
     */
    @Select("SELECT COUNT(*) FROM favorites WHERE username = #{username} AND business_id = #{businessId}")
    Integer checkFavorite(@Param("username") String username, @Param("businessId") Integer businessId);

    /**
     * 查询用户收藏夹列表
     */
    @Select("SELECT DISTINCT folder_name FROM favorites WHERE username = #{username}")
    List<String> selectFoldersByUsername(String username);

    /**
     * 更新收藏夹名称
     */
    @Update("UPDATE favorites SET folder_name = #{newFolderName} WHERE username = #{username} AND folder_name = #{oldFolderName}")
    void updateFolderName(@Param("username") String username, @Param("oldFolderName") String oldFolderName, @Param("newFolderName") String newFolderName);

    /**
     * 查询商品收藏数量
     */
    @Select("SELECT COUNT(*) FROM favorites WHERE business_id = #{businessId}")
    Integer selectFavoriteCountByBusinessId(Integer businessId);
}
