package com.peigo.msvcaccounts.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private String operationNumber;
    private BigDecimal balanceAccountOrigin;
    private BigDecimal balanceAccountDestination;
}
