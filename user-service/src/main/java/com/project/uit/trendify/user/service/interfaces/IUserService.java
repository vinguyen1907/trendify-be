package com.project.uit.trendify.user.service.interfaces;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.dto.UserDTO;
import com.project.uit.trendify.common.lib.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO updateUser(Long userId, UpdateUserRequest request);
    void updateUserAvatar(Long userId, String avatarUrl);
    List<ProductDTO> getRecommendProducts(Long userId, Integer page, Integer size);
}
