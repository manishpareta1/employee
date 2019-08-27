package com.example.springboot2.employeeApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot2.employeeApp.exception.ResourceNotFoundException;
import com.example.springboot2.employeeApp.model.Address;
import com.example.springboot2.employeeApp.model.CountryDetail;
import com.example.springboot2.employeeApp.model.Employee;
import com.example.springboot2.employeeApp.repository.EmployeeRepository;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class EmployeeController implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	public EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") Long id) throws ResourceNotFoundException{
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Requested Employee doesnt exists. Emp id: " + id));
		System.out.println(employee.getAddress().getCountry().toString());
		return ResponseEntity.ok().body(employee);
		
	}
	
	
	 @PostMapping("/employees")
	    public Employee createEmployee(@Valid @RequestBody Employee employee) {
		 System.out.println("Received Employee Details: " + employee.toString());
		 employee.setId(0);
	        return employeeRepository.save(employee);
	    }
	 
	 @PutMapping("/employees/{id}")
	    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
	         @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
	        Employee employee = employeeRepository.findById(employeeId)
	        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
	        CountryDetail country = new CountryDetail(employeeDetails.getAddress().getCountry().getCity(),
	        		employeeDetails.getAddress().getCountry().getState(),
	        		employeeDetails.getAddress().getCountry().getCountry());
	        country.setId(employeeDetails.getAddress().getCountry().getId());
	        Address address = new Address(employeeDetails.getAddress().getAddressLine1(),
	        		employeeDetails.getAddress().getAddressLine2(), country);
	        address.setId(employeeDetails.getAddress().getId());
	        employee.setEmailId(employeeDetails.getEmailId());
	        employee.setLastName(employeeDetails.getLastName());
	        employee.setFirstName(employeeDetails.getFirstName());
	        employee.setAddress(address);
	        //Defect - new row getting created for address and country table in DB if there is change in address obj 
	        //changed method from save() to saveAndFlush() - 
	        final Employee updatedEmployee = employeeRepository.saveAndFlush(employee);
	        return ResponseEntity.ok(updatedEmployee);
	    }

	    @DeleteMapping("/employees/{id}")
	    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
	         throws ResourceNotFoundException {
	        Employee employee = employeeRepository.findById(employeeId)
	       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

	        employeeRepository.delete(employee);
	        Map<String, Boolean> response = new HashMap<>();
	        response.put("deleted", Boolean.TRUE);
	        return response;
	    }

}
