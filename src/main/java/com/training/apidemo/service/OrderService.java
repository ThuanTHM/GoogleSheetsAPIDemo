package com.training.apidemo.service;

//<editor-fold desc="Import">
import com.training.apidemo.entity.Order;
import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//</editor-fold>

public interface OrderService {
    Collection<Order> findAll();

    Page<Order> findAll(PageRequest p);

    Order createOrUpdate(Order order);
}
