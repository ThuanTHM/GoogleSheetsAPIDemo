package com.training.apidemo.service;

import com.training.apidemo.entity.Order;
import com.training.apidemo.exception.RecordNotFoundException;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderService {
    Collection<Order> findAll();
    Page<Order> findAll(PageRequest p);
    Order createOrUpdate(Order order);
}
