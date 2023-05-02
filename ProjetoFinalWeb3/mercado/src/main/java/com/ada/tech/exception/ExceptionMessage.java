package com.ada.tech.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExceptionMessage {

    private Long timestamp;
    private int status;
    private String error;
    private String message;
}
