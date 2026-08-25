package com.example.myshop;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chargers")
public class Charger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stationId;
    private String type;
    private Integer kw;
    private double pricePerKw;
    private Boolean isBusy;

    @ManyToOne
    @JoinColumn(name = "power_plant_id")
    private PowerPlant powerPlant;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getStationId(){return stationId;}
    public void setStationId(String stationId){this.stationId = stationId;}

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public Integer getKw(){return kw;}
    public void setKw(Integer kw){this.kw = kw;}

    public double getPricePerKw(){return pricePerKw;}
    public void setPricePerKw(double pricePerKw){this.pricePerKw = pricePerKw;}

    public Boolean getIsBusy(){return isBusy;}
    public void setIsBusy(Boolean isBusy){this.isBusy = isBusy;}

    public PowerPlant getPowerPlant(){return powerPlant;}
    public void setPowerPlant(PowerPlant powerPlant){this.powerPlant = powerPlant;}
    
}
