package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.Business;
import com.example.shopserver.pojo.entity.User;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/list")
public class BusinessController {
    @Autowired
    private BusinessService businessService;

    /**
     * 查询全部商品列表
     * @return
     */
    @GetMapping("/all")
    public List<Business> getAllBusinesses() {
        List<Business> businessesList = businessService.getAllBusinesses();
        return businessesList;
    }

    /**
     * 根据分类查询商品列表
     * @param type
     * @return
     */
    @GetMapping("/type/{type}")
    public List<Business> getBusinessesByType(@PathVariable String type) {
        List<Business> businessesList = businessService.getBusinessesByType(type);
        return businessesList;
    }

    /**
     * 根据id查询商品详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public List<Business> getBusinessesById(@PathVariable Integer id) {
        List<Business> businessesList = businessService.getBusinessesById(id);
        // System.out.println(businessesList);
        return businessesList;
    }


    /**
     * 增加商品
     * @param business
     * @return
     */
    // TODO: 2025/1/19 新增商品接口 上传图片接口未实现
    @PostMapping("/add")
    public Result addBusiness(@RequestBody Business business) {
        businessService.addBusiness(business);
        return Result.success("添加成功");
    }

    /**
     * 修改商品
     * @param id
     * @param business
     * @return
     */
    // TODO: 2025/1/19 修改商品接口 上传图片接口未实现 
    @PutMapping("/update/{id}")
    // public Result updateBusiness(@PathVariable Integer id, @RequestBody Business business) {
    //     System.out.println("aaaaaaaaaaaaaaa" + id);
    //     System.out.println("bbbbbbbbbbbbbbbb" + business);
    //     businessService.updateBusiness(business,id);
    //     return Result.success("更新成功");
    // }
    public Result updateBusiness(@RequestBody Business business) {
        // System.out.println("aaaaaaaaaaaaaaa" + id);
        System.out.println("bbbbbbbbbbbbbbbb" + business);
        businessService.updateBusiness(business);
        return Result.success("更新成功");
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteBusiness(@PathVariable Integer id) {
        businessService.deleteBusiness(id);
        return Result.success("删除成功");
    }

    /**
     * 下架商品
     * @param id
     * @return
     */
    @GetMapping("/offshelf/{id}")
    public Result offshelfBusiness(@PathVariable Integer id) {
        businessService.offshelfBusiness(id);
        return Result.success("下架成功");
    }

    /**
     * 通过商品
     * @param id
     * @return
     */
    @GetMapping("/pass/{id}")
    public Result passBusiness(@PathVariable Integer id) {
        businessService.passBusiness(id);
        return Result.success("上架成功");
    }

    /**
     * 查询我的发布
     * @param user
     * @return
     */
    @GetMapping("/mypush/{user}")
    public List<Business> getMypush(@PathVariable String user){
        List<Business> businessesList = businessService.getMypush(user);
        return businessesList;
    }

    /**
     * 查询我卖出的
     * @param user
     * @return
     */
    @GetMapping("/myselled/{user}")
    public List<Business> getMyselled(@PathVariable String user){
        List<Business> businessesList = businessService.getMyselled(user);
        return businessesList;
    }

    /**
     * 处理搜索请求
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public List<Business> searchBusiness(@PathVariable String keyword){
        List<Business> businessesList = businessService.searchBusiness(keyword);
        return businessesList;
    }
}
