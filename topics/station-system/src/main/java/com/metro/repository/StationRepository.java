package com.metro.repository;

import com.metro.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 車站資料存取層
 * 
 * 提供車站資料的 CRUD 操作
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    
    /**
     * 根據車站代碼查詢車站
     * 
     * @param code 車站代碼 (例如: R3, O5)
     * @return 車站資料
     */
    Optional<Station> findByCode(String code);
    
    /**
     * 根據路線查詢所有車站
     * 
     * @param line 路線名稱 (紅線/橘線)
     * @return 該路線的所有車站
     */
    List<Station> findByLine(String line);
}

// Made with Bob
