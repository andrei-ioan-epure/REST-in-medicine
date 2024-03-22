import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const AddAppointment = () => {
  const { id } = useParams();
  const [patient, setPatient] = useState([]);
  const [physicians, setPhysicians] = useState([]);

  const [newAppointment, setNewAppointment] = useState({
    patientId: "",
    physicianId: 0,
    date: "",
    status: "neprezentat",
  });

  useEffect(() => {
    const fetchPatient = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(`http://127.0.0.1:8000/patients/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.status === 403) {
          console.error("Forbidden access");
          return;
        }

        const data = await response.json();
        data.dataNasterii = new Date(data.dataNasterii);

        console.log(data);
        setPatient(data);
      } catch (error) {
        console.error("Error fetching patient data:", error);
      }
    };

    const fetchPhysician = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const physiciansResponse = await fetch(
          "http://127.0.0.1:8000/physicians?page=1&items_per_page=10",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const physiciansData = await physiciansResponse.json();

        setPhysicians(physiciansData);
      } catch (error) {
        console.error("Error fetching patient data:", error);
      }
    };

    fetchPatient();
    fetchPhysician();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;

    const inputValue = type === "checkbox" ? checked : value;

    setNewAppointment((prevPatient) => ({
      ...prevPatient,
      [name]: inputValue,
    }));
  };

  const handleAddAppointment = () => {
    const token = sessionStorage.getItem("authToken");

    const appointmentData = {
      patientId: patient.cnp,
      physicianId: newAppointment.physicianId,
      date: new Date(newAppointment.date).toISOString(),
      status: newAppointment.status,
    };
    console.log("Adding appointment:", appointmentData);

    fetch("http://127.0.0.1:8000/appointments", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,

        "Content-Type": "application/json",
      },
      body: JSON.stringify(appointmentData),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Response:", data);

        // Reset the form
        // setNewPatient({
        //   cnp: "",
        //   userId: 0,
        //   nume: "",
        //   prenume: "",
        //   email: "",
        //   telefon: "",
        //   dataNasterii: "",
        //   is_active: false,
        // });
      })
      .catch((error) => {
        console.error("Error making POST request:", error);
      });
  };

  return (
    <div>
      <h2>Add Appointment:</h2>
      <form>
        <label>Physician:</label>
        <select
          name="physicianId"
          value={newAppointment.physicianId}
          onChange={handleInputChange}
        >
          <option value="">Select Physician</option>
          {physicians.map((physician, index) => (
            <option key={index} value={physician.id_doctor}>
              {`${physician.nume} ${physician.prenume}`}
            </option>
          ))}
        </select>
        <br />

        <label>Data:</label>
        <input
          type="date"
          name="date"
          value={newAppointment.date}
          onChange={handleInputChange}
        />
        <br />
        {/* 
        <label>Status:</label>
        <input
          type="text"
          name="status"
          value={newAppointment.status}
          onChange={handleInputChange}
        /> */}

        <br />

        <button type="button" onClick={handleAddAppointment}>
          Add Appointment
        </button>
      </form>
    </div>
  );
};

export default AddAppointment;
