package com.project.uit.trendify.shipping.repository;

import com.project.uit.trendify.shipping.entity.ShippingStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingStatusRepository extends JpaRepository<ShippingStatusEntity, Long> {

}
