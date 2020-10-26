package com.jacek.net.simplewarehouse.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jacek.net.simplewarehouse.dtos.query.CtrResultDto;
import com.jacek.net.simplewarehouse.dtos.query.DataSourcesResultDto;
import com.jacek.net.simplewarehouse.dtos.query.ImpressionsResultDto;
import com.jacek.net.simplewarehouse.dtos.query.TotalClicksResultsDto;
import com.jacek.net.simplewarehouse.dtos.searchparams.SearchParamsDto;
import com.jacek.net.simplewarehouse.services.WarehouseQueryService;
import com.jacek.net.simplewarehouse.services.initialization.DataInitializerService;

/**
 * @author Jacek Niepsuj
 */
@RestController
public class SimpleWarehouseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleWarehouseController.class);
    private final DataInitializerService dataInitializerService;
    private final WarehouseQueryService warehouseQueryService;

    @Autowired
    public SimpleWarehouseController(DataInitializerService dataInitializerService, WarehouseQueryService warehouseQueryService) {
        this.dataInitializerService = dataInitializerService;
        this.warehouseQueryService = warehouseQueryService;
    }

    @GetMapping("/datasourceList")
    public ResponseEntity<List<DataSourcesResultDto>> datasourceList() {
        List<DataSourcesResultDto> dataSourcesResultDtos = warehouseQueryService.getDataSourceList();
        return ResponseEntity.ok(dataSourcesResultDtos);
    }

    @GetMapping("/totalclicks")
    public ResponseEntity<List<TotalClicksResultsDto>> totalclicks(@RequestParam(value = "datasource", required=false) String datasource,
                                                                   @RequestParam(value = "campaign", required=false) String campaign,
                                                                   @RequestParam(value = "from", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                   @RequestParam(value = "to", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        LOGGER.info("datasourceclicks request with params: datasource {}, campaign {}, startDate {}, endDate {}", datasource, campaign, startDate, endDate);
        List<TotalClicksResultsDto> totalClicks = warehouseQueryService.getTotalClicks(SearchParamsDto.builder()
                .dataSource(datasource)
                .campaign(campaign)
                .startDate(startDate)
                .endDate(endDate)
                .build());
        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/ctr")
    public ResponseEntity<List<CtrResultDto>> clickThroughRate(@RequestParam(value = "datasource", required=false) String datasource,
                                                                        @RequestParam(value = "campaign", required=false) String campaign,
                                                                        @RequestParam(value = "from", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                        @RequestParam(value = "to", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        LOGGER.info("ctr request with params: datasource {}, campaign {}, startDate {}, endDate {}", datasource, campaign, startDate, endDate);
        List<CtrResultDto> totalClicks = warehouseQueryService.getClickThroughRate(SearchParamsDto.builder()
                .dataSource(datasource)
                .campaign(campaign)
                .startDate(startDate)
                .endDate(endDate)
                .build());
        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/impressions")
    public ResponseEntity<List<ImpressionsResultDto>> impressions(@RequestParam(value = "datasource", required=false) String datasource,
                                                                  @RequestParam(value = "campaign", required=false) String campaign,
                                                                  @RequestParam(value = "from", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                  @RequestParam(value = "to", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        LOGGER.info("impressions request with params: datasource {}, campaign {}, startDate {}, endDate {}", datasource, campaign, startDate, endDate);
        List<ImpressionsResultDto> totalClicks = warehouseQueryService.getImpressions(SearchParamsDto.builder()
                .dataSource(datasource)
                .campaign(campaign)
                .startDate(startDate)
                .endDate(endDate)
                .build());
        return ResponseEntity.ok(totalClicks);
    }

    @PostMapping("/init")
    public ResponseEntity<String> init() {
        LOGGER.info("init called...");
        dataInitializerService.init();
        return ResponseEntity.ok("OK");
    }
}
