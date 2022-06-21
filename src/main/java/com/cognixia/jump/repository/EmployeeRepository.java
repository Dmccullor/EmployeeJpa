package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
