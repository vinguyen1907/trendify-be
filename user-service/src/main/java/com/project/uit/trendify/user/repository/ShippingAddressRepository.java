package com.project.uit.trendify.user.repository;

import com.project.uit.trendify.user.entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Long> {
    List<ShippingAddressEntity> findAllByUserIdOrderByUpdatedAtDesc(Long userId);
}
