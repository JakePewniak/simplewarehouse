package com.jacek.net.simplewarehouse.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jacek.net.simplewarehouse.dtos.query.CtrResultDto;
import com.jacek.net.simplewarehouse.dtos.query.TotalClicksResultsDto;
import com.jacek.net.simplewarehouse.entities.DailyData;
import com.jacek.net.simplewarehouse.dtos.query.ImpressionsResultDto;

/**
 * @author Jacek Niepsuj
 */
public interface DailyDataRepository extends JpaRepository<DailyData, Long>, JpaSpecificationExecutor<DailyData> {

    @Query("SELECT new com.jacek.net.simplewarehouse.dtos.query.TotalClicksResultsDto(d.dataSource.datasource, min(d.daily), max(d.daily), sum(d.clicks)) "
                   + "FROM DailyData d where "
                   + "(:datasource is not null and d.dataSource.datasource = :datasource or :datasource is null) and "
                   + "(:campaign is not null and d.campaign.campaign = :campaign or :campaign is null) and "
                   + "(:startDate is not null and :endDate is not null and d.daily between :startDate and :endDate or :startDate is null or :endDate is null)"
                   + " group by d.dataSource order by d.dataSource")
    List<TotalClicksResultsDto> getTotalClicks(@Param("datasource") String datasource, @Param("campaign") String campaign, @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);


    @Query("SELECT new com.jacek.net.simplewarehouse.dtos.query.CtrResultDto(d.dataSource.datasource, sum(d.clicks), sum(d.impressions)) "
                   + "FROM DailyData d where "
                   + "(:datasource is not null and d.dataSource.datasource = :datasource or :datasource is null) and "
                   + "(:startDate is not null and :endDate is not null and d.daily between :startDate and :endDate or :startDate is null or :endDate is null)"
                   + " group by d.dataSource order by d.dataSource")
    List<CtrResultDto> getClickThroughRateByDatasource(@Param("datasource") String datasource, @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.jacek.net.simplewarehouse.dtos.query.CtrResultDto(d.campaign.campaign, sum(d.clicks), sum(d.impressions)) "
                   + "FROM DailyData d where "
                   + "(:campaign is not null and d.campaign.campaign = :campaign or :campaign is null) and "
                   + "(:startDate is not null and :endDate is not null and d.daily between :startDate and :endDate or :startDate is null or :endDate is null)"
                   + " group by d.campaign order by d.campaign")
    List<CtrResultDto> getClickThroughRateByCampaign(@Param("campaign") String campaign, @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.jacek.net.simplewarehouse.dtos.query.ImpressionsResultDto(d.campaign.campaign, sum(d.clicks), sum(d.impressions), d.daily) "
                   + "FROM DailyData d where "
                   + "(:campaign is not null and d.campaign.campaign = :campaign or :campaign is null) and "
                   + "(:startDate is not null and :endDate is not null and d.daily between :startDate and :endDate or :startDate is null or :endDate is null)"
                   + " group by d.campaign, d.daily order by d.daily")
    List<ImpressionsResultDto> getImpressionsByCampaign(@Param("campaign") String campaign, @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

    @Query("SELECT new com.jacek.net.simplewarehouse.dtos.query.ImpressionsResultDto(d.dataSource.datasource, sum(d.clicks), sum(d.impressions), d.daily) "
                   + "FROM DailyData d where "
                   + "(:datasource is not null and d.dataSource.datasource = :datasource or :datasource is null) and "
                   + "(:startDate is not null and :endDate is not null and d.daily between :startDate and :endDate or :startDate is null or :endDate is null)"
                   + " group by d.dataSource, d.daily order by d.daily")
    List<ImpressionsResultDto> getImpressionsByDatasource(@Param("datasource") String datasource, @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
