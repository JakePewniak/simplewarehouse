package com.jacek.net.simplewarehouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jacek.net.simplewarehouse.entities.Campaign;

/**
 * @author Jacek Niepsuj
 */
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}
