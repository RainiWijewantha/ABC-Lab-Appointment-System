package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.model.TestResultsModel;

@Repository
public interface TestResultsRepository extends JpaRepository<TestResultsModel, Long>{

}
