package com.anle.employeeservice.command.events;

import java.util.Optional;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anle.employeeservice.command.data.Employee;
import com.anle.employeeservice.command.data.EmployeeRepository;

@Component
public class EmployeeEventsHandler {
	@Autowired
	private EmployeeRepository employeeRepository;

	@EventHandler
	public void on(EmployeeCreatedEvent employeeCreatedEvent) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeCreatedEvent, employee);
		employeeRepository.save(employee);
	}

	@EventHandler
	public void on(EmployeeUpdatedEvent event) {
		Optional<Employee> emOptional = employeeRepository.findById(event.getEmployeeId());
		if (emOptional.isPresent()) {
			Employee employee = emOptional.get();
			employee.setFirstName(event.getFirstName());
			employee.setLastName(event.getLastName());
			employee.setKin(event.getKin());
			employee.setIsDisciplined(event.getIsDisciplined());
			employeeRepository.save(employee);
		}
	}

	@EventHandler
	public void on(EmployeeDeletedEvent event) {
		Optional<Employee> emOptional = employeeRepository.findById(event.getEmployeeId());
		if (emOptional.isPresent()) {
			Employee employee = emOptional.get();
			employeeRepository.deleteById(employee.getEmployeeId());
		}
	}
}
