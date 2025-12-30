package com.example.shopserver.service;

import com.example.shopserver.pojo.entity.Review;

import java.util.List;

/**
 * 评价服务接口
 */
public interface ReviewService {
    
    /**
     * 添加评价
     */
    void addReview(Review review);
    
    /**
     * 根据订单ID查询评价
     */
    List<Review> getReviewsByOrderId(Integer orderId);
    
    /**
     * 根据被评价者用户名查询评价
     */
    List<Review> getReviewsByReviewedUsername(String username);
    
    /**
     * 根据商品ID查询评价
     */
    List<Review> getReviewsByBusinessId(Integer businessId);
    
    /**
     * 根据评价者用户名查询评价
     */
    List<Review> getReviewsByReviewerUsername(String username);
    
    /**
     * 更新评价
     */
    void updateReview(Review review);
    
    /**
     * 删除评价
     */
    void deleteReview(Integer id);
    
    /**
     * 查询用户平均评分
     */
    Double getAverageRatingByUsername(String username);
    
    /**
     * 查询用户评价总数
     */
    Integer getReviewCountByUsername(String username);
    
    /**
     * 获取所有评价
     */
    List<Review> getAllReviews();
}
