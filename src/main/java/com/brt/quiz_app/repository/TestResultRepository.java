package com.brt.quiz_app.repository;


import com.brt.quiz_app.entities.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult,Long> {

    List<TestResult> findAllByUserId(Long userId);
}
