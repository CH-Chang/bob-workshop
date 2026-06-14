package com.payment.model;

/**
 * 交易狀態
 */
public enum TransactionStatus {
    PENDING,     // 處理中
    APPROVED,    // 已核准
    DECLINED,    // 已拒絕
    CANCELLED,   // 已取消
    REFUNDED     // 已退款
}

// Made with Bob
