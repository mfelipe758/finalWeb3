package com.ada.tech.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FieldErrorMessage {

    private Long timestamp;
    private int status;
    private String error;
    private List<FieldMessage> messages;
}
