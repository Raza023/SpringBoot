package com.example.crud.controller;

import com.example.crud.dao.TicketDao;
import com.example.crud.entities.Ticket;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @PostMapping("/bookTicket")
    public String bookTicket(List<Ticket> tickets) {
        ticketDao.saveAll(tickets);
        return tickets.size() + "tickets booked.";
    }

    @GetMapping("/getTickets")
    public List<Ticket> getTickets() {
        return (List<Ticket>) ticketDao.findAll();
    }
}
