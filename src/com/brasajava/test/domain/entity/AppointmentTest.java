package com.brasajava.test.domain.entity;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.brasajava.domain.entity.Appointment;
import com.brasajava.domain.entity.Prospect;
import com.brasajava.domain.enums.AppointmentStatus;
import com.brasajava.domain.enums.AppointmentType;
import com.brasajava.domain.enums.ProspectStatus;
import com.brasajava.domain.service.AppointmentService;
import com.brasajava.domain.service.impl.AppointmentServiceImpl;


class AppointmentTest {

	private static final String OWNER = "owner";
	private static final String OWNER_SHADOW = "shadow_onw";
	private static final String PROSPECT_ID = "123456789";
	private static final String APPOINTMENT_ID = "123456";
	private static final String SHADOW_ID = "654321";

	private static final AppointmentService service = new AppointmentServiceImpl();
	
	@Test
	void id_A_test_A_Prospect_Cannot_Have_More_Then_Two_Active_Appointments() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				Arrays.asList(
						new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null),
						new Appointment(SHADOW_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER_SHADOW, null)
				));
		try {
		service.createAppointment(prospect, appointment);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => A");
			assertEquals(ex.getMessage(), "Max number of Appointments is already reached");
		}
	}
	
	@Test
	void id_B_test_A_Prospect_Cannot_Have_More_Then_One_Active_Appointments_Of_Type_Visit() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				Arrays.asList(
						new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null)
				));
		try {
		service.createAppointment(prospect, appointment);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => B");
			assertEquals(ex.getMessage(), "Only one active appointment of regular type is alowed");
		}
	}

	@Test
	void id_C_test_A_Prospect_Cannot_Have_More_Then_One_Active_Appointments_Of_Type_Install() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				Arrays.asList(
						new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null)
				));
		try {
		service.createAppointment(prospect, appointment);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => C");
			assertEquals(ex.getMessage(), "Only one active appointment of regular type is alowed");
		}
	}
	
	@Test
	void id_D_test_A_Shadowing_Appointment_Cannot_Be_Added_If_There_Is_No_Active_Regular_Appointment_To_Be_Shadow() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		try {
		service.createAppointment(prospect, appointment);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => D");
			assertEquals(ex.getMessage(), "There is no regular appointment to be SHADOW!!");
		}
	}
	
	@Test
	void test_Can_Create_A_Visit_Appointment_On_A_Prospect_With_No_Appointments() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		Appointment result = service.createAppointment(prospect, appointment);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Install_Appointment_On_A_Prospect_With_No_Appointments() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		Appointment result = service.createAppointment(prospect, appointment);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Shadowing_Appointment_On_A_Prospect_With_Only_One_Acive_Appointment_Of_Type_Visit() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				new ArrayList<>(Arrays.asList(new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null)))
		);
		Appointment result = service.createAppointment(prospect, appointment);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Shadowing_Appointment_On_A_Prospect_With_Only_One_Acive_Appointment_Of_Type_Install() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				new ArrayList<>(Arrays.asList(new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null)))
		);
		Appointment result = service.createAppointment(prospect, appointment);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Install_Appointment_On_A_Prospect_With_Only_Inacive_Appointments() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				new ArrayList<>(
						Arrays.asList(
								new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.CLOSED, OWNER, null),
								new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.CLOSED, OWNER, null),
								new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.CLOSED, OWNER, null)
						)
				)
		);
		Appointment result = service.createAppointment(prospect, appointment);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Visit_Appointment_On_A_Prospect_With_Only_Inacive_Appointments() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				new ArrayList<>(
						Arrays.asList(
								new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.CLOSED, OWNER, null),
								new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.CLOSED, OWNER, null),
								new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.CLOSED, OWNER, null)
						)
				)
		);
		Appointment result = service.createAppointment(prospect, appointment);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Shadowing_Appointment_On_A_Prospect_With_One_Regular_Appointment_Acive_And_Others_Inactive_Shadowing_Appointments() {
		Appointment appointment = new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				new ArrayList<>(
						Arrays.asList(
								new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.SCHEDULED, OWNER, null),
								new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.CLOSED, OWNER, null),
								new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.CLOSED, OWNER, null)
						)
				)
		);
		Appointment result = service.createAppointment(prospect, appointment);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Visit_And_A_Shadowing_Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null),
					 new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		Prospect result = service.createAppointments(prospect, appointments);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_An_Install_And_A_Shadowing_Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null),
					 new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		Prospect result = service.createAppointments(prospect, appointments);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_A_Visit__Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT,
				null
		);
		Prospect result = service.createAppointments(prospect, appointments);
		assertNotNull(result);
	}
	
	@Test
	void test_Can_Create_An_Install__Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT,
				null
		);
		Prospect result = service.createAppointments(prospect, appointments);
		assertNotNull(result);
	}
	
	@Test
	void id_E_test_Cannot_Create_Two_Visit_Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null),
					 new Appointment(APPOINTMENT_ID, AppointmentType.VISIT, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		try {
			 service.createAppointments(prospect, appointments);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => E");
			assertEquals(ex.getMessage(), "Only one active appointment of regular type is alowed");
		}
	}
	
	@Test
	void id_F_test_Cannot_Create_Two_Install_Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null),
					 new Appointment(APPOINTMENT_ID, AppointmentType.INSTALL, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		try {
			 service.createAppointments(prospect, appointments);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => F");
			assertEquals(ex.getMessage(), "Only one active appointment of regular type is alowed");
		}
	}
	
	@Test
	void id_G_test_Cannot_Create_Two_Shadowing_Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null),
					 new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		try {
			 service.createAppointments(prospect, appointments);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => G");
			assertEquals(ex.getMessage(), "There is no regular appointment to be SHADOW!!");
		}
	}
	
	@Test
	void id_H_test_Cannot_Create_A_Shadowing_Appointment_On_A_Prospect_With_No_Appointments() {
		List<Appointment> appointments = new ArrayList<>(
				Arrays.asList(
					 new Appointment(APPOINTMENT_ID, AppointmentType.SHADOWING, AppointmentStatus.ASIGNED, OWNER, null)
				)
		);
		Prospect prospect = new Prospect(
				PROSPECT_ID, 
				ProspectStatus.HOT, 
				null
		);
		try {
			 service.createAppointments(prospect, appointments);
		}catch(RuntimeException ex) {
			System.out.println("Catching Exception of test => H");
			assertEquals(ex.getMessage(), "There is no regular appointment to be SHADOW!!");
		}
	}
}
