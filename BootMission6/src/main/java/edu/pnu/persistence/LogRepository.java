package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.DbLog;

public interface LogRepository extends JpaRepository<DbLog, Integer> {
}
