package com.metro.dto;

import java.util.Map;

/**
 * 回饋統計資料傳輸物件
 * 
 * 用於傳遞車站回饋的統計資訊
 */
public class FeedbackStatisticsDto {
    
    private Long stationId;
    private String stationName;
    private String stationCode;
    private Long totalFeedbacks;
    private Double averageRating;
    private Long pendingCount;
    private Long processingCount;
    private Long completedCount;
    private Map<String, Long> categoryDistribution;
    private Map<Integer, Long> ratingDistribution;
    
    // Constructors
    public FeedbackStatisticsDto() {
    }
    
    public FeedbackStatisticsDto(Long stationId, String stationName, String stationCode) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.stationCode = stationCode;
    }
    
    // Getters and Setters
    public Long getStationId() {
        return stationId;
    }
    
    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }
    
    public String getStationName() {
        return stationName;
    }
    
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    
    public String getStationCode() {
        return stationCode;
    }
    
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }
    
    public Long getTotalFeedbacks() {
        return totalFeedbacks;
    }
    
    public void setTotalFeedbacks(Long totalFeedbacks) {
        this.totalFeedbacks = totalFeedbacks;
    }
    
    public Double getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    
    public Long getPendingCount() {
        return pendingCount;
    }
    
    public void setPendingCount(Long pendingCount) {
        this.pendingCount = pendingCount;
    }
    
    public Long getProcessingCount() {
        return processingCount;
    }
    
    public void setProcessingCount(Long processingCount) {
        this.processingCount = processingCount;
    }
    
    public Long getCompletedCount() {
        return completedCount;
    }
    
    public void setCompletedCount(Long completedCount) {
        this.completedCount = completedCount;
    }
    
    public Map<String, Long> getCategoryDistribution() {
        return categoryDistribution;
    }
    
    public void setCategoryDistribution(Map<String, Long> categoryDistribution) {
        this.categoryDistribution = categoryDistribution;
    }
    
    public Map<Integer, Long> getRatingDistribution() {
        return ratingDistribution;
    }
    
    public void setRatingDistribution(Map<Integer, Long> ratingDistribution) {
        this.ratingDistribution = ratingDistribution;
    }
    
    @Override
    public String toString() {
        return "FeedbackStatisticsDto{" +
                "stationId=" + stationId +
                ", stationName='" + stationName + '\'' +
                ", stationCode='" + stationCode + '\'' +
                ", totalFeedbacks=" + totalFeedbacks +
                ", averageRating=" + averageRating +
                ", pendingCount=" + pendingCount +
                ", processingCount=" + processingCount +
                ", completedCount=" + completedCount +
                '}';
    }
}

// Made with Bob