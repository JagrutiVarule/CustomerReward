package com.reward.project.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.reward.project.controller.CustomerRewardController;
import com.reward.project.service.RewardCalculation;

@WebMvcTest(CustomerRewardController.class)
class GlobalExceptionHandlerTest {
	@Autowired
    private MockMvc mockMvc;

	@MockBean
    private RewardCalculation rewardCalculation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
	@Test
	void testHandleCustomerNotFoundException() throws Exception {
		when(rewardCalculation.getCustomerData(anyLong(), anyInt(), anyInt()))
				.thenThrow(new CustomerNotFoundException("Customer not found."));

		mockMvc.perform(get("/CalculateReward/CustomerTotalPoints").param("customerId", "1"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.errorCode").value("CUSTOMER_NOT_FOUND"))
				.andExpect(jsonPath("$.errorMessage").value("Customer not found."));
	}

	@Test
	void testHandleInvalidMonthYearException() throws Exception {
		when(rewardCalculation.calculateMonthWise(anyLong(), anyInt(), anyInt())).thenThrow(new InvalidMonthYearException("Month should be between 1 to 12."));

        mockMvc.perform(get("/CalculateReward/perMonth")
                .param("customerId", "1")
                .param("month", "13")
                .param("year", "2025"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("INVALID_MONTH_YEAR"))
                .andExpect(jsonPath("$.errorMessage").value("Month should be between 1 to 12."));
	}

}
