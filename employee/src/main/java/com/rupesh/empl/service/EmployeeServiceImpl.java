package com.rupesh.empl.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.empl.dao.EmployeeDAO;
import com.rupesh.empl.model.Employee;
import com.rupesh.empl.utility.ExcelHelper;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeDAO employeeDAO; 
	
	@Transactional
	@Override
	public List<Employee> get() {
		return employeeDAO.get();
	}

	@Transactional
	@Override
	public Employee get(int id) {
		return employeeDAO.get(id);
	}

	@Transactional
	@Override
	public void save(Employee employee) {
		employeeDAO.save(employee);
	}

	@Transactional
	@Override
	public void delete(int id) {
		employeeDAO.delete(id);
	}

	@Transactional
	@Override
	public void saveAll(MultipartFile file) {
	try {
		List<Employee> empList=ExcelHelper.excelToTutorials(file.getInputStream());
		for(Employee emp:empList) {
			employeeDAO.save(emp);
		}
	} catch (IOException e) {
		throw new RuntimeException("fail to store excel data: " + e.getMessage());
	}
	
	}
}
