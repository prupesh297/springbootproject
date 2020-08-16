package com.rupesh.empl.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.rupesh.empl.model.Employee;

public interface EmployeeService {

	List<Employee> get();
	
	Employee get(int id);
	
	void save(Employee employee);
	
	void delete(int id);
	
	void saveAll(MultipartFile file);
}
