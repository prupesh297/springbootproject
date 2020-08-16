package com.rupesh.empl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rupesh.empl.model.Employee;
import com.rupesh.empl.service.EmployeeService;
import com.rupesh.empl.utility.ExcelHelper;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

	@Autowired(required=true)
	private EmployeeService employeeService;
	
	@PostMapping(value="/createemployee")
	public Employee save(@RequestBody Employee employeeObj) {
		if(employeeObj.getId()==-1) {
			employeeObj.setId(null);
		}
		employeeService.save(employeeObj);
		return employeeObj;
	}
	
	@GetMapping("/allemployee")
	public List<Employee> get(){
		return employeeService.get();
	}
	
	@GetMapping("/empl/{id}")
	public Employee get(@PathVariable int id) {
		Employee employeeObj = employeeService.get(id);
		if(employeeObj == null) {
			throw new RuntimeException("Employee not found for the Id:"+id);
		}
		return employeeObj;
	}
	
	@PutMapping("/updateemployee")
	public Employee update(@RequestBody Employee employeeObj) {
		employeeService.save(employeeObj);
		return employeeObj;
	}
	
	@DeleteMapping("/empl/{id}")
	public String delete(@PathVariable int id) {
		employeeService.delete(id);
		return "Employee has been deleted with id:"+id;
	}
	
	@PostMapping(value="/upload/excel")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";

	    if (ExcelHelper.hasExcelFormat(file)) {
	      try {
	    	  employeeService.saveAll(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new String(message));
	      } catch (Exception e) {
	    	  e.printStackTrace();
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new String(message));
	      }
	    }

	    message = "Please upload an excel file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new String(message));
	  }
}

