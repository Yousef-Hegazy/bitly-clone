package com.yousef.bitlyClone.repositories;

import com.yousef.bitlyClone.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
}
