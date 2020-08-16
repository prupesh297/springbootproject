package com.rupesh.empl.dao;

import java.util.List;

import com.rupesh.empl.model.Employee;

public interface EmployeeDAO {

	List<Employee> get();
	
	Employee get(int id);
	
	void save(Employee employee);
	
	void delete(int id); 

}
