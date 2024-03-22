package com.Patient.Patient.controller;


import com.Patient.Patient.dto.AppointmentDTO;
import com.Patient.Patient.dto.PatientDTO;
import com.Patient.Patient.dto.PhysicianDTO;
import com.Patient.Patient.hateoas.PatientHateoas;
import com.Patient.Patient.service.interfaces.IPatientService;
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
@RequestMapping(value = "/api/medical_office/patients", headers = "Authorization")
public class PatientController {

    private final IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

//    @GetMapping
//    public ResponseEntity<List<PatientDTO>> getAllPatients(){
//        List<PatientDTO> patients=patientService.getAllPatients();
//        return new ResponseEntity<>(patients, HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<?> getAllPatients(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "items_per_page", required = false, defaultValue = "10") Integer itemsPerPage,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader

    ) {

            patientService.validateHeader(authorizationHeader,"admin");
            List<PatientDTO> patients = patientService.getAllPatientsPage(page, itemsPerPage);
            return new ResponseEntity<>(patients, HttpStatus.OK);

    }

    @GetMapping("/{cnp}")
    public ResponseEntity<?> getPatientByCNP(@PathVariable String cnp,
                                             @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        patientService.validateHeader(authorizationHeader,"admin,physician,patient");

        PatientDTO patient=patientService.getPatientByCNP(cnp);

        if(patient!=null)
        {
            return new ResponseEntity<>(new PatientHateoas().toModel(patient),HttpStatus.OK);
        }
        else{
            Map<String, ArrayList<Link>> links= new HashMap<>();
            ArrayList<Link> arrayLinks=new ArrayList<>();
            Link parentLink =linkTo(methodOn(PatientController.class).getAllPatients(1,1,null)).withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<>(arrayLinks));
            return  new ResponseEntity<>(links,HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getPatientByUserId(@PathVariable Integer user_id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        patientService.validateHeader(authorizationHeader,"admin,physician,patient");


        PatientDTO patient=patientService.getPatientByUserId(user_id);

        if(patient!=null)
        {
            return new ResponseEntity<>(new PatientHateoas().toModel(patient),HttpStatus.OK);
        }
        else{
            Map<String, ArrayList<Link>> links= new HashMap<>();
            ArrayList<Link> arrayLinks=new ArrayList<>();
            Link parentLink =linkTo(methodOn(PatientController.class).getAllPatients(1,1,null)).withRel("parent");
            arrayLinks.add(parentLink);
            links.put("_links", new ArrayList<>(arrayLinks));
            return  new ResponseEntity<>(links,HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping
    public ResponseEntity<?> createPatient(@RequestBody PatientDTO patient)
    {
        PatientDTO newPatient = patientService.createPatient(patient);
            return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{cnp}")
    public ResponseEntity<?> updatePatientByCNP(@PathVariable String cnp, @RequestBody PatientDTO patient, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        patientService.validateHeader(authorizationHeader,"admin,patient");

        if (patientService.getPatientByCNP(cnp) != null) {
                PatientDTO updatedPatient = patientService.updatePatient(cnp, patient);
                return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
            } else {
                patientService.createPatient(patient);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
    }


    @DeleteMapping("/{cnp}")
    public ResponseEntity<?> deletePatient(@PathVariable String cnp, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        patientService.validateHeader(authorizationHeader,"admin,patient");

        patientService.deletePatientByCNP(cnp);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{cnp}/physicians")
    public ResponseEntity<?> getPhysiciansForPatient(@PathVariable String cnp, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader)
    {
        patientService.validateHeader(authorizationHeader,"admin,patient");

        List<PhysicianDTO> physicians = patientService.getPhysiciansForPatient(cnp);
        return (physicians!=null)?  new ResponseEntity<>(physicians, HttpStatus.OK)
                :new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{cnp}/appointments")
    public ResponseEntity<?> getAppointmentsForPatient(@PathVariable String cnp,
                                                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        patientService.validateHeader(authorizationHeader,"patient");

        List<AppointmentDTO> appointments=patientService.getAppointmentsForPatient(cnp);
            return (appointments!=null)?  new ResponseEntity<>(appointments, HttpStatus.OK)
                    :new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{cnp}/appointments/details")
    public ResponseEntity<?> getAppointmentsForPatient(
            @PathVariable("cnp") String cnp,
            @RequestParam(name = "date") String date,
            @RequestParam(name = "type", required = false) String type,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

            patientService.validateHeader(authorizationHeader,"patient");

            List<AppointmentDTO> appointment = patientService.getAppointmentsForPatient(cnp, date, type);
            return (appointment!=null)?  new ResponseEntity<>(appointment, HttpStatus.OK)
                    :new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
}