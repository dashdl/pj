package com.example.shopserver.pojo.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResult {
    private String imagePath;

    public static UploadResult success(String imagePath) {
        return new UploadResult(imagePath);
    }
}
