package com.example.sicredidemo.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Class de transporte com os erros encontrados
 */
@Builder
@Data
public class ErrorDTO {

    private String internalMessage;
    private String moreInfo;
    private Date dateTime;
}
