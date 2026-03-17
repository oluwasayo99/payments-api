package com.oluwasayo.guard.service;


import com.oluwasayo.guard.repository.BlacklistRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BlacklistService {
    private final BlacklistRepository repository;

    public BlacklistService(BlacklistRepository repository) {
        this.repository = repository;
    }

    @Cacheable(value = "blacklistCache", key = "#merchantId")
    public boolean isBlacklisted(String merchantId) {
        return repository.existsByMerchantId(merchantId);
    }
}
