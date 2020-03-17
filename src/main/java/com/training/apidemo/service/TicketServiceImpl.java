package com.training.apidemo.service;

import com.training.apidemo.entity.Ticket;
import com.training.apidemo.exception.RecordNotFoundException;
import com.training.apidemo.jpa.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@Service("ticketService")
public class TicketServiceImpl implements TicketService {

    static Logger logger = Logger.getLogger(TicketServiceImpl.class.getName());

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void delete(Long id) throws RecordNotFoundException {
        Optional<Ticket> ticket = ticketRepository.findById(id);

        if (ticket.isPresent()) {
            ticketRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    @Override
    public Ticket findById(Long id) throws RecordNotFoundException {
        Optional<Ticket> ticket = ticketRepository.findById(id);

        if (ticket.isPresent()) {
            return ticket.get();
        } else {
            throw new RecordNotFoundException("No record exist for given id");
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Page<Ticket> findAll(PageRequest p) {
        return ticketRepository.findAll(p);
    }

    @Override
    public Ticket createOrUpdate(Ticket ticket) {
        if (ticket.getId() == null) {
            ticket = ticketRepository.save(ticket);
            return ticket;
        } else {
            Optional<Ticket> curTicket = ticketRepository.findById(ticket.getId());//current data
            if (curTicket.isPresent()) {
                Ticket newEntity = curTicket.get();
                newEntity.setAgeRank(ticket.getAgeRank());
                newEntity.setFirstname(ticket.getFirstname());
                newEntity.setLastname(ticket.getLastname());
                newEntity.setGoTripPrice(ticket.getGoTripPrice());
                newEntity.setGoTripSeat(ticket.getGoTripSeat());
                newEntity.setReturnTripPrice(ticket.getReturnTripPrice());
                newEntity.setReturnTripSeat(ticket.getReturnTripSeat());
                newEntity.setRoundticket(ticket.isRoundticket());
                newEntity.setOrder(ticket.getOrder());
                newEntity = ticketRepository.save(newEntity);
                return newEntity;
            } else {
                ticket = ticketRepository.save(ticket);
                return ticket;
            }
        }
    }
}
