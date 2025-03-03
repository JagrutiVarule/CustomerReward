package com.reward.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reward.project.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

}
