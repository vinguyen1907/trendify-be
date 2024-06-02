package com.project.uit.trendify.service;

import com.project.uit.trendify.common.lib.dto.ProductDTO;
import com.project.uit.trendify.common.lib.entity.Product;
import com.project.uit.trendify.common.lib.entity.ProductImageEntity;
import com.project.uit.trendify.entity.ProductElasticDocument;
import com.project.uit.trendify.repository.ProductImageRepository;
import com.project.uit.trendify.repository.ProductRepository;
import com.project.uit.trendify.repository.ProductSearchRepository;
import com.project.uit.trendify.service.interfaces.IProductSearchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductSearchService implements IProductSearchService {
    private final ProductSearchRepository productSearchRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public List<ProductDTO> searchProducts(String keyword) {
        List<ProductElasticDocument> products = productSearchRepository.findByNameOrDescriptionOrBrand(keyword);
        return products.stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId().toString());
            productDTO.setCode(product.getCode());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setBrand(product.getBrand());
            productDTO.setPrice(product.getPrice());
            var images = productImageRepository.findAllByProductId(product.getId());
            productDTO.setImageUrls(images.stream().map(ProductImageEntity::getUrl).toList());
            return productDTO;
        }).toList();
    }

    @Transactional
    public void syncProductsToElasticsearch() {
        List<Product> products = productRepository.findAll();
        List<ProductElasticDocument> elasticDocuments = products.stream()
                .map(product -> {
                    ProductElasticDocument doc = new ProductElasticDocument();
                    doc.setId(product.getId());
                    doc.setCode(product.getCode());
                    doc.setName(product.getName());
                    doc.setDescription(product.getDescription());
                    doc.setBrand(product.getBrand());
                    doc.setPrice(product.getPrice());
                    // Map các trường khác...
                    return doc;
                })
                .collect(Collectors.toList());

        productSearchRepository.saveAll(elasticDocuments);
    }
}
