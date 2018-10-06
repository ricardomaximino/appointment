package com.brasajava.domain.service;

import java.util.List;

import com.brasajava.domain.entity.Appointment;
import com.brasajava.domain.entity.Prospect;

public interface AppointmentService {

	Appointment createAppointment(Prospect prospect, Appointment appointment);
	Prospect createAppointments(Prospect prospect, List<Appointment> appointments);
}
