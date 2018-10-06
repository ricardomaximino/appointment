package com.brasajava.domain.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.brasajava.domain.entity.Appointment;
import com.brasajava.domain.entity.Prospect;
import com.brasajava.domain.enums.AppointmentStatus;
import com.brasajava.domain.enums.AppointmentType;
import com.brasajava.domain.service.AppointmentService;

public class AppointmentServiceImpl implements AppointmentService {

	@Override
	public Appointment createAppointment(Prospect prospect, Appointment appointment) {
		return addOne(prospect, appointment);
	}

	@Override
	public Prospect createAppointments(Prospect prospect, List<Appointment> appointments) {
		return addList(prospect, appointments);
	}

	private Appointment addOne(Prospect prospect, Appointment appointment) {
		List<Appointment> activeAppointments = getActiveAppointments(prospect);
		if (activeAppointments.size() > 1) {
			throw new RuntimeException("Max number of Appointments is already reached");
		} else {
			if (activeAppointments.isEmpty()) {
				if (!appointment.getType().equals(AppointmentType.SHADOWING)) {
					prospect.setAppointments(new ArrayList<>(Arrays.asList(appointment)));
					return appointment;
				} else {
					throw new RuntimeException("There is no regular appointment to be SHADOW!!");
				}
			} else {
				if (appointment.getType().equals(AppointmentType.SHADOWING)) {
					prospect.getAppointments().add(appointment);
					return appointment;
				} else {
					throw new RuntimeException("Only one active appointment of regular type is alowed");
				}
			}
		}
	}

	private Prospect addList(Prospect prospect, List<Appointment> appointments) {
		List<Appointment> activeAppointments = getActiveAppointments(prospect);
		if (appointments == null || appointments.isEmpty()) {
			throw new RuntimeException("No Appointment to be added");
		}

		if (activeAppointments.size() + appointments.size() > 2) {
			throw new RuntimeException("Max number of Appointments is already reached");
		}

		if (appointments.size() == 1) {
			addOne(prospect, appointments.get(0));
			return prospect;
		} else {
			if (!appointments.get(0).getType().equals(AppointmentType.SHADOWING)) {
				addOne(prospect, appointments.get(0));
				addOne(prospect, appointments.get(1));
				return prospect;
			} else {
				addOne(prospect, appointments.get(1));
				addOne(prospect, appointments.get(0));
				return prospect;
			}
		}
	}

	private List<Appointment> getActiveAppointments(Prospect prospect) {
		List<Appointment> activeAppointmentList = new ArrayList<>();

		if (prospect.getAppointments() != null && !prospect.getAppointments().isEmpty()) {
			prospect.getAppointments().stream().filter(a -> !a.getStatus().equals(AppointmentStatus.CLOSED))
					.forEach(a -> activeAppointmentList.add(a));
		}

		return activeAppointmentList;
	}

}
