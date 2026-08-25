package com.example.myshop.repository.ticket;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.myshop.entity.ticket.TicketStock;

import jakarta.persistence.LockModeType;

public interface TicketStockRepository extends JpaRepository<TicketStock,Long>{

    // 🌟 終極黑科技：利用 @Lock 標籤，下達最高等級的悲觀寫入鎖（PESSIMISTIC_WRITE）
    // 只要有人呼叫這個方法撈資料，資料庫那一列（Row）資料就會立刻被反鎖，不准別人看！
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TicketStock> findWithLockById(Long id);
    
}
