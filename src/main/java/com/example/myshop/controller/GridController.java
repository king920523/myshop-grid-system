package com.example.myshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myshop.Battery;
import com.example.myshop.CarbonAcc;
import com.example.myshop.Charger;
import com.example.myshop.GridAdmin;
import com.example.myshop.GridUser;
import com.example.myshop.PowerPlant;
import com.example.myshop.PowerPlantDTO;
import com.example.myshop.service.GridService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
public class GridController {
    @Autowired
    private GridService gridService;

    @PostMapping("/powerplant/add")
    private String addPowerPlant(
        @Valid @RequestBody PowerPlant powerPlant
    ){
        return gridService.saveDynamicPowerPlant(powerPlant);
    }

    @GetMapping("/powerplant/all")
    private List<PowerPlant> getAllPowerPlants(){
        return gridService.gerAllPowerPlants();
    }

    @GetMapping("/powerplant/find/{id}")
    private PowerPlant findPowerPlantById(
        @PathVariable Long id
    ){
        return gridService.findPowerPlant(id);
    }

    @DeleteMapping("/powerplant/remove/{id}")
    private String deletePowerPlantById(
        @PathVariable Long id
    ){
        gridService.deletePowerPlant(id);

        return "已刪除電力來源";
    }

    @PostMapping("/battery/add")
    public String addBattery(
        @Valid @RequestBody Battery battery
    ){
        return gridService.saveDynamicBattery(battery);
    }

    @GetMapping("/battery/all")
    public List<Battery> getAllBatteries(){
        return gridService.getAllBatteries();
    }

    @GetMapping("/battery/find/{id}")
    public Battery findBatteryById(
        @PathVariable Long id
    ){
        return gridService.findBattery(id);
    }

    @DeleteMapping("/battery/remove/{id}")
    public String deleteBattery(
        @PathVariable Long id
    ){
        gridService.deleteBattery(id);

        return "已刪除電池狀態";
    }

    @PostMapping("/griduser/add")
    public String addGridUser(
        @Valid @RequestBody GridUser gridUser
    ){
        return gridService.saveDynamicGridUser(gridUser);
    }

    @GetMapping("/griduser/all")
    public List<GridUser> getAllGridUsers(){
        return gridService.getAllGridUsers();
    }

    @GetMapping("/griduser/find/{id}")
    public GridUser finGridUserById(
        @PathVariable Long id
    ){
        return gridService.findGridUser(id);
    }

    @DeleteMapping("/griduser/remove/{id}")
    public String deleteGridUser(
        @PathVariable Long id
    ){
        gridService.deleteGridUser(id);

        return "已刪除用戶狀態";
    }

    @PostMapping("/charger/add")
    public String addCharger(
        @Valid @RequestBody Charger charger
    ){
        return gridService.saveDynamicCharger(charger);
    }

    @GetMapping("/charger/all")
    public List<Charger> getAllChargers(){
        return gridService.getAllChargers();
    }

    @GetMapping("/charger/find/{id}")
    public Charger findChargerById(
        @PathVariable Long id
    ){
        return gridService.findCharger(id);
    }

    @DeleteMapping("/charger/remove/{id}")
    public String deleteCharger(
        @PathVariable Long id
    ){
        gridService.deleteCharger(id);

        return "已刪除充電站點";
    }

    @PostMapping("/carbonacc/add")
    public String addCarbonAcc(
        @Valid @RequestBody CarbonAcc carbonAcc
    ){
        return gridService.saveDynamicCarbonAcc(carbonAcc);
    }

    @GetMapping("/carbonacc/all")
    public List<CarbonAcc> getAllCarbonAccs(){
        return gridService.getAllCarbonAccs();
    }

    @GetMapping("/carbonacc/find/{id}")
    public CarbonAcc findCarbonAccById(
        @PathVariable Long id
    ){
        return gridService.findCarbonAcc(id);
    }
    @DeleteMapping("/carbonacc/remoce/{id}")
    public String deleteCarbonAcc(
        @PathVariable Long id
    ){  
        gridService.deleteCarbonAcc(id);

        return "已刪除企業資訊";
    }

    @PostMapping("/charger/add/at/{powerplantId}")
    public String addCharger(
        @Valid @RequestBody Charger charger,
        @PathVariable Long powerplantId
    ){
        return gridService.saveChargerWithPlant(charger, powerplantId);
    }


    @PostMapping("/auth/login")
    public String login(
        @RequestBody GridAdmin loginRequest
    ){
        return gridService.loginAndGetToken(loginRequest.getUsername(),loginRequest.getPassword());
    }

    @PostMapping("/auth/register")
    public String register(
        @RequestBody GridAdmin newAdmin
    ){
        return gridService.saveDynamicGridAdmin(newAdmin);
    }

    @GetMapping("/powerplant/find-with-chargers/{plantId}")
    public PowerPlantDTO getPlantDetail(
        @PathVariable Long plantId
    ){
        return gridService.getPowerPlantWithChargers(plantId);
    }


}
