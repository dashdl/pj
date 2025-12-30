package com.example.shopserver.mapper;

import com.example.shopserver.pojo.entity.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {
    
    /**
     * 添加评价
     */
    @Insert("INSERT INTO reviews(order_id, reviewer_username, reviewed_username, business_id, rating, content, review_type, is_anonymous) " +
            "VALUES (#{orderId}, #{reviewerUsername}, #{reviewedUsername}, #{businessId}, #{rating}, #{content}, #{reviewType}, #{isAnonymous})")
    void insertReview(Review review);

    /**
     * 根据订单ID查询评价
     */
    @Select("SELECT * FROM reviews WHERE order_id = #{orderId}")
    List<Review> selectReviewsByOrderId(Integer orderId);

    /**
     * 根据被评价者用户名查询评价
     */
    @Select("SELECT * FROM reviews WHERE reviewed_username = #{username} ORDER BY created_at DESC")
    List<Review> selectReviewsByReviewedUsername(String username);

    /**
     * 根据商品ID查询评价
     */
    @Select("SELECT * FROM reviews WHERE business_id = #{businessId} ORDER BY created_at DESC")
    List<Review> selectReviewsByBusinessId(Integer businessId);

    /**
     * 根据评价者用户名查询评价
     */
    @Select("SELECT * FROM reviews WHERE reviewer_username = #{username} ORDER BY created_at DESC")
    List<Review> selectReviewsByReviewerUsername(String username);

    /**
     * 更新评价
     */
    @Update("UPDATE reviews SET rating = #{rating}, content = #{content} WHERE id = #{id}")
    void updateReview(Review review);

    /**
     * 删除评价
     */
    @Delete("DELETE FROM reviews WHERE id = #{id}")
    void deleteReview(Integer id);

    /**
     * 查询用户平均评分
     */
    @Select("SELECT AVG(rating) FROM reviews WHERE reviewed_username = #{username}")
    Double selectAverageRatingByUsername(String username);

    /**
     * 查询用户评价总数
     */
    @Select("SELECT COUNT(*) FROM reviews WHERE reviewed_username = #{username}")
    Integer selectReviewCountByUsername(String username);
    
    /**
     * 查询所有评价
     */
    @Select("SELECT * FROM reviews ORDER BY created_at DESC")
    List<Review> selectAllReviews();
}
