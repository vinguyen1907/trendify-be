package com.project.uit.trendify.service.interfaces;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.entity.CategoryEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
    List<CategoryEntity> getAllCategories();
    CategoryEntity getCategoryById(Long categoryId);
    List<CategoryEntity> searchCategories(String keyword);
    Page<ProductDTO> getProductsInCategory(Long categoryId, Integer page, Integer size);
}
