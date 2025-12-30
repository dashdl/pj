package com.example.shopserver.controller;

import com.example.shopserver.pojo.result.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.MultipartConfigElement;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

// 静态资源配置类
@Configuration
class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取应用工作目录
        String appHome = System.getProperty("user.dir");
        // 配置静态资源映射，让/upload-images/**路径能访问到应用工作目录下的upload-images文件夹
        registry.addResourceHandler("/upload-images/**")
                .addResourceLocations("file:" + appHome + "/upload-images/")
                .setCachePeriod(0); // 开发环境不缓存
        
        System.out.println("静态资源配置: 映射/upload-images/** 到 " + appHome + "/upload-images/");
    }
    
    // 配置文件上传限制
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置单个文件的最大大小为10MB
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        // 设置总上传数据的最大大小为20MB
        factory.setMaxRequestSize(DataSize.ofMegabytes(20));
        return factory.createMultipartConfig();
    }
}

@RestController
@Slf4j
public class FileUploadController {
    @PostMapping("/upload")
    public ResponseEntity<?> imgUpDown(@RequestParam("image") MultipartFile file) {
        try {
            log.info("接收到文件上传请求，文件名: {}", file.getOriginalFilename());
            log.info("文件大小: {} bytes", file.getSize());
            log.info("文件类型: {}", file.getContentType());
            
            // 获取文件名
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                log.error("文件名不能为空");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件名不能为空");
            }

            // 生成唯一文件名
            String newFileName = UUID.randomUUID() + "-" + fileName;

            // 获取应用工作目录，并设置上传目录为应用根目录下的upload-images
            String appHome = System.getProperty("user.dir");
            log.info("应用工作目录: {}", appHome);
            
            // 设置上传目录为应用工作目录下的upload-images
            String uploadDir = appHome + "/upload-images/";
            Path uploadPath = Paths.get(uploadDir);
            
            // 确保目录存在
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("创建上传目录: {}", uploadPath.toAbsolutePath());
            }

            // 保存文件
            Path filePath = uploadPath.resolve(newFileName);
            file.transferTo(filePath.toFile());
            log.info("文件上传成功: {}", filePath.toAbsolutePath());

            // 返回相对路径，用于前端访问
            String imagePath = "/upload-images/" + newFileName;
            log.info("返回图片路径: {}", imagePath);
            return ResponseEntity.ok(UploadResult.success(imagePath));
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("文件上传失败: " + e.getMessage());
        }
    }
}
