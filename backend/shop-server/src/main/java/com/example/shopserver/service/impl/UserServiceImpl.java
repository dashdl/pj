package com.example.shopserver.service.impl;

import com.example.shopserver.mapper.UserMapper;
import com.example.shopserver.pojo.entity.User;
import com.example.shopserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userMapper.getByUsernameAndPassword(user);
    }

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    @Override
    public Boolean isUsernameExists(String username) {
        if(userMapper.getByUsername(username) != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void register(User user) {
        // 设置注册时间为当前时间
        user.setCreatedAt(LocalDateTime.now());
        userMapper.insert(user);
    }

    /**
     * 获取所有用户信息
     * @return
     */
    @Override
    public List<User> userList() {
        return userMapper.selectAll();
    }
    
    /**
     * 根据用户名模糊查询用户
     * @param username
     * @return
     */
    @Override
    public List<User> searchUsersByUsername(String username) {
        return userMapper.selectByUsernameLike(username);
    }

    /**
     * 封停用户
     * @param id
     */
    @Override
    public void stopUser(Integer id) {
        userMapper.updateStatusToStop(id);
    }

    /**
     * 解除用户
     * @param id
     */
    @Override
    public void passUser(Integer id) {
        userMapper.updateStatusToPass(id);
    }

    /**
     * 更新用户个人信息
     * @param user
     */
    @Override
    public void updatePersonalInfo(User user) {
        userMapper.updatePersonalInfo(user);
    }


}
