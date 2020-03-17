package com.training.apidemo.service;

import com.training.apidemo.entity.Order;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.jpa.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service("orderService")
public class OrderServiceImpl implements OrderService{
    static Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    @Autowired
    private OrderRepository orderRepository;

    public Order save(Order order){
        return orderRepository.save(order);
    }

    @Override
    public Collection<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> findAll(PageRequest p) {
        return orderRepository.findAll(p);
    }

    @Override
    public Order createOrUpdate(Order order) {
        if (order.getId() == null) {
            order = orderRepository.save(order);
            return order;
        } else {
            Optional<Order> curOrder = orderRepository.findById(order.getId());//current data
            if (curOrder.isPresent()) {
                Order newEntity = curOrder.get();
                newEntity.setContactAddress(order.getContactAddress());
                newEntity.setContactEmail(order.getContactEmail());
                newEntity.setContactFirstName(order.getContactFirstName());
                newEntity.setContactLastName(order.getContactLastName());
                newEntity.setContactPhoneNum(order.getContactPhoneNum());
                newEntity = orderRepository.save(newEntity);
                return newEntity;
            } else {
                order = orderRepository.save(order);
                return order;
            }
        }
    }
}
