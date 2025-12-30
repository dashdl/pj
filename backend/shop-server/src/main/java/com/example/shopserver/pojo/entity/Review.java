package com.example.shopserver.pojo.entity;

import java.time.LocalDateTime;

/**
 * 评价实体类
 */
public class Review {
    private Integer id;
    private Integer orderId;
    private String reviewerUsername;
    private String reviewedUsername;
    private Integer businessId;
    private Integer rating;
    private String content;
    private Integer reviewType;
    private LocalDateTime createdAt;
    private Boolean isAnonymous;

    // 构造函数
    public Review() {}

    public Review(Integer orderId, String reviewerUsername, String reviewedUsername, 
                  Integer businessId, Integer rating, String content, Integer reviewType, Boolean isAnonymous) {
        this.orderId = orderId;
        this.reviewerUsername = reviewerUsername;
        this.reviewedUsername = reviewedUsername;
        this.businessId = businessId;
        this.rating = rating;
        this.content = content;
        this.reviewType = reviewType;
        this.isAnonymous = isAnonymous;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getReviewerUsername() {
        return reviewerUsername;
    }

    public void setReviewerUsername(String reviewerUsername) {
        this.reviewerUsername = reviewerUsername;
    }

    public String getReviewedUsername() {
        return reviewedUsername;
    }

    public void setReviewedUsername(String reviewedUsername) {
        this.reviewedUsername = reviewedUsername;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReviewType() {
        return reviewType;
    }

    public void setReviewType(Integer reviewType) {
        this.reviewType = reviewType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", reviewerUsername='" + reviewerUsername + '\'' +
                ", reviewedUsername='" + reviewedUsername + '\'' +
                ", businessId=" + businessId +
                ", rating=" + rating +
                ", content='" + content + '\'' +
                ", reviewType=" + reviewType +
                ", createdAt=" + createdAt +
                ", isAnonymous=" + isAnonymous +
                '}';
    }
}
