package com.example.myshop.service.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.myshop.entity.grid.Charger;
import com.example.myshop.entity.grid.PowerPlant;
import com.example.myshop.entity.grid.PowerPlantDTO;
import com.example.myshop.repository.grid.PowerPlantRepository;

@ExtendWith(MockitoExtension.class)
class GridServiceTest {

    @Mock
    private PowerPlantRepository powerPlantRepository;

    @InjectMocks
    private GridService gridService;

    @Test
    void getPowerPlantWithChargers_whenPlantDoesNotExist_returnsNull() {
        Long plantId = 1L;
        when(powerPlantRepository.findById(plantId)).thenReturn(Optional.empty());

        PowerPlantDTO result = gridService.getPowerPlantWithChargers(plantId);

        assertNull(result);
        verify(powerPlantRepository).findById(plantId);
        verifyNoMoreInteractions(powerPlantRepository);
    }

    @Test
    void getPowerPlantWithChargers_whenPlantExists_mapsPlantAndChargerIds() {
        Long plantId = 1L;
        PowerPlant plant = new PowerPlant();
        plant.setId(plantId);
        plant.setPlantName("南部太陽能電廠");
        plant.setPlantType("太陽能");
        plant.setIsOk(true);

        Charger firstCharger = new Charger();
        firstCharger.setStationId("ST-001");
        Charger secondCharger = new Charger();
        secondCharger.setStationId("ST-002");
        plant.setCharger(List.of(firstCharger, secondCharger));

        when(powerPlantRepository.findById(plantId)).thenReturn(Optional.of(plant));

        PowerPlantDTO result = gridService.getPowerPlantWithChargers(plantId);

        assertEquals(plantId, result.getID());
        assertEquals("南部太陽能電廠", result.getPlantName());
        assertEquals("太陽能", result.getPlantType());
        assertEquals(true, result.getIsOk());
        assertEquals(List.of("ST-001", "ST-002"), result.getChargerStationIds());
        verify(powerPlantRepository).findById(plantId);
        verifyNoMoreInteractions(powerPlantRepository);
    }

    @Test
    void getPowerPlantWithChargers_whenChargersAreNull_returnsEmptyChargerIds() {
        Long plantId = 1L;
        PowerPlant plant = new PowerPlant();
        plant.setId(plantId);
        plant.setCharger(null);
        when(powerPlantRepository.findById(plantId)).thenReturn(Optional.of(plant));

        PowerPlantDTO result = gridService.getPowerPlantWithChargers(plantId);

        assertEquals(List.of(), result.getChargerStationIds());
        verify(powerPlantRepository).findById(plantId);
        verifyNoMoreInteractions(powerPlantRepository);
    }
}