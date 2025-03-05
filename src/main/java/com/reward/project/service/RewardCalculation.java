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
import com.reward.project.exception.CustomerNotFoundException;
import com.reward.project.exception.InvalidMonthYearException;
import com.reward.project.repo.CustomerRepo;
import com.reward.project.repo.TransRepo;

@Service
public class RewardCalculation {
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private TransRepo transactionRepo;
    
    public int calculateMonthWise(Long customerId, int month, int year) {
    	if (month < 1 || month > 12) {
            throw new InvalidMonthYearException("Month should be between 1 to 12.");
        }
    	LocalDate startDate = LocalDate.of(year, Month.of(month), 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
    	List<Transactions> transactions = transactionRepo.findTransactions(customerId, startDate, endDate);
    	if (transactions.isEmpty()) {
            throw new InvalidMonthYearException("transactions not found");
        }
    	int rewardPoints = 0;
    	for(Transactions  transaction : transactions) {
    		rewardPoints+=calculateReward(transaction.getAmount());
    	}    	
    	return rewardPoints;  	
    }
	
    public int totalRewardPoints(Long customerId) {
    	List<Transactions> transactionByCustomerId = transactionRepo.findByCustomerId(customerId);
    	if (transactionByCustomerId.isEmpty()) {
            throw new InvalidMonthYearException("transactions not found");
        }
    	int rewardPoints = 0;
    	for(Transactions transactionData : transactionByCustomerId) {
    		rewardPoints+= calculateReward(transactionData.getAmount());
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
        if (!customerDetail.isPresent()) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }
        ResponseDTO response  = new ResponseDTO();
    	List<Transactions> TransactionDetail = new ArrayList<>();
    	if(month==0 && year==0) {
    		TransactionDetail= transactionRepo.findByCustomerId(customerId);
    	}else {
    		if (month < 1 || month > 12) {
                throw new InvalidMonthYearException("Month should be between 1 to 12.");
            }
    		LocalDate startDate = LocalDate.of(year, Month.of(month), 1);
            LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
            TransactionDetail = transactionRepo.findTransactions(customerId, startDate, endDate);
    	}      	
        Customer customer = customerDetail.get();            
        response.setCustomerId(customer.getCustomerId());
        response.setCustomerName(customer.getCustomerName());
        response.setTransactions(TransactionDetail);
		return response;       
	}
}
