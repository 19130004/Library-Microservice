package com.anle.employeeservice.query.projection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anle.commonservice.model.EmployeeResponseCommonModel;
import com.anle.commonservice.query.GetDetailsEmployeeQuery;
import com.anle.employeeservice.command.data.Employee;
import com.anle.employeeservice.command.data.EmployeeRepository;
import com.anle.employeeservice.query.model.EmployeeReponseModel;
import com.anle.employeeservice.query.queries.GetAllEmployeeQuery;
import com.anle.employeeservice.query.queries.GetEmployeesQuery;

@Component
public class EmployeeProjection {
	@Autowired
	private EmployeeRepository employeeRepository;

	@QueryHandler
	public EmployeeReponseModel handle(GetEmployeesQuery getEmployeesQuery) {
		EmployeeReponseModel model = new EmployeeReponseModel();
		Optional<Employee> employeeOptional = employeeRepository.findById(getEmployeesQuery.getEmployeeId());
		if (employeeOptional.isPresent()) {
			Employee employee = employeeOptional.get();
			BeanUtils.copyProperties(employee, model);
		}
		return model;
	}

	@QueryHandler
	public List<EmployeeReponseModel> handle(GetAllEmployeeQuery getAllEmployeeQuery) {
		List<EmployeeReponseModel> listModel = new ArrayList<>();
		List<Employee> listEntity = employeeRepository.findAll();
		listEntity.stream().forEach(e -> {
			EmployeeReponseModel model = new EmployeeReponseModel();
			BeanUtils.copyProperties(e, model);
			listModel.add(model);
		});
		return listModel;
	}

	@QueryHandler
	public EmployeeResponseCommonModel handle(GetDetailsEmployeeQuery getDetailsEmployeeQuery) {
		EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
		Employee employee = employeeRepository.getById(getDetailsEmployeeQuery.getEmployeeId());
		BeanUtils.copyProperties(employee, model);

		return model;
	}

}
