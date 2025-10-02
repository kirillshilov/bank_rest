package com.example.bankcards.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilResponse {
    private Integer errorCode;
    private String message;
}
