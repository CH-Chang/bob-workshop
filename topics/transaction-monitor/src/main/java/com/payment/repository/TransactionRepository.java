package com.payment.repository;

import com.payment.model.Transaction;
import com.payment.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易資料存取介面
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    /**
     * 根據卡片ID查詢交易
     */
    List<Transaction> findByCardId(Long cardId);
    
    /**
     * 根據商店ID查詢交易
     */
    List<Transaction> findByMerchantId(Long merchantId);
    
    /**
     * 根據狀態查詢交易
     */
    List<Transaction> findByStatus(TransactionStatus status);
    
    /**
     * 查詢指定時間後的交易
     */
    List<Transaction> findByTransactionTimeAfter(LocalDateTime since);
    
    /**
     * 查詢指定卡片在指定時間後的交易
     */
    List<Transaction> findByCardIdAndTransactionTimeAfter(Long cardId, LocalDateTime since);
    
    /**
     * 計算指定卡片在指定時間後的交易數量
     */
    long countByCardIdAndTransactionTimeAfter(Long cardId, LocalDateTime since);
    
    /**
     * 查詢高額交易（金額大於指定值）
     */
    @Query("SELECT t FROM Transaction t WHERE t.amount > :amount")
    List<Transaction> findHighAmountTransactions(@Param("amount") BigDecimal amount);
    
    /**
     * 查詢指定時間範圍內的交易
     */
    @Query("SELECT t FROM Transaction t WHERE t.transactionTime BETWEEN :start AND :end")
    List<Transaction> findByTransactionTimeBetween(
        @Param("start") LocalDateTime start, 
        @Param("end") LocalDateTime end
    );
    
    /**
     * 查詢可能重複的交易（相同卡片、相同商店、相同金額、時間接近）
     */
    @Query("SELECT t FROM Transaction t WHERE t.card.id = :cardId " +
           "AND t.merchant.id = :merchantId " +
           "AND t.amount = :amount " +
           "AND t.transactionTime BETWEEN :start AND :end")
    List<Transaction> findPotentialDuplicates(
        @Param("cardId") Long cardId,
        @Param("merchantId") Long merchantId,
        @Param("amount") BigDecimal amount,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}

// Made with Bob
