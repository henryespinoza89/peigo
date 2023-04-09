package com.peigo.msvcaccounts.common.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String processedAt;
    private String message;
    private String details;
}
