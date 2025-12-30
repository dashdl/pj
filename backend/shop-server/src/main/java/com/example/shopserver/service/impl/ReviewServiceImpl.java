package com.example.shopserver.service.impl;

import com.example.shopserver.mapper.ReviewMapper;
import com.example.shopserver.pojo.entity.Review;
import com.example.shopserver.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评价服务实现类
 */
@Service
public class ReviewServiceImpl implements ReviewService {
    
    @Autowired
    private ReviewMapper reviewMapper;

    @Override
    public void addReview(Review review) {
        reviewMapper.insertReview(review);
    }

    @Override
    public List<Review> getReviewsByOrderId(Integer orderId) {
        return reviewMapper.selectReviewsByOrderId(orderId);
    }

    @Override
    public List<Review> getReviewsByReviewedUsername(String username) {
        return reviewMapper.selectReviewsByReviewedUsername(username);
    }

    @Override
    public List<Review> getReviewsByBusinessId(Integer businessId) {
        return reviewMapper.selectReviewsByBusinessId(businessId);
    }

    @Override
    public List<Review> getReviewsByReviewerUsername(String username) {
        return reviewMapper.selectReviewsByReviewerUsername(username);
    }

    @Override
    public void updateReview(Review review) {
        reviewMapper.updateReview(review);
    }

    @Override
    public void deleteReview(Integer id) {
        reviewMapper.deleteReview(id);
    }

    @Override
    public Double getAverageRatingByUsername(String username) {
        Double rating = reviewMapper.selectAverageRatingByUsername(username);
        return rating != null ? rating : 0.0;
    }

    @Override
    public Integer getReviewCountByUsername(String username) {
        return reviewMapper.selectReviewCountByUsername(username);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewMapper.selectAllReviews();
    }
}
