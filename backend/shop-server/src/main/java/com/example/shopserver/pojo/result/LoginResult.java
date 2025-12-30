package com.example.shopserver.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResult {
    private String username; //用户名
    private String avatarUrl; //头像url
    private String token; //token
    private String msg; //响应信息

    //成功响应
    public static LoginResult success(String username, String avatarUrl, String token, String msg) {
        return new LoginResult(username, avatarUrl, token, msg);
    }

    //失败响应
    public static LoginResult error(String msg) {
        return new LoginResult(null, null, null, msg);
    }
}
