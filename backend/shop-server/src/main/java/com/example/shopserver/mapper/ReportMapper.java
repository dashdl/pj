package com.example.shopserver.mapper;

import com.example.shopserver.pojo.entity.Report;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReportMapper {
    
    /**
     * 添加举报
     */
    @Insert("INSERT INTO reports(reporter_username, reported_type, reported_id, reported_username, " +
            "reason, description, evidence_images, status) " +
            "VALUES (#{reporterUsername}, #{reportedType}, #{reportedId}, #{reportedUsername}, " +
            "#{reason}, #{description}, #{evidenceImages}, #{status})")
    void insertReport(Report report);

    /**
     * 查询所有举报
     */
    @Select("SELECT id, reporter_username AS reporterUsername, reported_type AS reportedType, " +
            "reported_id AS reportedId, reported_username AS reportedUsername, " +
            "reason, description, evidence_images AS evidenceImages, status, " +
            "admin_remark AS adminRemark, processed_by AS processedBy, processed_at AS processedAt, " +
            "created_at AS createdAt " +
            "FROM reports ORDER BY created_at DESC")
    List<Report> selectAllReports();

    /**
     * 根据状态查询举报
     */
    @Select("SELECT id, reporter_username AS reporterUsername, reported_type AS reportedType, " +
            "reported_id AS reportedId, reported_username AS reportedUsername, " +
            "reason, description, evidence_images AS evidenceImages, status, " +
            "admin_remark AS adminRemark, processed_by AS processedBy, processed_at AS processedAt, " +
            "created_at AS createdAt " +
            "FROM reports WHERE status = #{status} ORDER BY created_at DESC")
    List<Report> selectReportsByStatus(Integer status);

    /**
     * 根据举报类型查询举报
     */
    @Select("SELECT id, reporter_username AS reporterUsername, reported_type AS reportedType, " +
            "reported_id AS reportedId, reported_username AS reportedUsername, " +
            "reason, description, evidence_images AS evidenceImages, status, " +
            "admin_remark AS adminRemark, processed_by AS processedBy, processed_at AS processedAt, " +
            "created_at AS createdAt " +
            "FROM reports WHERE reported_type = #{reportedType} ORDER BY created_at DESC")
    List<Report> selectReportsByType(Integer reportedType);

    /**
     * 查询用户举报记录
     */
    @Select("SELECT id, reporter_username AS reporterUsername, reported_type AS reportedType, " +
            "reported_id AS reportedId, reported_username AS reportedUsername, " +
            "reason, description, evidence_images AS evidenceImages, status, " +
            "admin_remark AS adminRemark, processed_by AS processedBy, processed_at AS processedAt, " +
            "created_at AS createdAt " +
            "FROM reports WHERE reporter_username = #{username} ORDER BY created_at DESC")
    List<Report> selectReportsByReporter(String username);

    /**
     * 查询被举报记录
     */
    @Select("SELECT id, reporter_username AS reporterUsername, reported_type AS reportedType, " +
            "reported_id AS reportedId, reported_username AS reportedUsername, " +
            "reason, description, evidence_images AS evidenceImages, status, " +
            "admin_remark AS adminRemark, processed_by AS processedBy, processed_at AS processedAt, " +
            "created_at AS createdAt " +
            "FROM reports WHERE reported_username = #{username} ORDER BY created_at DESC")
    List<Report> selectReportsByReported(String username);

    /**
     * 根据ID查询举报详情
     */
    @Select("SELECT id, reporter_username AS reporterUsername, reported_type AS reportedType, " +
            "eported_id AS reportedId, reported_username AS reportedUsername, " +
            "eason, description, evidence_images AS evidenceImages, status, " +
            "dmin_remark AS adminRemark, processed_by AS processedBy, processed_at AS processedAt, " +
            "reated_at AS createdAt " +
            "ROM reports WHERE id = #{id}")
    Report selectReportById(Integer id);

    /**
     * 处理举报
     */
    @Update("UPDATE reports SET status = #{status}, admin_remark = #{adminRemark}, " +
            "processed_by = #{processedBy}, processed_at = NOW() WHERE id = #{id}")
    void processReport(@Param("id") Integer id, @Param("status") Integer status, 
                      @Param("adminRemark") String adminRemark, @Param("processedBy") String processedBy);

    /**
     * 更新举报状态
     */
    @Update("UPDATE reports SET status = #{status} WHERE id = #{id}")
    void updateReportStatus(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 删除举报
     */
    @Delete("DELETE FROM reports WHERE id = #{id}")
    void deleteReport(Integer id);

    /**
     * 查询待处理举报数量
     */
    @Select("SELECT COUNT(*) FROM reports WHERE status = 1")
    Integer selectPendingReportCount();

    /**
     * 查询用户举报统计
     */
    @Select("SELECT reported_type AS reportedType, COUNT(*) as count FROM reports WHERE reporter_username = #{username} GROUP BY reported_type")
    List<Report> selectReportStatsByReporter(String username);
}
