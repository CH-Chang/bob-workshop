package com.metro.service;

import com.metro.dto.FeedbackStatisticsDto;
import com.metro.model.Feedback;
import com.metro.model.Station;
import com.metro.repository.FeedbackRepository;
import com.metro.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 回饋服務層
 * 
 * 處理回饋相關的業務邏輯
 */
@Service
@Transactional
public class FeedbackService {
    
    private final FeedbackRepository feedbackRepository;
    private final StationRepository stationRepository;
    
    // 允許的類別
    private static final List<String> VALID_CATEGORIES = List.of("清潔", "服務", "設施", "安全", "其他");
    
    // 允許的狀態
    private static final List<String> VALID_STATUSES = List.of("待處理", "處理中", "已完成");
    
    public FeedbackService(FeedbackRepository feedbackRepository, StationRepository stationRepository) {
        this.feedbackRepository = feedbackRepository;
        this.stationRepository = stationRepository;
    }
    
    /**
     * 建立回饋
     * 
     * @param feedback 回饋資料
     * @return 建立的回饋
     * @throws RuntimeException 如果驗證失敗
     */
    public Feedback createFeedback(Feedback feedback) {
        // 驗證車站存在
        if (feedback.getStation() == null || feedback.getStation().getId() == null) {
            throw new RuntimeException("車站 ID 不可為空");
        }
        
        @SuppressWarnings("null")
        Station station = stationRepository.findById(feedback.getStation().getId())
                .orElseThrow(() -> new RuntimeException("車站不存在: ID = " + feedback.getStation().getId()));
        
        feedback.setStation(station);
        
        // 驗證評分範圍
        if (feedback.getRating() == null || feedback.getRating() < 1 || feedback.getRating() > 5) {
            throw new RuntimeException("評分必須在 1-5 之間");
        }
        
        // 驗證類別
        if (feedback.getCategory() == null || !VALID_CATEGORIES.contains(feedback.getCategory())) {
            throw new RuntimeException("類別必須為：清潔、服務、設施、安全或其他");
        }
        
        // 驗證意見內容
        if (feedback.getComment() == null || feedback.getComment().trim().isEmpty()) {
            throw new RuntimeException("意見內容不可為空");
        }
        
        if (feedback.getComment().length() > 1000) {
            throw new RuntimeException("意見內容不可超過 1000 字");
        }
        
        // 驗證 Email 格式（如果有提供）
        if (feedback.getContactEmail() != null && !feedback.getContactEmail().trim().isEmpty()) {
            if (!isValidEmail(feedback.getContactEmail())) {
                throw new RuntimeException("Email 格式不正確");
            }
        }
        
        return feedbackRepository.save(feedback);
    }
    
