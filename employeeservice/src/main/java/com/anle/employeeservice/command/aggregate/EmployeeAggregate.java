package com.anle.employeeservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.anle.employeeservice.command.command.CreateEmployeeCommand;
import com.anle.employeeservice.command.command.DeleteEmployeeCommand;
import com.anle.employeeservice.command.command.UpdateEmployeeCommand;
import com.anle.employeeservice.command.events.EmployeeCreatedEvent;
import com.anle.employeeservice.command.events.EmployeeDeletedEvent;
import com.anle.employeeservice.command.events.EmployeeUpdatedEvent;

@Aggregate
public class EmployeeAggregate {
	@AggregateIdentifier
	private String employeeId;
	private String firstName;
	private String lastName;
	private String kin;
	private Boolean isDisciplined;

	public EmployeeAggregate() {
	}

	@CommandHandler
	public EmployeeAggregate(CreateEmployeeCommand command) {
		EmployeeCreatedEvent event = new EmployeeCreatedEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}

	@CommandHandler
	public void handle(UpdateEmployeeCommand command) {
		EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}

	@CommandHandler
	public void handle(DeleteEmployeeCommand command) {
		EmployeeDeletedEvent event = new EmployeeDeletedEvent();
		event.setEmployeeId(employeeId);
		AggregateLifecycle.apply(event);

	}

	@EventSourcingHandler
	public void on(EmployeeCreatedEvent event) {
		this.employeeId = event.getEmployeeId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.kin = event.getKin();
		this.isDisciplined = event.getIsDisciplined();
	}

	@EventSourcingHandler
	public void on(EmployeeUpdatedEvent event) {
		this.employeeId = event.getEmployeeId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.kin = event.getKin();
		this.isDisciplined = event.getIsDisciplined();
	}

	@EventSourcingHandler
	public void on(EmployeeDeletedEvent event) {
		this.employeeId = event.getEmployeeId();
	}
}
