import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";

const PhysicianPatients = () => {
  const { id } = useParams();

  const [patients, setPatients] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(
          `http://127.0.0.1:8000/physicians/${id}/patients`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 403) {
          setError("Forbidden access");
          return;
        }

        const data = await response.json();
        setPatients(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [id]);

  if (error) {
    return (
      <div>
        <p> {error}</p>
      </div>
    );
  }

  return (
    <div>
      <Link to="/findPatient">Find Patient</Link>
      <br />
      <Link to="/deletePatient">Delete Patient</Link>
      <br />

      <h2>My Patients:</h2>
      {patients.length > 0 ? (
        <ul>
          {patients.map((patient, index) => (
            <li key={index}>
              <p>CNP: {patient.cnp}</p>
              <p>Name: {`${patient.nume} ${patient.prenume}`}</p>
              <p>Email: {patient.email}</p>

              <Link to={`/myAppointments/${patient.cnp}`}>Appointments</Link>
            </li>
          ))}
        </ul>
      ) : (
        <p>No patients available.</p>
      )}
    </div>
  );
};

export default PhysicianPatients;
