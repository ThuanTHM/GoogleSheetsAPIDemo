package com.training.apidemo.service;

//<editor-fold desc="Import">
import com.training.apidemo.entity.User;
//</editor-fold>

public interface UserService {
    User save(User user);

    User findByEmail(String email);

    public User findByUserName(String username);
}
