package com.oluwasayo.guard.dto;


import lombok.Data;

@Data
public class IngestionRequestDto {
    private String cardNo;
    private Long amount;
    private String currency;
    private String merchantId;
    private String senderId;
    private String ipAddress;
}
