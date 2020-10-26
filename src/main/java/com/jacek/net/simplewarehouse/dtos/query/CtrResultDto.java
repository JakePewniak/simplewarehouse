package com.jacek.net.simplewarehouse.dtos.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Jacek Niepsuj
 */
@Builder
@Getter
@AllArgsConstructor
public class CtrResultDto {

    String name;
    Long clicks;
    Long impressions;

    public String getCtr() {
        return String.format("%.2f", (double)clicks*100/impressions);
    }
}
