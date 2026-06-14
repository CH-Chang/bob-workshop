package com.metro.controller;

import com.metro.model.Station;
import com.metro.service.StationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 車站 REST API 控制器
 * 
 * 提供車站管理的 RESTful API
 */
@RestController
@RequestMapping("/api/stations")
@CrossOrigin(origins = "*")
public class StationController {
    
    private final StationService stationService;
    
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }
    
    /**
     * 取得所有車站
     * 
     * @return 所有車站列表
     */
    @GetMapping
    public ResponseEntity<List<Station>> getAllStations() {
        List<Station> stations = stationService.getAllStations();
        return ResponseEntity.ok(stations);
    }
    
    /**
     * 根據 ID 取得車站
     * 
     * @param id 車站 ID
     * @return 車站資料
     */
    @GetMapping("/{id}")
    public ResponseEntity<Station> getStationById(@PathVariable Long id) {
        try {
            Station station = stationService.getStationById(id);
            return ResponseEntity.ok(station);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * 建立新車站
     * 
     * @param station 車站資料
     * @return 建立的車站
     */
    @PostMapping
    public ResponseEntity<?> createStation(@RequestBody Station station) {
        try {
            Station createdStation = stationService.createStation(station);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * 更新車站資料
     * 
     * @param id 車站 ID
     * @param station 更新的車站資料
     * @return 更新後的車站
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStation(@PathVariable Long id, @RequestBody Station station) {
        try {
            Station updatedStation = stationService.updateStation(id, station);
            return ResponseEntity.ok(updatedStation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * 刪除車站
     * 
     * @param id 車站 ID
     * @return 無內容回應
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable Long id) {
        try {
            stationService.deleteStation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * 根據路線取得車站
     * 
     * @param line 路線名稱
     * @return 該路線的所有車站
     */
    @GetMapping("/line/{line}")
    public ResponseEntity<List<Station>> getStationsByLine(@PathVariable String line) {
        List<Station> stations = stationService.getStationsByLine(line);
        return ResponseEntity.ok(stations);
    }
}

// Made with Bob
