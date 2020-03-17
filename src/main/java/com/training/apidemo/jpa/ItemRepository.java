package com.training.apidemo.jpa;

//<editor-fold defaultstate="collapsed" desc="Import">
import com.training.apidemo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
//</editor-fold>

/**
 *
 * @author FB-001
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {

}
