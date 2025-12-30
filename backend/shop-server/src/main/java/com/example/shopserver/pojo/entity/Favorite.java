package com.example.shopserver.pojo.entity;

import java.time.LocalDateTime;

/**
 * 收藏实体类
 */
public class Favorite {
    private Integer id;
    private String username;
    private Integer businessId;
    private LocalDateTime createdAt;
    private String folderName;

    // 构造函数
    public Favorite() {}

    public Favorite(String username, Integer businessId, String folderName) {
        this.username = username;
        this.businessId = businessId;
        this.folderName = folderName;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", businessId=" + businessId +
                ", createdAt=" + createdAt +
                ", folderName='" + folderName + '\'' +
                '}';
    }
}
