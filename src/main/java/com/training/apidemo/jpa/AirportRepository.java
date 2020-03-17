package com.training.apidemo.jpa;
//<editor-fold defaultstate="collapsed" desc="Import">
import com.training.apidemo.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//</editor-fold>

import java.util.Collection;

/**
 *
 * @author FB-001
 */

public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("SELECT DISTINCT a.location FROM Airport a")
    Collection<String> findDistinctLocations();
}
