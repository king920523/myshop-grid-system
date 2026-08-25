package com.example.myshop.entity.grid;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "powerPlants")
public class PowerPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "廠名不能空白")
    private String plantName;

    @NotBlank(message = "請填寫類型")
    private String plantType;

    @NotNull
    @Min(value = 200, message = "當前功率過低")
    private double output;

    private double capacity;

    private Boolean isOk;

    @jakarta.persistence.OneToMany(mappedBy = "powerPlant")
    private List<Charger> chargers;

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getPlantName(){return plantName;}
    public void setPlantName(String plantName){this.plantName = plantName;}

    public String getPlantType(){return plantType;}
    public void setPlantType(String plantType){this.plantType = plantType;}

    public double getOutput(){return output;}
    public void setOutput(double output){this.output = output;}

    public double getCapacity(){return capacity;}
    public void setCapacity(double capacity){this.capacity = capacity;}

    public Boolean getIsOk(){return isOk;}
    public void setIsOk(Boolean isOk){this.isOk = isOk;}

    public List<Charger> getChargers(){return chargers;}
    public void setCharger(List<Charger> chargers){this.chargers = chargers;}

    
}
