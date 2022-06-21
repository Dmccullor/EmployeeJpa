package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Employee;
import com.cognixia.jump.repository.EmployeeRepository;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	EmployeeRepository repo;
	
	@GetMapping("/employee")
	public List<Employee> getEmployees() {
		
		return repo.findAll();
		
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable int id) {
		
		Optional<Employee> found = repo.findById(id);
		
		if(found.isEmpty()) {
			return ResponseEntity.status(404).body("Employee with id of " + id + " not found");
		}
		else {
			return ResponseEntity.status(200).body(found.get());
		}
	}
	
	@PostMapping("/employee")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		
		employee.setId(null);
		
		Employee created = repo.save(employee);
		
		return ResponseEntity.status(201).body(created);
	}
	
	@PutMapping("/employee")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) {
		
		boolean exists = repo.existsById(employee.getId());
		
		if(exists) {
			Employee updated = repo.save(employee);
			
			return ResponseEntity.status(200).body(updated);
		}
		else {
			return ResponseEntity.status(404).body("Can't perform update, employee doesn't exist");
		}
	}
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> deletEmployee(@PathVariable int id) {
		
		boolean exists = repo.existsById(id);
		
		if(exists) {
			
			repo.deleteById(id);
			
			return ResponseEntity.status(200).body("Student was deleted");
		}
		
		else {
			return ResponseEntity.status(404).body("Can't delete, employee doesn't exist");
		}
	}
	
	@PatchMapping("/employee/department")
	public ResponseEntity<?> updateDept(@PathParam(value = "id") int id, @PathParam(value = "department") String department) {
		
		Optional<Employee> found = repo.findById(id);
		
		if(found.isEmpty()) {
			
			return ResponseEntity.status(404).body("Employee with id of " + id + " not found");
		}
		else {
			
			Employee toUpdate = found.get();
			
			toUpdate.setDepartment(department);
			
			repo.save(toUpdate);
			
			return ResponseEntity.status(200).body("Department for employee has been changed");
		}
	}
	
	@PatchMapping("/employee/title")
	public ResponseEntity<?> updateTitle(@PathParam(value = "id") int id, @PathParam(value = "title") String title) {
		
		Optional<Employee> found = repo.findById(id);
		
		if(found.isEmpty()) {
			
			return ResponseEntity.status(404).body("Employee with id of " + id + " not found");
		}
		else {
			
			Employee toUpdate = found.get();
			
			toUpdate.setTitle(title);
			
			repo.save(toUpdate);
			
			return ResponseEntity.status(200).body("Title for employee was changed");
		}
	}

}
