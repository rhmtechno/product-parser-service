package com.java.parser.repository;


import com.java.parser.domain.entity.ChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChangeHistoryRepository extends JpaRepository<ChangeHistory,Long> {
    List<ChangeHistory> findAllByOrderByTimestampDesc();
}
