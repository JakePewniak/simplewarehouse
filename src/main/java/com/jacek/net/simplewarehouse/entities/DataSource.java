package com.jacek.net.simplewarehouse.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jacek Niepsuj
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "DATASOURCE")
public class DataSource implements PersistenceTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", length = 18, nullable = false)
    private Long id;

    @Column(name = "DATASOURCE", length = 200, nullable = false)
    private String datasource;

    @OneToMany(mappedBy="dataSource", fetch = FetchType.LAZY)
    private Set<DailyData> dailyDatas = new HashSet();

}
