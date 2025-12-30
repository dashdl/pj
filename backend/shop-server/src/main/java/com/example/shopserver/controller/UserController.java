package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.User;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取所有用户信息
     * @return
     */
    @GetMapping("/alluser")
    public List<User> userList(){
        List<User> userList = userService.userList();
        return userList;
    }
    
    /**
     * 根据用户名模糊查询用户
     * @param username
     * @return
     */
    @GetMapping("/searchuser")
    public List<User> searchUser(@RequestParam String username){
        List<User> userList = userService.searchUsersByUsername(username);
        return userList;
    }

    /**
     * 封停用户
     * @return
     */
    @GetMapping("/stopuser/{id}")
    public Result stopUser(@PathVariable Integer id) {
        userService.stopUser(id);
        return Result.success("封停成功");
    }

    /**
     * 解除封停用户
     * @return
     */
    @GetMapping("/passuser/{id}")
    public Result passUser(@PathVariable Integer id) {
        userService.passUser(id);
        return Result.success("解除封停成功");
    }

    /**
     * 更新个人信息
     * @param user
     * @return
     */
    @PutMapping("/updatePersonalInfo")
    public Result updatePersonalInfo(@RequestBody User user){
        Boolean isUsernameExists = userService.isUsernameExists(user.getUsername());
        if (!isUsernameExists){
            return Result.error("用户名不存在");
        }
        userService.updatePersonalInfo(user);
        return Result.success("修改成功");
    }


}
