package com.training.apidemo.jpa;

//<editor-fold desc="Import">
import com.training.apidemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//</editor-fold>

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
