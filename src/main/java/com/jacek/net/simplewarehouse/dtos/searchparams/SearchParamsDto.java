package com.jacek.net.simplewarehouse.dtos.searchparams;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Jacek Niepsuj
 */
@Builder
@Getter
public class SearchParamsDto {

    private String dataSource;
    private String campaign;
    private LocalDate startDate;
    private LocalDate endDate;
}