    /**
     * 取得所有回饋
     * 
     * @return 所有回饋列表（依建立時間降序）
     */
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAllByOrderByCreatedAtDesc();
    }
    
    /**
     * 根據 ID 取得回饋
     * 
     * @param id 回饋 ID
     * @return 回饋資料
     * @throws RuntimeException 如果回饋不存在
     */
    @SuppressWarnings("null")
    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("回饋不存在: ID = " + id));
    }
    
    /**
     * 根據車站 ID 取得回饋
     * 
     * @param stationId 車站 ID
     * @return 該車站的所有回饋
     */
    public List<Feedback> getFeedbacksByStation(Long stationId) {
        // 驗證車站存在
        @SuppressWarnings("null")
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("車站不存在: ID = " + stationId));
        
        return feedbackRepository.findByStationIdOrderByCreatedAtDesc(stationId);
    }
    
    /**
     * 根據狀態取得回饋
     * 
     * @param status 狀態
     * @return 該狀態的所有回饋
     */
    public List<Feedback> getFeedbacksByStatus(String status) {
        if (!VALID_STATUSES.contains(status)) {
            throw new RuntimeException("狀態必須為：待處理、處理中或已完成");
        }
        return feedbackRepository.findByStatus(status);
    }
    
    /**
     * 更新回饋狀態
     * 
     * @param id 回饋 ID
     * @param status 新狀態
     * @return 更新後的回饋
     * @throws RuntimeException 如果回饋不存在或狀態無效
     */
    public Feedback updateFeedbackStatus(Long id, String status) {
        Feedback feedback = getFeedbackById(id);
        
        // 驗證狀態
        if (!VALID_STATUSES.contains(status)) {
            throw new RuntimeException("狀態必須為：待處理、處理中或已完成");
        }
        
        feedback.setStatus(status);
        return feedbackRepository.save(feedback);
    }
    
    /**
     * 刪除回饋
     * 
     * @param id 回饋 ID
     * @throws RuntimeException 如果回饋不存在
     */
    @SuppressWarnings("null")
    public void deleteFeedback(Long id) {
        if (!feedbackRepository.existsById(id)) {
            throw new RuntimeException("回饋不存在: ID = " + id);
        }
        feedbackRepository.deleteById(id);
    }
    
    /**
     * 取得車站統計資訊
     * 
     * @param stationId 車站 ID
     * @return 統計資訊
     * @throws RuntimeException 如果車站不存在
     */
    @SuppressWarnings("null")
    public FeedbackStatisticsDto getStationStatistics(Long stationId) {
        // 驗證車站存在
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new RuntimeException("車站不存在: ID = " + stationId));
        
        List<Feedback> feedbacks = feedbackRepository.findByStationId(stationId);
        
        FeedbackStatisticsDto stats = new FeedbackStatisticsDto(
                station.getId(),
                station.getName(),
                station.getCode()
        );
        
        // 總回饋數
        stats.setTotalFeedbacks((long) feedbacks.size());
        
        if (feedbacks.isEmpty()) {
            stats.setAverageRating(0.0);
            stats.setPendingCount(0L);
            stats.setProcessingCount(0L);
            stats.setCompletedCount(0L);
            stats.setCategoryDistribution(new HashMap<>());
            stats.setRatingDistribution(new HashMap<>());
            return stats;
        }
        
        // 平均評分
        double avgRating = feedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);
        stats.setAverageRating(Math.round(avgRating * 10.0) / 10.0);
        
        // 狀態統計
        stats.setPendingCount(feedbacks.stream().filter(f -> "待處理".equals(f.getStatus())).count());
        stats.setProcessingCount(feedbacks.stream().filter(f -> "處理中".equals(f.getStatus())).count());
        stats.setCompletedCount(feedbacks.stream().filter(f -> "已完成".equals(f.getStatus())).count());
        
        // 類別分布
        Map<String, Long> categoryDist = feedbacks.stream()
                .collect(Collectors.groupingBy(Feedback::getCategory, Collectors.counting()));
        stats.setCategoryDistribution(categoryDist);
        
        // 評分分布
        Map<Integer, Long> ratingDist = feedbacks.stream()
                .collect(Collectors.groupingBy(Feedback::getRating, Collectors.counting()));
        stats.setRatingDistribution(ratingDist);
        
        return stats;
    }
    
    /**
     * 取得全域統計資訊
     * 
     * @return 全域統計資訊
     */
    public FeedbackStatisticsDto getGlobalStatistics() {
        List<Feedback> allFeedbacks = feedbackRepository.findAll();
        
        FeedbackStatisticsDto stats = new FeedbackStatisticsDto();
        stats.setStationName("全部車站");
        
        // 總回饋數
        stats.setTotalFeedbacks((long) allFeedbacks.size());
        
        if (allFeedbacks.isEmpty()) {
            stats.setAverageRating(0.0);
            stats.setPendingCount(0L);
            stats.setProcessingCount(0L);
            stats.setCompletedCount(0L);
            stats.setCategoryDistribution(new HashMap<>());
            stats.setRatingDistribution(new HashMap<>());
            return stats;
        }
        
        // 平均評分
        double avgRating = allFeedbacks.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);
        stats.setAverageRating(Math.round(avgRating * 10.0) / 10.0);
        
        // 狀態統計
        stats.setPendingCount(allFeedbacks.stream().filter(f -> "待處理".equals(f.getStatus())).count());
        stats.setProcessingCount(allFeedbacks.stream().filter(f -> "處理中".equals(f.getStatus())).count());
        stats.setCompletedCount(allFeedbacks.stream().filter(f -> "已完成".equals(f.getStatus())).count());
        
        // 類別分布
        Map<String, Long> categoryDist = allFeedbacks.stream()
                .collect(Collectors.groupingBy(Feedback::getCategory, Collectors.counting()));
        stats.setCategoryDistribution(categoryDist);
        
        // 評分分布
        Map<Integer, Long> ratingDist = allFeedbacks.stream()
                .collect(Collectors.groupingBy(Feedback::getRating, Collectors.counting()));
        stats.setRatingDistribution(ratingDist);
        
        return stats;
    }
    
    /**
     * 驗證 Email 格式
     * 
     * @param email Email 地址
     * @return 是否有效
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}

// Made with Bob