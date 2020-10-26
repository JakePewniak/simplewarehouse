package com.jacek.net.simplewarehouse.dtos.query;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Jacek Niepsuj
 */
@Builder
@Getter
@AllArgsConstructor
public class ImpressionsResultDto {

    String name;
    Long clicks;
    Long impressions;
    LocalDate date;
}
