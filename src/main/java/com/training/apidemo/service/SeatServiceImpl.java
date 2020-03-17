package com.training.apidemo.service;

import com.training.apidemo.entity.Seat;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.jpa.SeatRepository;
import java.util.ArrayList;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.UnexpectedRollbackException;

@Service("seatService")
public class SeatServiceImpl implements SeatService {

    static Logger logger = Logger.getLogger(SeatServiceImpl.class.getName());

    @Autowired
    private SeatRepository seatRepository;

    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Override
    public void delete(Long id) throws RecordNotFoundException {
        Optional<Seat> seat = seatRepository.findById(id);

        if (seat.isPresent()) {
            seatRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    @Override
    public Seat findById(Long id) throws RecordNotFoundException {
        Optional<Seat> seat = seatRepository.findById(id);

        if (seat.isPresent()) {
            return seat.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    public Collection<Seat> findAll() {
        return seatRepository.findAll();
    }

    public Page<Seat> findAll(PageRequest p) {
        return seatRepository.findAll(p);
    }

    @Override
    public Seat createOrUpdate(Seat seat) {
        if (seat.getId() == null) {
            seat.setAvailableNum(seat.getTotalNum());
            seat = seatRepository.save(seat);
            return seat;
        } else {
            Optional<Seat> curSeat = seatRepository.findById(seat.getId());//current data
            if (curSeat.isPresent()) {
                Seat newEntity = curSeat.get();
                newEntity.setSeatRank(seat.getSeatRank());
                newEntity.setTotalNum(seat.getTotalNum());
                newEntity.setAdultPrice(seat.getAdultPrice());
                newEntity.setChildPrice(seat.getChildPrice());
                newEntity.setInfantPrice(seat.getInfantPrice());
                newEntity = seatRepository.save(newEntity);
                return newEntity;
            } else {
                seat = seatRepository.save(seat);
                return seat;
            }
        }
    }

    @Override
    public Collection<Seat> findSuitableDepartingSlots(String departureLocation, String arrivalLocation, Date departureDate) throws UnexpectedRollbackException {
        Calendar c = Calendar.getInstance();
        c.setTime(departureDate);
        c.add(Calendar.DATE, 1);
        try {
            return seatRepository.findSuitableDepartingSlots(departureLocation, arrivalLocation, departureDate, c.getTime());
        } catch (UnexpectedRollbackException e) {
            logger.log(Level.FINE, e.toString());
            return new ArrayList<Seat>();
        }
    }

    @Override
    public Collection<Seat> findSuitableReturningSlots(String departureLocation, String arrivalLocation, Date returningDate) throws UnexpectedRollbackException {
        Calendar c = Calendar.getInstance();
        c.setTime(returningDate);
        c.add(Calendar.DATE, 1);
        try {
            return seatRepository.findSuitableReturningSlots(departureLocation, arrivalLocation, returningDate, c.getTime());
        } catch (UnexpectedRollbackException e) {
            logger.log(Level.FINE, e.toString());
            return new ArrayList<Seat>();
        }
    }
}
