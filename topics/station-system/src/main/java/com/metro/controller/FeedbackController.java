package com.metro.controller;

import com.metro.dto.FeedbackStatisticsDto;
import com.metro.model.Feedback;
import com.metro.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 回饋 REST API 控制器
 * 
 * 提供回饋管理的 RESTful API
 */
@RestController
@RequestMapping("/api/feedbacks")
@CrossOrigin(origins = "*")
public class FeedbackController {
    
    private final FeedbackService feedbackService;
    
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }
    
    /**
     * 建立回饋
     * 
     * @param feedback 回饋資料
     * @return 建立的回饋
     */
    @PostMapping
    public ResponseEntity<?> createFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback createdFeedback = feedbackService.createFeedback(feedback);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdFeedback);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * 取得所有回饋
     * 
     * @param status 選填，依狀態篩選
     * @return 回饋列表
     */
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks(
            @RequestParam(required = false) String status) {
        try {
            List<Feedback> feedbacks;
            if (status != null && !status.trim().isEmpty()) {
                feedbacks = feedbackService.getFeedbacksByStatus(status);
            } else {
                feedbacks = feedbackService.getAllFeedbacks();
            }
            return ResponseEntity.ok(feedbacks);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * 根據 ID 取得回饋
     * 
     * @param id 回饋 ID
     * @return 回饋資料
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFeedbackById(@PathVariable Long id) {
        try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            return ResponseEntity.ok(feedback);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * 根據車站 ID 取得回饋
     * 
     * @param stationId 車站 ID
     * @return 該車站的所有回饋
     */
    @GetMapping("/station/{stationId}")
    public ResponseEntity<?> getFeedbacksByStation(@PathVariable Long stationId) {
        try {
            List<Feedback> feedbacks = feedbackService.getFeedbacksByStation(stationId);
            return ResponseEntity.ok(feedbacks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * 更新回饋狀態
     * 
     * @param id 回饋 ID
     * @param body 包含 status 欄位的 JSON
     * @return 更新後的回饋
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateFeedbackStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        try {
            String status = body.get("status");
            if (status == null || status.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "狀態不可為空"));
            }
            
            Feedback updatedFeedback = feedbackService.updateFeedbackStatus(id, status);
            return ResponseEntity.ok(updatedFeedback);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * 刪除回饋
     * 
     * @param id 回饋 ID
     * @return 無內容回應
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * 取得車站統計資訊
     * 
     * @param stationId 車站 ID
     * @return 統計資訊
     */
    @GetMapping("/statistics/station/{stationId}")
    public ResponseEntity<?> getStationStatistics(@PathVariable Long stationId) {
        try {
            FeedbackStatisticsDto statistics = feedbackService.getStationStatistics(stationId);
            return ResponseEntity.ok(statistics);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * 取得全域統計資訊
     * 
     * @return 全域統計資訊
     */
    @GetMapping("/statistics/global")
    public ResponseEntity<FeedbackStatisticsDto> getGlobalStatistics() {
        FeedbackStatisticsDto statistics = feedbackService.getGlobalStatistics();
        return ResponseEntity.ok(statistics);
    }
}

// Made with Bob