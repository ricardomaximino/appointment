package com.brasajava.domain.entity;

import com.brasajava.domain.enums.AppointmentStatus;
import com.brasajava.domain.enums.AppointmentType;

public class Appointment {
	private String id;
	private AppointmentType type;
	private AppointmentStatus status;
	private String owner;
	private String closingReason;
	
	
	
	
	public Appointment() {
		super();
	}
	public Appointment(String id, AppointmentType type, AppointmentStatus status, String owner, String closingReason) {
		super();
		this.id = id;
		this.type = type;
		this.status = status;
		this.owner = owner;
		this.closingReason = closingReason;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public AppointmentType getType() {
		return type;
	}
	public void setType(AppointmentType type) {
		this.type = type;
	}
	public AppointmentStatus getStatus() {
		return status;
	}
	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getClosingReason() {
		return closingReason;
	}
	public void setClosingReason(String closingReason) {
		this.closingReason = closingReason;
	}
	@Override
	public String toString() {
		return "Appointment [id=" + id + ", type=" + type + ", status=" + status + ", owner=" + owner
				+ ", closingReason=" + closingReason + "]";
	}

	
}
