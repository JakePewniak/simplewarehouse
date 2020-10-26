package com.jacek.net.simplewarehouse.services.initialization;

import java.util.List;

import com.jacek.net.simplewarehouse.dtos.initialization.ParsedDataDto;

/**
 * @author Jacek Niepsuj
 */
public interface DataBaseLoader {

    void loadDataToDB(List<ParsedDataDto> parsedDataDtos);
}
