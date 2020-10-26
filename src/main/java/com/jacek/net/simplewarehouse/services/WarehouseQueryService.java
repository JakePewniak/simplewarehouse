package com.jacek.net.simplewarehouse.services;

import java.util.List;

import com.jacek.net.simplewarehouse.dtos.query.CtrResultDto;
import com.jacek.net.simplewarehouse.dtos.query.DataSourcesResultDto;
import com.jacek.net.simplewarehouse.dtos.query.ImpressionsResultDto;
import com.jacek.net.simplewarehouse.dtos.query.TotalClicksResultsDto;
import com.jacek.net.simplewarehouse.dtos.searchparams.SearchParamsDto;

/**
 * @author Jacek Niepsuj
 */
public interface WarehouseQueryService {

    List<DataSourcesResultDto> getDataSourceList();

    List<TotalClicksResultsDto> getTotalClicks(SearchParamsDto searchParamsDto);

    List<CtrResultDto> getClickThroughRate(SearchParamsDto searchParamsDto);

    List<ImpressionsResultDto> getImpressions(SearchParamsDto build);
}
