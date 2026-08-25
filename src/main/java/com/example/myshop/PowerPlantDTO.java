package com.example.myshop;

import java.util.List;

public class PowerPlantDTO {

    private Long id;
    private String plantName;
    private String plantType;
    private Boolean isOk;
    private List<String> chargerStationIds; //  關鍵優化：我們不包裝整個 Charger 物件，只撈出充電站的「代號清單」，徹底切斷無窮迴圈！

    public Long getID(){return id;}
    public void setId(Long id){this.id = id;}

    public String getPlantName(){return plantName;}
    public void setPlantName(String plantName){this.plantName = plantName;}

    public String getPlantType(){return plantType;}
    public void setPlantType(String plantType){this.plantType = plantType;}

    public Boolean getIsOk(){return isOk;}
    public void setIsOk(Boolean isOk){this.isOk = isOk;}

    public List<String> getChargerStationIds(){return chargerStationIds;}
    public void setChargerStationIds(List<String> chargerStationIds){this.chargerStationIds = chargerStationIds;}
    
    
}
