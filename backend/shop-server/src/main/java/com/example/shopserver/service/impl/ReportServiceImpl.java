package com.example.shopserver.service.impl;

import com.example.shopserver.mapper.ReportMapper;
import com.example.shopserver.pojo.entity.Report;
import com.example.shopserver.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 举报服务实现类
 */
@Service
public class ReportServiceImpl implements ReportService {
    
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public void addReport(Report report) {
        reportMapper.insertReport(report);
    }

    @Override
    public List<Report> getAllReports() {
        return reportMapper.selectAllReports();
    }

    @Override
    public List<Report> getReportsByStatus(Integer status) {
        return reportMapper.selectReportsByStatus(status);
    }

    @Override
    public List<Report> getReportsByType(Integer reportedType) {
        return reportMapper.selectReportsByType(reportedType);
    }

    @Override
    public List<Report> getReportsByReporter(String username) {
        return reportMapper.selectReportsByReporter(username);
    }

    @Override
    public List<Report> getReportsByReported(String username) {
        return reportMapper.selectReportsByReported(username);
    }

    @Override
    public Report getReportById(Integer id) {
        return reportMapper.selectReportById(id);
    }

    @Override
    public void processReport(Integer id, Integer status, String adminRemark, String processedBy) {
        reportMapper.processReport(id, status, adminRemark, processedBy);
    }

    @Override
    public void updateReportStatus(Integer id, Integer status) {
        reportMapper.updateReportStatus(id, status);
    }

    @Override
    public void deleteReport(Integer id) {
        reportMapper.deleteReport(id);
    }

    @Override
    public Integer getPendingReportCount() {
        return reportMapper.selectPendingReportCount();
    }
}
