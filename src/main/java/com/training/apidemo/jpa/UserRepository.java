package com.training.apidemo.jpa;

//<editor-fold desc="Import">
import com.training.apidemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//</editor-fold>

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String userName);
}
