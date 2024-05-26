package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.dto.UserDTO;
import com.project.uit.trendify.common.lib.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO updateUser(Long userId, UpdateUserRequest request);
    List<ProductDTO> getRecommendProducts(Long userId);
}
