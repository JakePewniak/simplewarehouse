package com.jacek.net.simplewarehouse.dtos.errors;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Jacek Niepsuj
 */
@Builder
@Getter
public class ErrorResultDto {

    @JsonProperty("ErrorUUID")
    String uuid;

    @JsonProperty("ErrorMessage")
    String message;
}
