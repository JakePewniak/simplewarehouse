package com.jacek.net.simplewarehouse.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jacek Niepsuj
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
@Table(name = "DAILY_DATA")
public class DailyData implements PersistenceTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 18, nullable = false)
    private Long id;

    @Column(name = "DAILY", nullable = false)
    private LocalDate daily;

    @Column(name = "CLICKS", length = 18, nullable = false)
    private Long clicks;

    @Column(name = "IMPRESSIONS", length = 18, nullable = false)
    private Long impressions;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DATASOURCE", referencedColumnName = "ID")
    private DataSource dataSource;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CAMPAIGN", referencedColumnName = "ID")
    private Campaign campaign;

}
