package com.project.uit.trendify.repository;

import com.project.uit.trendify.entity.ProductElasticDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductSearchRepository extends ElasticsearchRepository<ProductElasticDocument, Long> {
    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"description\": \"?0\"}}]}}")
    List<ProductElasticDocument> findByNameOrDescription(String text);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": \"?0\"}}, {\"match\": {\"description\": \"?0\"}}, {\"match\": {\"brand\": \"?0\"}}]}}")
    List<ProductElasticDocument> findByNameOrDescriptionOrBrand(String text);

}