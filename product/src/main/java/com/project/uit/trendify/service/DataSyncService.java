package com.project.uit.trendify.service;

import com.project.uit.trendify.common.lib.entity.Product;
import com.project.uit.trendify.entity.ProductElasticDocument;
import com.project.uit.trendify.repository.ProductRepository;
import com.project.uit.trendify.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSyncService {
    private final ProductRepository productRepository;
    private final ProductSearchRepository productElasticRepository;

    @Scheduled(fixedRate = 60000)  // Chạy mỗi 60 giây
    public void syncData() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            ProductElasticDocument doc = new ProductElasticDocument();
            doc.setId(product.getId());
            doc.setCode(product.getCode());
            doc.setName(product.getName());
            doc.setDescription(product.getDescription());
            doc.setBrand(product.getBrand());
            doc.setPrice(product.getPrice());
            doc.setOuterMaterial(product.getOuterMaterial());
            doc.setInnerMaterial(product.getInnerMaterial());
            doc.setSole(product.getSole());
            doc.setClosure(product.getClosure());
            doc.setHeelHeight(product.getHeelHeight());
            doc.setHeelType(product.getHeelType());
            doc.setShoeWidth(product.getShoeWidth());
            productElasticRepository.save(doc);
        });
    }
}