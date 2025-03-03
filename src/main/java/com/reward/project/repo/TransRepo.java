package com.reward.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.reward.project.entity.Transactions;
import java.time.LocalDate;
import java.util.List;


public interface TransRepo extends JpaRepository<Transactions, Long>{
	@Query("SELECT t FROM Transactions t WHERE t.customerId=:customerId AND t.transDate BETWEEN :startDate AND :endDate")
	List<Transactions> findTransactions(Long customerId, LocalDate  startDate, LocalDate  endDate);
	
	List<Transactions> findByCustomerId(Long customerId);
}
