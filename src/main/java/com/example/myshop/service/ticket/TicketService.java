package com.example.myshop.service.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.myshop.entity.ticket.TicketStock;
import com.example.myshop.repository.ticket.TicketStockRepository;

import jakarta.transaction.Transactional;

@Service
public class TicketService {
    @Autowired
    private TicketStockRepository ticketStockRepository;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Transactional
    public String rushBuyTicket(Long ticketId){
        
        String redisKey = "ticket_stock:" + ticketId;

        Long remainingStock = redisTemplate.opsForValue().decrement(redisKey);
        
        if (remainingStock < 0) {

            redisTemplate.opsForValue().increment(redisKey);
            return "❌ 搶票失敗：[Redis 攔截] 周杰倫門票在記憶體中已全數售罄！";
        }
        
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
