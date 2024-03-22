import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";

const PatientAppointments = ({ onFindSuccess, onFindError }) => {
  const { id } = useParams();
  const [foundAppointment, setFoundAppointment] = useState(null);

  useEffect(() => {
    handleAppointments();
  }, []);

  const handleAppointments = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/appointments/${id}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Get appointments failed");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Find response:", data);
        setFoundAppointment(data);
      })
      .catch((error) => {
        console.error("Error GET request:", error);
      });
  };
  return (
    <div>
      <h3>Appointments:</h3>

      {foundAppointment && foundAppointment.length > 0 ? (
        <ul>
          {foundAppointment.map((appointment, index) => (
            <li key={index}>
              <p>{index + 1}</p>
              <p>Data: {appointment.date}</p>
              <p>Status: {appointment.status}</p>
              <Link
                to={`/patientConsultations?id_doctor=${appointment.physicianId}&id_pacient=${appointment.patientId}&date=${appointment.date}`}
              >
                Consultations
              </Link>
              <br />
            </li>
          ))}
        </ul>
      ) : (
        <p>No appointments found.</p>
      )}
    </div>
  );
};

export default PatientAppointments;
