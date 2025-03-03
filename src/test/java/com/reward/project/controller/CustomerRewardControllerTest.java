package com.reward.project.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.reward.project.dto.ResponseDTO;
import com.reward.project.service.RewardCalculation;

class CustomerRewardControllerTest {
	@Mock
    private RewardCalculation rewardCalculation;
	
	@InjectMocks
    private CustomerRewardController customerRewardController;
	
	private MockMvc mockMvc;
	
	 @BeforeEach
	 void setUp() {
		 MockitoAnnotations.openMocks(this);
		 mockMvc = MockMvcBuilders.standaloneSetup(customerRewardController).build();
	 }
	
	@Test
	void testGetPerMonthPoints() throws Exception{
		Long customerId = 1L;
        int month = 3;
        int year = 2025;
        int monthWiseReward = 150;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTotalPoints(monthWiseReward);

        when(rewardCalculation.calculateMonthWise(customerId, month, year)).thenReturn(monthWiseReward);
        when(rewardCalculation.getCustomerData(customerId, month, year)).thenReturn(responseDTO);

        mockMvc.perform(get("/CalculateReward/perMonth")
                        .param("customerId", customerId.toString())
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalPoints").value(monthWiseReward));

        verify(rewardCalculation).calculateMonthWise(customerId, month, year);
        verify(rewardCalculation).getCustomerData(customerId, month, year);
	}

	@Test
	void testGetCustomerTotalPoints() throws Exception {
		Long customerId = 1L;
        int totalRewardPoints = 300;
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setTotalPoints(totalRewardPoints);

        when(rewardCalculation.totalRewardPoints(customerId)).thenReturn(totalRewardPoints);
        when(rewardCalculation.getCustomerData(customerId, 0, 0)).thenReturn(responseDTO);

        mockMvc.perform(get("/CalculateReward/CustomerTotalPoints")
                        .param("customerId", customerId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPoints").value(totalRewardPoints));

        verify(rewardCalculation).totalRewardPoints(customerId);
        verify(rewardCalculation).getCustomerData(customerId, 0, 0);
	}

}
