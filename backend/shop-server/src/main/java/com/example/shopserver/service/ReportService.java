package com.example.shopserver.service;

import com.example.shopserver.pojo.entity.Report;

import java.util.List;

/**
 * 举报服务接口
 */
public interface ReportService {
    
    /**
     * 添加举报
     */
    void addReport(Report report);
    
    /**
     * 查询所有举报
     */
    List<Report> getAllReports();
    
    /**
     * 根据状态查询举报
     */
    List<Report> getReportsByStatus(Integer status);
    
    /**
     * 根据举报类型查询举报
     */
    List<Report> getReportsByType(Integer reportedType);
    
    /**
     * 查询用户举报记录
     */
    List<Report> getReportsByReporter(String username);
    
    /**
     * 查询被举报记录
     */
    List<Report> getReportsByReported(String username);
    
    /**
     * 根据ID查询举报详情
     */
    Report getReportById(Integer id);
    
    /**
     * 处理举报
     */
    void processReport(Integer id, Integer status, String adminRemark, String processedBy);
    
    /**
     * 更新举报状态
     */
    void updateReportStatus(Integer id, Integer status);
    
    /**
     * 删除举报
     */
    void deleteReport(Integer id);
    
    /**
     * 查询待处理举报数量
     */
    Integer getPendingReportCount();
}
