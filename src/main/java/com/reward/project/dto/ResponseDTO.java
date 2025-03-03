package com.reward.project.dto;

import java.util.List;

import com.reward.project.entity.Transactions;

public class ResponseDTO {
	 private Long customerId;
	 private String customerName;
	 private List<Transactions> transactions;
	 private int totalPoints;
	
	 public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public List<Transactions> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transactions> transactions) {
		this.transactions = transactions;
	}
	public int getTotalPoints() {
		return totalPoints;
	}
	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}
	 
	 
}
