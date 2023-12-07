package com.example.gamestorespring.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageUtil {
    public static String generateRandomName(String contentType) throws IOException {
        String[] parts = contentType.split("/");
        if (parts[1] == "octet-stream" || parts[1] == null) {
            throw new IOException();
        }
        String fileType = "." + parts[1];
        String uuid = UUID.randomUUID().toString() + fileType;
        return uuid;
    }

    public static String saveImage(MultipartFile file) throws IOException {
        String uploadPath = "src/main/resources/static/img";
        File uploadDirPath = new File(uploadPath);

        if (!uploadDirPath.exists()) {
            uploadDirPath.mkdirs();
        }

        try {
            String nameFile = generateRandomName(file.getContentType());
            Path filePath = Paths.get(uploadPath + "/" + nameFile);
            Files.write(filePath, file.getBytes());
            return nameFile;
        } catch (IOException e) {
            throw e;
        }
    }

    public static String getDefaultImageGame(){
        return "default.jpg";
    }
}
