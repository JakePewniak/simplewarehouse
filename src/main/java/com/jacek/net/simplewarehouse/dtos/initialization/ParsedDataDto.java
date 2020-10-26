package com.jacek.net.simplewarehouse.dtos.initialization;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Jacek Niepsuj
 */
@Builder
@Getter
public class ParsedDataDto {

    private String datasource;
    private String campaign;
    private LocalDate daily;
    private Long clicks;
    private Long impressions;
}
