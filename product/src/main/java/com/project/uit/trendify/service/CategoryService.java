package com.project.uit.trendify.service;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.entity.CategoryEntity;
import com.project.uit.trendify.repository.CategoryRepository;
import com.project.uit.trendify.repository.ProductRepository;
import com.project.uit.trendify.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public List<CategoryEntity> searchCategories(String keyword) {
        return categoryRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Page<ProductDTO> getProductsInCategory(Long categoryId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAllByCategoryId(categoryId, pageable).map(ProductDTO::new);
    }
}
