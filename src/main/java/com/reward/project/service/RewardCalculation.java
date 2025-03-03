package com.reward.project.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reward.project.dto.ResponseDTO;
import com.reward.project.entity.Customer;
import com.reward.project.entity.Transactions;
import com.reward.project.repo.CustomerRepo;
import com.reward.project.repo.TransRepo;

@Service
public class RewardCalculation {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private TransRepo transRepo;
    
    public int calculateMonthWise(Long customerId, int month, int year) {
    	LocalDate startDate = LocalDate.of(year, Month.of(month), 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
    	List<Transactions> transactions = transRepo.findTransactions(customerId, startDate, endDate);
    	int rewardPoints = 0;
    	for(Transactions  trans : transactions) {
    		rewardPoints+=calculateReward(trans.getAmount());
    	}    	
    	return rewardPoints;  	
    }
	
    public int totalRewardPoints(Long customerId) {
    	List<Transactions> transactionByCustomerId = transRepo.findByCustomerId(customerId);
    	int rewardPoints = 0;
    	for(Transactions trans : transactionByCustomerId) {
    		rewardPoints+= calculateReward(trans.getAmount());
    	}
    	return rewardPoints;  	
    }
	
	public static int calculateReward(double amount) {
		int reward = 0;
		if(amount>100) {
			reward+=(amount-100)*2+50;		
		}
		else if(amount>50) {
			reward+=(amount-50);
		}	
		return reward;
	}
	
	public ResponseDTO getCustomerData(Long customerId, int month, int year) {
        Optional<Customer> customerDetail = customerRepo.findById(customerId);
        ResponseDTO response  = new ResponseDTO();
        if (customerDetail.isPresent()) {
        	List<Transactions> TransactionDetail = new ArrayList<>();
        	if(month==0 && year==0) {
        		TransactionDetail= transRepo.findByCustomerId(customerId);
        	}else {
        		LocalDate startDate = LocalDate.of(year, Month.of(month), 1);
                LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                TransactionDetail = transRepo.findTransactions(customerId, startDate, endDate);
        	}      	
            Customer customer = customerDetail.get();            
            response.setCustomerId(customer.getCustomerId());
            response.setCustomerName(customer.getCustomerName());
            response.setTransactions(TransactionDetail);
        }
		return response;       
	}
}
