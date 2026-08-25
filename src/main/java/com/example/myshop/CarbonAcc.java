package com.example.myshop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "carbonAccs")
public class CarbonAcc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String compName;
    private double credits;
    private double allocated;
    private Boolean isCompliant;
    private String tradeDate;

    public Long getId(){return id;}
    public void setId(Long id){this.id =id;}

    public String getCompName(){return compName;}
    public void setCompName(String compName){this.compName = compName;}

    public double getCredits(){return credits;}
    public void setCredits(double credits){this.credits = credits;}

    public double getAllocated(){return allocated;}
    public void setAllocated(double allocated){this.allocated = allocated;}

    public Boolean getIsCompliant(){return isCompliant;}
    public void setIsCompliant(Boolean isCompliant){this.isCompliant = isCompliant;}

    public String getTradeDate(){return tradeDate;}
    public void setTradeDate(String tradeDate){this.tradeDate = tradeDate;}

    
}
