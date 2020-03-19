package com.training.apidemo.entity;

//<editor-fold desc="Import">
import javax.persistence.*;
//</editor-fold>

@Entity
@Table(name = "role", schema = "dbexample")
public class Role {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String role = "";

    public Role() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role", nullable = false)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
