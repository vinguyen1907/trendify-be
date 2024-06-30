package com.project.uit.trendify.user.service;

import com.project.uit.trendify.user.service.interfaces.ICloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {
    private final Cloudinary cloudinary;

    public Map uploadUserAvatar(Long userId, MultipartFile image) {
        try {
            Map<String, String> options = new HashMap<>();
            options.put("public_id", String.valueOf(userId));
            options.put("folder", "trendify/user_avatar/");
            return cloudinary.uploader().upload(image.getBytes(), options);
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading image -- " + e.getMessage());
        }
    }
}
