package com.training.apidemo.jpa;

//<editor-fold defaultstate="collapsed" desc="Import">
import com.training.apidemo.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
//</editor-fold>

/**
 *
 * @author FB-001
 */
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
