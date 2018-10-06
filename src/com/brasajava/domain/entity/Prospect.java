package com.brasajava.domain.entity;

import java.util.List;

import com.brasajava.domain.enums.ProspectStatus;

public class Prospect {
	
	private String id;
	private ProspectStatus status;
	private List<Appointment> appointments;
	
	public Prospect() {
		super();
	}
	
	public Prospect(String id, ProspectStatus status, List<Appointment> appointments) {
		super();
		this.id = id;
		this.status = status;
		this.appointments = appointments;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ProspectStatus getStatus() {
		return status;
	}
	public void setStatus(ProspectStatus status) {
		this.status = status;
	}
	public List<Appointment> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Override
	public String toString() {
		return "Prospect [id=" + id + ", status=" + status + ", appointments=" + appointments + "]";
	}
}
