package com.example.shopserver.mapper;

import com.example.shopserver.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名，密码和状态查询用户（用于登录）
     * @param user
     * @return
     */
    @Select("select * from user where username=#{username} and password=#{password} and status = 1")
    User getByUsernameAndPassword(User user);

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @Select("select * from user where username=#{username}")
    User getByUsername(String username);

    /**
     * 新增用户
     * @param user
     */
    @Insert("insert into user(username, password, avatarUrl, status, created_at) values(#{username}, #{password}, '/upload-images/default-avatar.png',1, #{createdAt})")
    void insert(User user);

    /**
     * 查询所有用户
     * @return
     */
    @Select("select * from user")
    List<User> selectAll();
    
    /**
     * 根据用户名模糊查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username like concat('%', #{username}, '%')")
    List<User> selectByUsernameLike(String username);

    /**
     * 更新用户状态(封停用户)
     * @param id
     */
    @Update("update user set status = 0 where id = #{id}")
    void updateStatusToStop(Integer id);

    /**
     * 更新用户状态(解封用户)
     * @param id
     */
    @Update("update user set status = 1 where id = #{id}")
    void updateStatusToPass(Integer id);

    /**
     * 更新用户个人信息
     * @param user
     */
    @Update("update user set password = #{password}, avatarUrl = #{avatarUrl} where username = #{username}")
    void updatePersonalInfo(User user);
}
