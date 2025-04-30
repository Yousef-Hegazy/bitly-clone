package com.yousef.bitlyClone.repositories;

import com.yousef.bitlyClone.models.ClickEvent;
import com.yousef.bitlyClone.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    List<ClickEvent> findByUrlMappingAndClickedAtBetween(UrlMapping urlMapping, LocalDateTime from, LocalDateTime to);
    List<ClickEvent> findByUrlMappingInAndClickedAtBetween(List<UrlMapping> urlMapping, LocalDate from, LocalDate to);
}
