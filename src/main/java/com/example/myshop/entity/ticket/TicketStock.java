package com.example.myshop.entity.ticket;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticketstocks")
public class TicketStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String concertName;
    private Integer stock;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getConcertName(){return concertName;}
    public void setConcertName(String concertName){this.concertName = concertName;}

    public Integer getStock(){return stock;}
    public void setStock(Integer stock){this.stock = stock;}
    
}
