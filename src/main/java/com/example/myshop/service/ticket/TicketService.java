package com.example.myshop.service.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myshop.entity.ticket.TicketStock;
import com.example.myshop.repository.ticket.TicketStockRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {
    @Autowired
    private TicketStockRepository ticketStockRepository;

    @Transactional
    public String rushBuyTicket(Long ticketId){
        
        TicketStock ticket = ticketStockRepository.findWithLockById(ticketId).orElse(null);
        if (ticket == null) {
            return "找不到該場次的演唱會";
        }

        if (ticket.getStock() <= 0) {
            return "票已全數售鑿";
        }

        ticket.setStock(ticket.getStock() - 1);
        ticketStockRepository.save(ticket);

        return "搶票成功!目前剩餘票數"+ ticket.getStock();        
    }


}
