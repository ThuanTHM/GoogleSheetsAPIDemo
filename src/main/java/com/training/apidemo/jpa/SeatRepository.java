package com.training.apidemo.jpa;

//<editor-fold defaultstate="collapsed" desc="Import">
import com.training.apidemo.entity.Seat;
import java.util.Collection;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//</editor-fold>

/**
 *
 * @author FB-001
 */
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Modifying
//    @Query(value="SELECT s.* FROM seat s INNER JOIN flight f "
//            + " WHERE f.departure_airport_id IN ( SELECT a.id FROM airport a WHERE a.location LIKE :departureLocation ) "
//            + "      AND f.arrival_airport_id IN ( SELECT aa.id FROM airport aa WHERE aa.location LIKE :arrivalLocation ) "
//            + "      AND f.departure_time = :date "
//            + " AND s.flight_id = f.id", nativeQuery = true)
    @Query(value = "SELECT s FROM Seat s"
            + " WHERE s.flight.departureAirport.location LIKE :departureLocation "
            + "      AND s.flight.arrivalAirport.location LIKE :arrivalLocation "
            + "      AND s.flight.departureTime BETWEEN :startdate AND :enddate ")
    Collection<Seat> findSuitableDepartingSlots(@Param("departureLocation") String departureLocation, @Param("arrivalLocation") String arrivalLocation, @Param("startdate") Date departureDate, @Param("enddate") Date nextDay);

    @Modifying
//    @Query(value="SELECT s.* FROM seat s INNER JOIN flight f "
//            + " WHERE f.departure_airport_id IN ( SELECT a.id FROM airport a WHERE a.location LIKE :departureLocation ) "
//            + "      AND f.arrival_airport_id IN ( SELECT aa.id FROM airport aa WHERE aa.location LIKE :arrivalLocation ) "
//            + "      AND f.arrival_time = :date "
//            + " AND s.flight_id = f.id", nativeQuery = true)
    @Query(value = "SELECT s FROM Seat s"
            + " WHERE s.flight.departureAirport.location LIKE :departureLocation "
            + "      AND s.flight.arrivalAirport.location LIKE :arrivalLocation "
            + "      AND s.flight.arrivalTime BETWEEN :startdate AND :enddate ")
    Collection<Seat> findSuitableReturningSlots(@Param("departureLocation") String departureLocation, @Param("arrivalLocation") String arrivalLocation, @Param("startdate") Date arrivalDate, @Param("enddate") Date nextDay);
}
