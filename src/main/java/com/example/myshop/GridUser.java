package com.example.myshop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "grisUsers")
public class GridUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String uid;
    private String userName;
    private Integer usageAmount;
    private String planType;
    private Integer balance;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getUid(){return uid;}
    public void setUid(String uid){this.uid = uid;}

    public String getUserName(){return userName;}
    public void setUseName(String userName){this.userName = userName;}

    public Integer getUsageAmount(){return usageAmount;}
    public void setUsageAmount(Integer usageAmount){this.usageAmount = usageAmount;}

    public String getPlanType(){return planType;}
    public void setPlanType(String planType){this.planType = planType;}

    public Integer getBalance(){return balance;}
    public void setBalance(Integer balance){this.balance = balance;}

    
}
