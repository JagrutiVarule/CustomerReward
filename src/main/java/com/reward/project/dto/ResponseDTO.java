package com.reward.project.dto;

import java.util.List;

import com.reward.project.entity.Transactions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO {
	 private Long customerId;
	 private String customerName;
	 private List<Transactions> transactions;
	 private int totalPoints;	 
}
