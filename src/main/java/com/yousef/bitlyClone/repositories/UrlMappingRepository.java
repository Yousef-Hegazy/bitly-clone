package com.yousef.bitlyClone.repositories;

import com.yousef.bitlyClone.models.UrlMapping;
import com.yousef.bitlyClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    List<UrlMapping> findAllByUser(User user);
}
