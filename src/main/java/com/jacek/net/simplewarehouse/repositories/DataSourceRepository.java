package com.jacek.net.simplewarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacek.net.simplewarehouse.entities.DataSource;

/**
 * @author Jacek Niepsuj
 */
public interface DataSourceRepository extends JpaRepository<DataSource, Long> {

}
