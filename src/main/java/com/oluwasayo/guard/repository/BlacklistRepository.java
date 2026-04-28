package com.oluwasayo.guard.repository;

import com.oluwasayo.guard.model.BlacklistedMerchants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<BlacklistedMerchants, Long> {
    public boolean existsByMerchantId(String merchantId);
}
