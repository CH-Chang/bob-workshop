package com.metro.repository;

import com.metro.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 回饋資料存取層
 * 
 * 提供回饋資料的 CRUD 操作
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
    /**
     * 根據車站 ID 查詢回饋
     * 
     * @param stationId 車站 ID
     * @return 該車站的所有回饋
     */
    List<Feedback> findByStationId(Long stationId);
    
    /**
     * 根據車站 ID 查詢回饋（依建立時間降序排列）
     * 
     * @param stationId 車站 ID
     * @return 該車站的所有回饋（最新的在前）
     */
    List<Feedback> findByStationIdOrderByCreatedAtDesc(Long stationId);
    
    /**
     * 根據類別查詢回饋
     * 
     * @param category 類別
     * @return 該類別的所有回饋
     */
    List<Feedback> findByCategory(String category);
    
    /**
     * 根據狀態查詢回饋
     * 
     * @param status 狀態
     * @return 該狀態的所有回饋
     */
    List<Feedback> findByStatus(String status);
    
    /**
     * 統計車站的回饋數量
     * 
     * @param stationId 車站 ID
     * @return 回饋數量
     */
    Long countByStationId(Long stationId);
    
    /**
     * 查詢評分大於等於指定值的回饋
     * 
     * @param rating 評分
     * @return 符合條件的回饋
     */
    List<Feedback> findByRatingGreaterThanEqual(Integer rating);
    
    /**
     * 查詢所有回饋（依建立時間降序排列）
     * 
     * @return 所有回饋（最新的在前）
     */
    List<Feedback> findAllByOrderByCreatedAtDesc();
}

// Made with Bob