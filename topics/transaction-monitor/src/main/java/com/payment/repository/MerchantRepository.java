package com.payment.repository;

import com.payment.model.Merchant;
import com.payment.model.MerchantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 特約商店資料存取介面
 */
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    
    /**
     * 根據商店代碼查詢
     */
    Optional<Merchant> findByMerchantCode(String merchantCode);
    
    /**
     * 根據類別查詢
     */
    List<Merchant> findByCategory(MerchantCategory category);
    
    /**
     * 根據商店名稱查詢
     */
    List<Merchant> findByMerchantNameContaining(String name);
}

// Made with Bob
