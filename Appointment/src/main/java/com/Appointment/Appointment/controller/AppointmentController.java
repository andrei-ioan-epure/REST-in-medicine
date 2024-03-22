package com.Appointment.Appointment.controller;


import com.Appointment.Appointment.dto.AppointmentDTO;
import com.Appointment.Appointment.service.interfaces.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/medical_office/appointments", headers = "Authorization")
public class AppointmentController {

    private final IAppointmentService appointmentService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<?> getAllAppointments(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "items_per_page", required = false, defaultValue = "10") Integer itemsPerPage,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.appointmentService.validateHeader(authorizationHeader,"patient,physician");

        List<AppointmentDTO> appointments = appointmentService.getAppointments(page, itemsPerPage);
        return new ResponseEntity<>(appointments, HttpStatus.OK);

    }


    @GetMapping("/{cnp}")
    public ResponseEntity<?> getAppointmentsById(@PathVariable String cnp,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        appointmentService.validateHeader(authorizationHeader,"patient,physician");

        List<AppointmentDTO> appointment= appointmentService.getAppointmentsByPatientId(cnp);

        if(appointment!=null)
        {
            return new ResponseEntity<>(appointment,HttpStatus.OK);

        }
        else{
            Map<String, ArrayList<Link>> links= new HashMap<>();
            ArrayList<Link> arrayLinks=new ArrayList<>();

            Link parentLink =linkTo(methodOn(AppointmentController.class).getAllAppointments(1,1,null)).withRel("parent");

            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<>(arrayLinks));
            return  new ResponseEntity<>(links,HttpStatus.NOT_FOUND);


            }
    }
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody AppointmentDTO appointmentDTO ,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        this.appointmentService.validateHeader(authorizationHeader,"patient");
        AppointmentDTO newAppointment = appointmentService.createAppointment(appointmentDTO);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);

    }

    @DeleteMapping("/{cnp}")
    public ResponseEntity<?> deleteAppointment(@PathVariable String cnp,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        appointmentService.validateHeader(authorizationHeader,"patient");
        appointmentService.deleteAppointmentByPatientCNP(cnp);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
