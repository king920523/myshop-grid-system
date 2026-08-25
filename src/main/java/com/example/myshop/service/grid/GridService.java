package com.example.myshop.service.grid;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.myshop.entity.grid.Battery;
import com.example.myshop.entity.grid.CarbonAcc;
import com.example.myshop.entity.grid.Charger;
import com.example.myshop.entity.grid.GridAdmin;
import com.example.myshop.entity.grid.GridUser;
import com.example.myshop.entity.grid.PowerPlant;
import com.example.myshop.entity.grid.PowerPlantDTO;
import com.example.myshop.repository.grid.BatteryRepository;
import com.example.myshop.repository.grid.CarbonAccRepository;
import com.example.myshop.repository.grid.ChargerRepository;
import com.example.myshop.repository.grid.GridAdminRepository;
import com.example.myshop.repository.grid.GridUserRepository;
import com.example.myshop.repository.grid.PowerPlantRepository;
import com.example.myshop.service.JwtService;

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

    private final org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder = 
        new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();

    public String saveDynamicGridAdmin(GridAdmin admin){
        GridAdmin existingAdmin = gridAdminRepository.findByUsername(admin.getUsername());
        if (existingAdmin != null) {
            return "❌ 註冊失敗：此帳號已存在，請換一個帳號！";
        }

        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);

        gridAdminRepository.save(admin);
        return "🎉 管理員帳號註冊成功！(密碼已啟動 BCrypt 金融級加密防護)";
    }     

    public String loginAndGetToken(String username, String password){
        GridAdmin admin = gridAdminRepository.findByUsername(username);

        if (admin == null || !passwordEncoder.matches(password, admin.getPassword())) {
            return "❌ 登入失敗：帳號或密碼錯誤！";            
        }

        String token = jwtService.generateToken(username);
        return "✅ 登入成功！請收下您的安全憑證 Token:\nBearer " + token;
    }

    public Page<Charger> getChargersByPage(int page, int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return chargerRepository.findAll(pageable);
    }
    

}
