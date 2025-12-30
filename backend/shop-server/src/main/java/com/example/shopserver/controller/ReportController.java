package com.example.shopserver.controller;

import com.example.shopserver.pojo.entity.Report;
import com.example.shopserver.pojo.result.Result;
import com.example.shopserver.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 举报控制器
 */
@RestController
@RequestMapping("/report")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;

    /**
     * 添加举报
     */
    @PostMapping("/add")
    public Result addReport(@RequestBody Report report) {
        try {
            reportService.addReport(report);
            return Result.success("举报提交成功，我们会尽快处理");
        } catch (Exception e) {
            return Result.error("举报提交失败: " + e.getMessage());
        }
    }

    /**
     * 查询所有举报（管理员）
     */
    @GetMapping("/admin/all")
    public Result getAllReports() {
        try {
            List<Report> reports = reportService.getAllReports();
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据状态查询举报（管理员）
     */
    @GetMapping("/admin/status/{status}")
    public Result getReportsByStatus(@PathVariable Integer status) {
        try {
            List<Report> reports = reportService.getReportsByStatus(status);
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据举报类型查询举报（管理员）
     */
    @GetMapping("/admin/type/{reportedType}")
    public Result getReportsByType(@PathVariable Integer reportedType) {
        try {
            List<Report> reports = reportService.getReportsByType(reportedType);
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户举报记录
     */
    @GetMapping("/my/{username}")
    public Result getReportsByReporter(@PathVariable String username) {
        try {
            List<Report> reports = reportService.getReportsByReporter(username);
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询被举报记录
     */
    @GetMapping("/reported/{username}")
    public Result getReportsByReported(@PathVariable String username) {
        try {
            List<Report> reports = reportService.getReportsByReported(username);
            return Result.success(reports);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID查询举报详情
     */
    @GetMapping("/{id}")
    public Result getReportById(@PathVariable Integer id) {
        try {
            Report report = reportService.getReportById(id);
            return Result.success(report);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }

    /**
     * 处理举报（管理员）
     */
    @PutMapping("/admin/process/{id}")
    public Result processReport(@PathVariable Integer id, @RequestParam Integer status, 
                               @RequestParam(required = false) String adminRemark, 
                               @RequestParam String processedBy) {
        try {
            reportService.processReport(id, status, adminRemark, processedBy);
            return Result.success("举报处理完成");
        } catch (Exception e) {
            return Result.error("处理失败: " + e.getMessage());
        }
    }

    /**
     * 更新举报状态（管理员）
     */
    @PutMapping("/admin/status/{id}")
    public Result updateReportStatus(@PathVariable Integer id, @RequestParam Integer status) {
        try {
            reportService.updateReportStatus(id, status);
            return Result.success("状态更新成功");
        } catch (Exception e) {
            return Result.error("更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除举报（管理员）
     */
    @DeleteMapping("/admin/delete/{id}")
    public Result deleteReport(@PathVariable Integer id) {
        try {
            reportService.deleteReport(id);
            return Result.success("举报删除成功");
        } catch (Exception e) {
            return Result.error("删除失败: " + e.getMessage());
        }
    }

    /**
     * 查询待处理举报数量（管理员）
     */
    @GetMapping("/admin/pendingCount")
    public Result getPendingReportCount() {
        try {
            Integer count = reportService.getPendingReportCount();
            return Result.success(count);
        } catch (Exception e) {
            return Result.error("查询失败: " + e.getMessage());
        }
    }
}
