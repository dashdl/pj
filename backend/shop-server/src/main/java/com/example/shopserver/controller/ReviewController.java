package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.Review;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评价控制器
 */
@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    /**
     * 添加评价
     */
    @PostMapping("/add")
    public Result addReview(@RequestBody Review review) {
        try {
            reviewService.addReview(review);
            return Result.success("评价添加成功");
        } catch (Exception e) {
            return Result.error("评价添加失败: " + e.getMessage());
        }
    }

    /**
     * 根据订单ID查询评价
     */
    @GetMapping("/order/{orderId}")
    public Result getReviewsByOrderId(@PathVariable Integer orderId) {
        try {
            List<Review> reviews = reviewService.getReviewsByOrderId(orderId);
            return Result.success(reviews);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据被评价者用户名查询评价
     */
    @GetMapping("/user/{username}")
    public Result getReviewsByReviewedUsername(@PathVariable String username) {
        try {
            List<Review> reviews = reviewService.getReviewsByReviewedUsername(username);
            return Result.success(reviews);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据商品ID查询评价
     */
    @GetMapping("/business/{businessId}")
    public Result getReviewsByBusinessId(@PathVariable Integer businessId) {
        try {
            List<Review> reviews = reviewService.getReviewsByBusinessId(businessId);
            return Result.success(reviews);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据评价者用户名查询评价
     */
    @GetMapping("/reviewer/{username}")
    public Result getReviewsByReviewerUsername(@PathVariable String username) {
        try {
            List<Review> reviews = reviewService.getReviewsByReviewerUsername(username);
            return Result.success(reviews);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 更新评价
     */
    @PutMapping("/update")
    public Result updateReview(@RequestBody Review review) {
        try {
            reviewService.updateReview(review);
            return Result.success("评价更新成功");
        } catch (Exception e) {
            return Result.error("评价更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除评价
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteReview(@PathVariable Integer id) {
        try {
            reviewService.deleteReview(id);
            return Result.success("评价删除成功");
        } catch (Exception e) {
            return Result.error("评价删除失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户平均评分
     */
    @GetMapping("/rating/{username}")
    public Result getAverageRatingByUsername(@PathVariable String username) {
        try {
            Double rating = reviewService.getAverageRatingByUsername(username);
            Integer count = reviewService.getReviewCountByUsername(username);
            return Result.success("平均评分: " + rating + ", 评价总数: " + count);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 管理员获取所有评价
     */
    @GetMapping("/admin/all")
    public Result getAllReviews() {
        try {
            List<Review> reviews = reviewService.getAllReviews();
            return Result.success(reviews);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
}
