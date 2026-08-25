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
    public String rushBuyTicket(Long ticketId, String username){
        
        String stockKey = "ticket_stock:" + ticketId;
        String userSetKey = "ticket_user_set" + ticketId;

        Boolean isAlreadyBought = redisTemplate.opsForSet().isMember(userSetKey, username);

        if (Boolean.TRUE.equals(isAlreadyBought)) {
            return "❌ 搶票失敗：[黃牛攔截] 系統偵測到帳號 " + username + " 已經成功購買過門票，本場次每人限購一張！";
        }


        Long remainingStock = redisTemplate.opsForValue().decrement(stockKey);
        
        if (remainingStock < 0) {

            redisTemplate.opsForValue().increment(stockKey);
            return "❌ 搶票失敗：[Redis 攔截] 周杰倫門票在記憶體中已全數售罄！";
        }

        redisTemplate.opsForSet().add(userSetKey, username);
        
        TicketStock ticket = ticketStockRepository.findWithLockById(ticketId).orElse(null);
        if (ticket == null) {
            return "找不到該場次的演唱會";
        }


        ticket.setStock(ticket.getStock() - 1);
        ticketStockRepository.save(ticket);

        return "搶票成功!目前剩餘票數"+ ticket.getStock();        
    }


}
