package com.example.shopserver.mapper;

import com.example.shopserver.pojo.entity.Business;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import java.util.List;

@Mapper
public interface BusinessMapper {
    /**
     * 查询全部商品列表
     * @return
     */
    @Select("SELECT * FROM business")
    List<Business> selectAllBusinesses();

    /**
     * 根据分类查询商品列表
     * @param type
     * @return
     */
    @Select("SELECT * FROM business WHERE type = #{type} AND status = 1")
    List<Business> selectBusinessesByType(String type);

    /**
     * 根据id查询商品列表
     * @param id
     * @return
     */
    @Select("SELECT * FROM business WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "username", column = "username"),
        @Result(property = "name", column = "name"),
        @Result(property = "phone", column = "phone"),
        @Result(property = "title", column = "title"),
        @Result(property = "description", column = "description"),
        @Result(property = "content", column = "content"),
        @Result(property = "position", column = "position"),
        @Result(property = "type", column = "type"),
        @Result(property = "status", column = "status"),
        @Result(property = "photo", column = "photo"),
        @Result(property = "price", column = "price"),
        @Result(property = "created_at", column = "created_at")
    })
    List<Business> selectBusinessesById(Integer id);

    /**
     * 新增商品
     * @param business
     */
    @Insert("INSERT INTO business (username, name, phone, title, description, content, position, status, type, photo, price) VALUES (#{username}, #{name}, #{phone}, #{title}, #{description}, #{content}, #{position}, '0', #{type}, #{photo}, #{price})")
    void insertBusiness(Business business);

    /**
     * 更新商品
     * @param business
     * @param paramsId
     */
    @Update("UPDATE business SET username = #{username}, name = #{name}, phone = #{phone}, title = #{title}, description = #{description}, content = #{content}, position = #{position}, type = #{type}, photo = #{photo}, price = #{price} WHERE id = #{id}")
    void updateBusiness(Business business);
    // void updateBusiness(Business business, Integer paramsId);

    /**
     * 删除商品
     * @param id
     */
    @Delete("DELETE FROM business WHERE id = #{id}")
    void deleteBusiness(Integer id);

    /**
     * 下架商品
     * @param id
     */
    @Update("UPDATE business SET status = 0 WHERE id = #{id}")
    void updateBusinessStatusToOffshelf(Integer id);

    /**
     * 上架商品
     * @param id
     */
    @Update("UPDATE business SET status = 1 WHERE id = #{id}")
    void updateBusinessStatusToPass(Integer id);

    /**
     * 查询用户发布的商品
     * @param user
     * @return
     */
    @Select("SELECT * FROM business WHERE username = #{user} AND status != 2")
    List<Business> selectMypush(String user);

    /**
     * 查询用户已卖出的商品
     * @param user
     * @return
     */
    @Select("SELECT * FROM business WHERE username = #{user} AND status = 2")
    List<Business> selectMyselled(String user);

    /**
     * 根据关键字搜索商品
     * @param keyword
     * @return
     */
    @Select("SELECT * FROM business WHERE status = 1 AND description LIKE CONCAT('%', #{keyword}, '%')")
    List<Business> selectBusinessesByKeyword(String keyword);
}
