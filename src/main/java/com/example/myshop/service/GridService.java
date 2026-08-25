package com.example.myshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myshop.Battery;
import com.example.myshop.CarbonAcc;
import com.example.myshop.Charger;
import com.example.myshop.GridAdmin;
import com.example.myshop.GridUser;
import com.example.myshop.PowerPlant;
import com.example.myshop.PowerPlantDTO;
import com.example.myshop.repository.BatteryRepository;
import com.example.myshop.repository.CarbonAccRepository;
import com.example.myshop.repository.ChargerRepository;
import com.example.myshop.repository.GridAdminRepository;
import com.example.myshop.repository.GridUserRepository;
import com.example.myshop.repository.PowerPlantRepository;

@Service
public class GridService {
    @Autowired
    private BatteryRepository batteryRepository;
    @Autowired
    private CarbonAccRepository carbonAccRepository;
    @Autowired
    private ChargerRepository chargerRepository;
    @Autowired
    private GridUserRepository gridUserRepository;
    @Autowired
    private PowerPlantRepository powerplantRepository;
    @Autowired
    private GridAdminRepository gridAdminRepository;
    @Autowired
    private JwtService jwtService;


    public String saveDynamicPowerPlant(PowerPlant powerPlant){
        powerplantRepository.save(powerPlant);

        return "以新增電力來源";
    }

    public List<PowerPlant> gerAllPowerPlants(){
        return powerplantRepository.findAll();
    }

    public PowerPlant findPowerPlant(Long id){
        return powerplantRepository.findById(id).orElse(null);
    }

    public void deletePowerPlant(Long id){
        powerplantRepository.deleteById(id);
    }

    public String saveDynamicBattery(Battery battery){
        batteryRepository.save(battery);

        return "以新增電池狀態";
    }

    public List<Battery> getAllBatteries(){
        return batteryRepository.findAll();
    }

    public Battery findBattery(Long id){
        return batteryRepository.findById(id).orElse(null);
    }

    public void deleteBattery(Long id){
        batteryRepository.deleteById(id);
    }

    public String saveDynamicGridUser(GridUser gridUser){
        gridUserRepository.save(gridUser);

        return "以新增用電客戶";
    }

    public List<GridUser> getAllGridUsers(){
        return gridUserRepository.findAll();
    }

    public GridUser findGridUser(Long id){
        return gridUserRepository.findById(id).orElse(null);
    }

    public void deleteGridUser(Long id){
        gridUserRepository.deleteById(id);
    }

    public String saveDynamicCharger(Charger charger){
        chargerRepository.save(charger);

        return "以新增充電站";
    }

    public List<Charger> getAllChargers(){
        return chargerRepository.findAllChargersWithPlant();
    }

    public Charger findCharger(Long id){
        return chargerRepository.findById(id).orElse(null);
    }

    public void deleteCharger(Long id){
        chargerRepository.deleteById(id);
    }

    public String saveDynamicCarbonAcc(CarbonAcc carbonAcc){
        carbonAccRepository.save(carbonAcc);

        return "以新增企業炭權額度";
    }

    public List<CarbonAcc> getAllCarbonAccs(){
        return carbonAccRepository.findAll();
    }

    public CarbonAcc findCarbonAcc(Long id){
        return carbonAccRepository.findById(id).orElse(null);
    }

    public void deleteCarbonAcc(Long id){
        carbonAccRepository.deleteById(id);
    }

    public String saveChargerWithPlant(Charger charger,Long powerplantId){
        PowerPlant plant = powerplantRepository.findById(powerplantId).orElse(null);

        if (plant == null) {
            return "❌ 錯誤：找不到指定的電力來源發電廠，無法新增充電站！";
        }

        charger.setPowerPlant(plant);

        chargerRepository.save(charger);

        return "✅ 已成功新增充電站，並綁定電力來源：" + plant.getPlantName();
    }

    public String loginAndGetToken(String username, String password){

        GridAdmin admin = gridAdminRepository.findByUsername(username);

        if (admin == null || !admin.getPassword().equals(password)) {

            return "❌ 登入失敗：帳號或密碼錯誤！";

        }

        String token = jwtService.generateToken(username);
        return "✅ 登入成功！請收下您的安全憑證 Token:\nBearer " + token;

    }

    public String saveDynamicGridAdmin(GridAdmin admin){

        gridAdminRepository.save(admin);

        return "管理員帳號註冊成功";
    }

    public PowerPlantDTO getPowerPlantWithChargers(Long plantId){

        PowerPlant plant =powerplantRepository.findById(plantId).orElse(null);
        if (plant == null) { return null; }  
        
        PowerPlantDTO dto = new PowerPlantDTO();
        dto.setId(plant.getId());
        dto.setPlantName(plant.getPlantName());
        dto.setPlantType(plant.getPlantType());
        dto.setIsOk(plant.getIsOk());

        List<String> ids = new java.util.ArrayList<>();
        if (plant.getChargers() != null) {
            for (Charger c : plant.getChargers()) {
                ids.add(c.getStationId()); // 提取充電站代號
            }
        }
        dto.setChargerStationIds(ids);

        return dto;
    }



}
