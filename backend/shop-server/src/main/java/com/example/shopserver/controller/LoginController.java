package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.User;
import com.example.shopserver.pojo.result.LoginResult;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.UserService;
import com.example.shopserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public LoginResult login(@RequestBody User user) {
        User u = userService.login(user);
        if(u==null){
            return LoginResult.error("登录失败");
        }
        return LoginResult.success(u.getUsername(),u.getAvatarUrl(),"token","登录成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        Boolean isExists = userService.isUsernameExists(user.getUsername());
        if(isExists){
            return Result.error("用户名已存在");
        }
        System.out.println(user);
        userService.register(user);
        return Result.success("注册成功");
    }


}
