package com.example.crud.dao;

import com.example.crud.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketDao extends CrudRepository<Ticket, Integer> {

}
