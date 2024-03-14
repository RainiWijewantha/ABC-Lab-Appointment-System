package com.example.test.repository;


import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.test.model.PaymentsModel;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentsModel, Long> {

	@Query("SELECT COALESCE(SUM(p.amount), 0) FROM PaymentsModel p WHERE DATE(p.date) = DATE(:date)")
	double getDailyIncome(@Param("date") Date date);

	@Query("SELECT COALESCE(SUM(p.amount), 0) FROM PaymentsModel p WHERE MONTH(p.date) = MONTH(:date) AND YEAR(p.date) = YEAR(:date)")
	double getMonthlyIncome(@Param("date") Date date);

	@Query("SELECT COALESCE(SUM(p.amount), 0) FROM PaymentsModel p WHERE p.date BETWEEN :startDate AND :endDate")
    double getAnnualIncome(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
