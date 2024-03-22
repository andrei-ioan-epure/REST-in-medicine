import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
// import jwt from "jsonwebtoken";

const AllPatients = () => {
  const [patients, setPatients] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        // const userId = decodedToken.payload.sub;
        // console.log("User id ", userId);
        const response = await fetch(
          "http://127.0.0.1:8000/patients?page=1&items_per_page=10",
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
  }, []);

  if (error) {
    return (
      <div>
        <p> {error}</p>
      </div>
    );
  }

  return (
    <div>
      <Link to="/addPatient">Add Patient</Link>
      <br />
      <Link to="/findAppointments">Find Appointments</Link>
      <br />
      <Link to="/findPatient">Find Patient</Link>
      <br />
      <Link to="/deletePatient">Delete Patient</Link>
      <br />

      <h2>Patients Data:</h2>
      {patients.length > 0 ? (
        <ul>
          {patients.map((patient, index) => (
            <li key={index}>
              <p>CNP: {patient.cnp}</p>
              <p>Name: {`${patient.nume} ${patient.prenume}`}</p>
              <p>Email: {patient.email}</p>
              {/* <Link to={`/editPatient/${patient.userId}`}>Edit</Link> */}
            </li>
          ))}
        </ul>
      ) : (
        <p>No patients available.</p>
      )}
    </div>
  );
};

export default AllPatients;
