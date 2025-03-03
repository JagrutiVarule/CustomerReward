package com.reward.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.reward.project.dto.ResponseDTO;
import com.reward.project.service.RewardCalculation;

@RestController
@RequestMapping("/CalculateReward")
public class CustomerRewardController {
	
	@Autowired
	private RewardCalculation rewardCalculation;	
	
	@GetMapping("/perMonth")
	public ResponseEntity<ResponseDTO> getPerMonthPoints(@RequestParam Long customerId, @RequestParam int month, @RequestParam int year){				
		int monthWiseReward = rewardCalculation.calculateMonthWise(customerId, month, year);
		ResponseDTO customerData = rewardCalculation.getCustomerData(customerId, month, year);
		customerData.setTotalPoints(monthWiseReward);
		return ResponseEntity.ok(customerData);
	}
	
	@GetMapping("/CustomerTotalPoints")
    public ResponseEntity<ResponseDTO> getCustomerTotalPoints(@RequestParam Long customerId) {
		int totalRewardPoints = rewardCalculation.totalRewardPoints(customerId);
		ResponseDTO customerData = rewardCalculation.getCustomerData(customerId, 0, 0);
		customerData.setTotalPoints(totalRewardPoints);
        return ResponseEntity.ok(customerData);
    }

}
