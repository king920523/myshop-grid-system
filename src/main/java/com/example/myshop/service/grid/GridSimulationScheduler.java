package com.example.myshop.service.grid;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.myshop.entity.grid.Battery;
import com.example.myshop.repository.grid.BatteryRepository;

// @Component
public class GridSimulationScheduler {
    
    @Autowired
    private BatteryRepository batteryRepository;

    @Scheduled(fixedRate = 1000 * 5)
    public void simulateBatteryDataChanges(){

        List<Battery> allBatteries = batteryRepository.findAll();

        System.out.println("⏰ [物聯網排程偵測] 啟動每 5 秒電池數據即時更新...");

        for(Battery battery : allBatteries){

            if (battery.getSoc() > 0) {
                battery.setSoc(battery.getSoc() - 1);
            } else {
                battery.setSoc(100);
            }

            double currentTemp = battery.getTemp();

            double tempChange = (Math.random() - 0.5);
            battery.setTemp(Math.round((currentTemp + tempChange) * 10) / 10.0);

        }

        batteryRepository.saveAll(allBatteries);

        System.out.println("✅ [物聯網排程偵測] 全體電池 SOC 電量與溫度更新成功！");
    }


}
