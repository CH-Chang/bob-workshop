package com.payment.service;

import com.payment.model.Transaction;
import com.payment.model.TransactionStatus;
import com.payment.repository.TransactionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 交易服務
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    
    /**
     * 建立新交易
     */
    public Transaction createTransaction(Transaction transaction) {
        log.info("建立新交易: 卡號={}, 金額={}", 
            transaction.getCard().getMaskedCardNumber(), 
            transaction.getAmount());
        return transactionRepository.save(transaction);
    }
    
    /**
     * 查詢所有交易
     */
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    /**
     * 根據ID查詢交易
     */
    @Transactional(readOnly = true)
    public Transaction getTransactionById(@NonNull Long id) {
        return transactionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("交易不存在: " + id));
    }
    
    /**
     * 查詢指定卡片的交易
     */
    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByCardId(@NonNull Long cardId) {
        return transactionRepository.findByCardId(cardId);
    }
    
    /**
     * 查詢最近的交易
     */
    @Transactional(readOnly = true)
    public List<Transaction> getRecentTransactions(int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return transactionRepository.findByTransactionTimeAfter(since);
    }
    
    /**
     * 查詢高額交易
     */
    @Transactional(readOnly = true)
    public List<Transaction> getHighAmountTransactions(BigDecimal threshold) {
        return transactionRepository.findHighAmountTransactions(threshold);
    }
    
    /**
     * 計算指定卡片在指定時間內的交易數量
     */
    @Transactional(readOnly = true)
    public long countRecentTransactions(@NonNull Long cardId, int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return transactionRepository.countByCardIdAndTransactionTimeAfter(cardId, since);
    }
    
    /**
     * 檢查是否有重複交易
     */
    @Transactional(readOnly = true)
    public List<Transaction> findPotentialDuplicates(
            @NonNull Long cardId,
            @NonNull Long merchantId,
            @NonNull BigDecimal amount,
            int minutes) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusMinutes(minutes);
        return transactionRepository.findPotentialDuplicates(
            cardId, merchantId, amount, start, now);
    }
    
    /**
     * 更新交易狀態
     */
    public Transaction updateTransactionStatus(@NonNull Long id, @NonNull TransactionStatus status) {
        Transaction transaction = getTransactionById(id);
        transaction.setStatus(status);
        log.info("更新交易狀態: ID={}, 新狀態={}", id, status);
        return transactionRepository.save(transaction);
    }
}

// Made with Bob
