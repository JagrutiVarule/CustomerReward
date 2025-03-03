package com.reward.project.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.reward.project.entity.Customer;
import com.reward.project.entity.Transactions;
import com.reward.project.repo.CustomerRepo;
import com.reward.project.repo.TransRepo;

class RewardCalculationTest {
	@Mock
    private CustomerRepo customerRepo;

    @Mock
    private TransRepo transRepo;

    @InjectMocks
    private RewardCalculation rewardCalculation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
	@Test
	void testCalculateMonthWise() {
		Long customerId = 1L;
        int month = 03;
        int year = 2025;

        List<Transactions> mockTransactions = new ArrayList<>();
        Transactions trans1 = new Transactions();
        trans1.setAmount(150);
        mockTransactions.add(trans1);
        when(transRepo.findTransactions(eq(customerId), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(mockTransactions);
        rewardCalculation.calculateMonthWise(customerId, month, year);
	}

	@Test
	void testTotalRewardPoints() {
		Long customerId = 1L;
        List<Transactions> mockTransactions = new ArrayList<>();
        Transactions trans1 = new Transactions();
        trans1.setAmount(150);
        mockTransactions.add(trans1);
        when(transRepo.findByCustomerId(customerId)).thenReturn(mockTransactions);
        rewardCalculation.totalRewardPoints(customerId);
	}

	@Test
	void testCalculateReward() {
		 assertEquals(90, RewardCalculation.calculateReward(120)); 
	}

	@Test
	void testGetCustomerData() {
		Long customerId = 1L;
        int month = 3;
        int year = 2025;

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName("abcd");
        List<Transactions> mockTransactions = new ArrayList<>();
        Transactions trans1 = new Transactions();
        trans1.setAmount(200);
        mockTransactions.add(trans1);
        when(customerRepo.findById(customerId)).thenReturn(Optional.of(customer));
        when(transRepo.findTransactions(eq(customerId), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(mockTransactions);
        rewardCalculation.getCustomerData(customerId, month, year);
	}

}
