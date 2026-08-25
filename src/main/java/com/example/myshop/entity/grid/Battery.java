package com.example.myshop.entity.grid;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "batterys")
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String batteryId;
    private double soc;
    private double temp;
    private String status;
    private Boolean isCharging;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getBatteryId(){return batteryId;}
    public void setBatteryId(String batteryId){this.batteryId = batteryId;}

    public double getSoc(){return soc;}
    public void setSoc(double soc){this.soc = soc;}

    public double getTemp(){return temp;}
    public void setTemp(double temp){this.temp = temp;}

    public String getStatus(){return status;}
    public void setStatus(String status){this.status = status;}

    public Boolean getIsCharging(){return isCharging;}
    public void setIsCharging(Boolean isCharging){this.isCharging = isCharging;}


}
