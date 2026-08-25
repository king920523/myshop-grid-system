package com.example.myshop.controller.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myshop.service.ticket.TicketService;

@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/ticket/rush/{ticketId}")
    public String rushTicket(
        @PathVariable Long ticketId,
        @RequestParam String username
    ){
        return ticketService.rushBuyTicket(ticketId, username);
    }

}
