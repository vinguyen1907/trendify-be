package com.project.uit.trendify.user.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ICloudinaryService {
    Map uploadUserAvatar(Long userId, MultipartFile image);
}
