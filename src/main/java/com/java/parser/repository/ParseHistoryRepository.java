package com.java.parser.repository;

import com.java.parser.domain.entity.ParseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParseHistoryRepository extends JpaRepository<ParseHistory,Long> {
}
