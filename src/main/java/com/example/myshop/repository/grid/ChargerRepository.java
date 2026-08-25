package com.example.myshop.repository.grid;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.myshop.entity.grid.Charger;

public interface ChargerRepository extends JpaRepository<Charger,Long>{

    @Query("SELECT c FROM Charger c JOIN FETCH c.powerPlant")
    List<Charger> findAllChargersWithPlant();
    
}