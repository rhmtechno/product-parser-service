package com.java.parser.repository;


import com.java.parser.domain.entity.ChangeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangeHistoryRepository extends JpaRepository<ChangeHistory,Long> {
    List<ChangeHistory> findAllByOrderByTimestampDesc();

    List<ChangeHistory> findByRequestId(String requestId);
}
