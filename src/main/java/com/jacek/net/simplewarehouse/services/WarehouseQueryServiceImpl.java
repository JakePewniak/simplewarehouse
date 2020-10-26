package com.jacek.net.simplewarehouse.services;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jacek.net.simplewarehouse.dtos.query.CtrResultDto;
import com.jacek.net.simplewarehouse.dtos.query.DataSourcesResultDto;
import com.jacek.net.simplewarehouse.dtos.query.ImpressionsResultDto;
import com.jacek.net.simplewarehouse.dtos.query.TotalClicksResultsDto;
import com.jacek.net.simplewarehouse.dtos.searchparams.SearchParamsDto;
import com.jacek.net.simplewarehouse.entities.DataSource;
import com.jacek.net.simplewarehouse.repositories.DailyDataRepository;
import com.jacek.net.simplewarehouse.repositories.DataSourceRepository;

/**
 * @author Jacek Niepsuj
 */
@Component
public class WarehouseQueryServiceImpl implements WarehouseQueryService {

    private DataSourceRepository dataSourceRepository;
    private DailyDataRepository dailyDataRepository;

    @Autowired
    public WarehouseQueryServiceImpl(DataSourceRepository dataSourceRepository,
                                     DailyDataRepository dailyDataRepository) {
        this.dataSourceRepository = dataSourceRepository;
        this.dailyDataRepository = dailyDataRepository;
    }

    @Override
    @Transactional
    public List<DataSourcesResultDto> getDataSourceList() {
        List<DataSource> dataSources = dataSourceRepository.findAll();
        List<DataSourcesResultDto> result = Optional.ofNullable(dataSources)
                .map(dataSource -> dataSource.stream()
                        .map(data -> DataSourcesResultDto.builder()
                        .name(data.getDatasource())
                        .build())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        return result;
    }

    @Transactional
    public List<TotalClicksResultsDto> getTotalClicks(SearchParamsDto searchParamsDto) {
        return dailyDataRepository.getTotalClicks(searchParamsDto.getDataSource(), searchParamsDto.getCampaign(), searchParamsDto.getStartDate(),
                searchParamsDto.getEndDate());
    }

    @Override
    public List<CtrResultDto> getClickThroughRate(SearchParamsDto searchParamsDto) {
        if (Objects.nonNull(searchParamsDto.getCampaign())) {
            return dailyDataRepository.getClickThroughRateByCampaign(searchParamsDto.getCampaign(), searchParamsDto.getStartDate(),
                    searchParamsDto.getEndDate());
        } else {
            return dailyDataRepository.getClickThroughRateByDatasource(searchParamsDto.getDataSource(), searchParamsDto.getStartDate(),
                    searchParamsDto.getEndDate());
        }
    }

    @Override
    public List<ImpressionsResultDto> getImpressions(SearchParamsDto searchParamsDto) {
        if (Objects.nonNull(searchParamsDto.getCampaign())) {
            return dailyDataRepository.getImpressionsByCampaign(searchParamsDto.getCampaign(), searchParamsDto.getStartDate(),
                    searchParamsDto.getEndDate());
        } else {
            return dailyDataRepository.getImpressionsByDatasource(searchParamsDto.getDataSource(), searchParamsDto.getStartDate(),
                    searchParamsDto.getEndDate());
        }
    }
}
