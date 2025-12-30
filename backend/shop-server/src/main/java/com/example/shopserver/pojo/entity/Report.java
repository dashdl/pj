package com.example.shopserver.pojo.entity;

import java.time.LocalDateTime;

/**
 * 举报实体类
 */
public class Report {
    private Integer id;
    private String reporterUsername;
    private Integer reportedType;
    private Integer reportedId;
    private String reportedUsername;
    private Integer reason;
    private String description;
    private String evidenceImages;
    private Integer status;
    private String adminRemark;
    private String processedBy;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;

    // 构造函数
    public Report() {}

    public Report(String reporterUsername, Integer reportedType, Integer reportedId, 
                  String reportedUsername, Integer reason, String description) {
        this.reporterUsername = reporterUsername;
        this.reportedType = reportedType;
        this.reportedId = reportedId;
        this.reportedUsername = reportedUsername;
        this.reason = reason;
        this.description = description;
        this.status = 1; // 默认待处理
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReporterUsername() {
        return reporterUsername;
    }

    public void setReporterUsername(String reporterUsername) {
        this.reporterUsername = reporterUsername;
    }

    public Integer getReportedType() {
        return reportedType;
    }

    public void setReportedType(Integer reportedType) {
        this.reportedType = reportedType;
    }

    public Integer getReportedId() {
        return reportedId;
    }

    public void setReportedId(Integer reportedId) {
        this.reportedId = reportedId;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
        this.reportedUsername = reportedUsername;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvidenceImages() {
        return evidenceImages;
    }

    public void setEvidenceImages(String evidenceImages) {
        this.evidenceImages = evidenceImages;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAdminRemark() {
        return adminRemark;
    }

    public void setAdminRemark(String adminRemark) {
        this.adminRemark = adminRemark;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reporterUsername='" + reporterUsername + '\'' +
                ", reportedType=" + reportedType +
                ", reportedId=" + reportedId +
                ", reportedUsername='" + reportedUsername + '\'' +
                ", reason=" + reason +
                ", description='" + description + '\'' +
                ", evidenceImages='" + evidenceImages + '\'' +
                ", status=" + status +
                ", adminRemark='" + adminRemark + '\'' +
                ", processedBy='" + processedBy + '\'' +
                ", processedAt=" + processedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
