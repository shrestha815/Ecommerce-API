package com.example.model;

import lombok.Data;

@Data
public class PaymentResponse {
	private Long paymentId;
    private Double amount;
    private String paymentMethod;
    private String status;
}
