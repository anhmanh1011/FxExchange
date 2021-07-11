package com.api.orderfx.repository;

import com.api.orderfx.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends  JpaRepository<OrderEntity, Long>  {
}
