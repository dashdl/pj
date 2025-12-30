package com.example.shopserver.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String msg; //响应信息
    private Object results; //响应数据

    //成功响应 增删改
    public static Result success(String msg) {
        return new Result(msg,null);
    }

    // 成功响应 查
    public static Result success(Object results) {
        return new Result("success",results);
    }

    //失败响应
    public static Result error(String msg) { return new Result(msg,null); }
}
