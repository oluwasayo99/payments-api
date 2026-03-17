package com.oluwasayo.guard.repository;

import com.oluwasayo.guard.model.BlacklistedMerchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistedMerchant, Long> {
    public boolean existsByMerchantId(String merchantId);
}
