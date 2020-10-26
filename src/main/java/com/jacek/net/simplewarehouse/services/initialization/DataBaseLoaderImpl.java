package com.jacek.net.simplewarehouse.services.initialization;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jacek.net.simplewarehouse.dtos.initialization.ParsedDataDto;
import com.jacek.net.simplewarehouse.entities.Campaign;
import com.jacek.net.simplewarehouse.entities.DailyData;
import com.jacek.net.simplewarehouse.entities.DataSource;
import com.jacek.net.simplewarehouse.repositories.CampaignRepository;
import com.jacek.net.simplewarehouse.repositories.DailyDataRepository;
import com.jacek.net.simplewarehouse.repositories.DataSourceRepository;

/**
 * @author Jacek Niepsuj
 */
@Service
public class DataBaseLoaderImpl implements DataBaseLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseLoaderImpl.class);
    private final String NO_DATA_ERROR = "No data to load.";
    private final CampaignRepository campaignRepository;
    private final DailyDataRepository dailyDataRepository;
    private final DataSourceRepository dataSourceRepository;

    @Autowired
    public DataBaseLoaderImpl(CampaignRepository campaignRepository,
                              DailyDataRepository dailyDataRepository,
                              DataSourceRepository dataSourceRepository) {
        this.campaignRepository = campaignRepository;
        this.dailyDataRepository = dailyDataRepository;
        this.dataSourceRepository = dataSourceRepository;
    }

    @Override
    @Transactional
    public void loadDataToDB(List<ParsedDataDto> parsedDataDtos) {
        validateData(parsedDataDtos);
        LOGGER.info("Starting to load data to database.");

        List<ParsedDataDto> filteredDataSourceDtos = filterDictinctDataSourceData(parsedDataDtos);
        LOGGER.info("Found {} dataSourceDtos", filteredDataSourceDtos.size());
        List<DataSource> dataSourceEntities = filteredDataSourceDtos.stream()
                .map(parsedDataDto -> DataSource.builder()
                        .datasource(parsedDataDto.getDatasource())
                        .dailyDatas(new HashSet<>())
                        .build())
                .collect(Collectors.toList());
        LOGGER.info("dataSourceEntities {}", dataSourceEntities.size());

        List<ParsedDataDto> filteredCampaignDataDtos = filterDictinctCampaingData(parsedDataDtos);
        LOGGER.info("Found {} campaignDataDtos", filteredCampaignDataDtos.size());
        List<Campaign> campaignEntities = filteredCampaignDataDtos.stream()
                .map(parsedDataDto -> Campaign.builder()
                        .campaign(parsedDataDto.getCampaign())
                        .dailyDatas(new HashSet<>())
                        .build())
                .collect(Collectors.toList());
        LOGGER.info("campaignEntities {}", campaignEntities.size());

        List<ParsedDataDto> dailyDataDtos = filterDictinctDailyData(parsedDataDtos);
        LOGGER.info("Found {} dailyDataDtos", dailyDataDtos.size());

        final List<DailyData> dailyDatas = dailyDataDtos.stream()
                .map(dailyDataDto -> {
                    DataSource dataSource = dataSourceEntities.stream()
                            .filter(data -> data.getDatasource().equals(dailyDataDto.getDatasource()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Inconsistent data. Can not find datasource for daily data..."));
                    Campaign campaign = campaignEntities.stream()
                            .filter(data -> data.getCampaign().equals(dailyDataDto.getCampaign()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Inconsistent data. Can not find campaign for daily data..."));
                    DailyData dailyData = DailyData.builder()
                            .daily(dailyDataDto.getDaily())
                            .clicks(dailyDataDto.getClicks())
                            .impressions(dailyDataDto.getImpressions())
                            .dataSource(dataSource)
                            .campaign(campaign)
                            .build();
                    dataSource.getDailyDatas().add(dailyData);
                    campaign.getDailyDatas().add(dailyData);
                    return dailyData;
                }).collect(Collectors.toList());

        List<DailyData> list = dailyDataRepository.saveAll(dailyDatas);

        LOGGER.info("Database initialization completed. {}", list.size());
    }

    private List<ParsedDataDto> filterDictinctDataSourceData(List<ParsedDataDto> allParsedDataDtos) {
        return allParsedDataDtos
                .stream()
                .filter(distinctByKeys(ParsedDataDto::getDatasource))
                .collect(Collectors.toList());
    }

    private List<ParsedDataDto> filterDictinctCampaingData(List<ParsedDataDto> allParsedDataDtos) {
        return allParsedDataDtos
                .stream()
                .filter(distinctByKeys(ParsedDataDto::getDatasource, ParsedDataDto::getCampaign))
                .collect(Collectors.toList());
    }

    private List<ParsedDataDto> filterDictinctDailyData(List<ParsedDataDto> allParsedDataDtos) {
        return allParsedDataDtos
                .stream()
                .filter(distinctByKeys(ParsedDataDto::getDatasource, ParsedDataDto::getCampaign, ParsedDataDto::getDaily, ParsedDataDto::getClicks, ParsedDataDto::getImpressions))
                .collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    private void validateData(List<ParsedDataDto> parsedDataDtos) {
        if(CollectionUtils.isEmpty(parsedDataDtos)) {
            LOGGER.error(NO_DATA_ERROR);
            throw new RuntimeException(NO_DATA_ERROR);
        }
    }
}
