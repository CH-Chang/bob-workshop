package com.metro.service;

import com.metro.model.Station;
import com.metro.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 車站服務層
 * 
 * 處理車站相關的業務邏輯
 */
@Service
@Transactional
public class StationService {
    
    private final StationRepository stationRepository;
    
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }
    
    /**
     * 取得所有車站
     * 
     * @return 所有車站列表
     */
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }
    
    /**
     * 根據 ID 取得車站
     * 
     * @param id 車站 ID
     * @return 車站資料
     * @throws RuntimeException 如果車站不存在
     */
    @SuppressWarnings("null")
    public Station getStationById(Long id) {
        return stationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("車站不存在: ID = " + id));
    }
    
    /**
     * 建立新車站
     * 
     * @param station 車站資料
     * @return 建立的車站
     */
    public Station createStation(Station station) {
        // 檢查車站代碼是否已存在
        if (stationRepository.findByCode(station.getCode()).isPresent()) {
            throw new RuntimeException("車站代碼已存在: " + station.getCode());
        }
        return stationRepository.save(station);
    }
    
    /**
     * 更新車站資料
     * 
     * @param id 車站 ID
     * @param station 更新的車站資料
     * @return 更新後的車站
     */
    public Station updateStation(Long id, Station station) {
        Station existingStation = getStationById(id);
        
        // 如果車站代碼有變更，檢查新代碼是否已被使用
        if (!existingStation.getCode().equals(station.getCode())) {
            if (stationRepository.findByCode(station.getCode()).isPresent()) {
                throw new RuntimeException("車站代碼已存在: " + station.getCode());
            }
        }
        
        existingStation.setCode(station.getCode());
        existingStation.setName(station.getName());
        existingStation.setLine(station.getLine());
        
        return stationRepository.save(existingStation);
    }
    
    /**
     * 刪除車站
     * 
     * @param id 車站 ID
     */
    @SuppressWarnings("null")
    public void deleteStation(Long id) {
        if (!stationRepository.existsById(id)) {
            throw new RuntimeException("車站不存在: ID = " + id);
        }
        stationRepository.deleteById(id);
    }
    
    /**
     * 根據路線取得車站
     * 
     * @param line 路線名稱
     * @return 該路線的所有車站
     */
    public List<Station> getStationsByLine(String line) {
        return stationRepository.findByLine(line);
    }
}

// Made with Bob
