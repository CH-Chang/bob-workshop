package com.payment.repository;

import com.payment.model.Card;
import com.payment.model.CardStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 信用卡資料存取介面
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
    /**
     * 根據卡號查詢
     */
    Optional<Card> findByCardNumber(String cardNumber);
    
    /**
     * 根據狀態查詢
     */
    List<Card> findByStatus(CardStatus status);
    
    /**
     * 根據持卡人姓名查詢
     */
    List<Card> findByCardholderNameContaining(String name);
}

// Made with Bob
