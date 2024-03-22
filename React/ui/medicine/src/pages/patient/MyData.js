import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const MyData = () => {
  const [user, setUser] = useState([]);
  const [info, setInfo] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("authToken");
        console.log("Token" + token);
        const validateTokenResponse = await fetch(
          "http://127.0.0.1:8000/validate",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ token: token }),
          }
        );
        const userInfo = await validateTokenResponse.json();
        setInfo(userInfo);
        // const userId = decodedToken.payload.sub;
        // console.log("User id ", userId);
        // const response = await fetch(
        //   "http://127.0.0.1:8000/patients?page=1&items_per_page=10",
        //   {
        //     headers: {
        //       Authorization: `Bearer ${token}`,
        //     },
        //   }
        // );
        let response;
        console.log(userInfo.role);
        if (userInfo.role === "patient") {
          response = await fetch(
            `http://127.0.0.1:8000/patients/users/${userInfo.sub}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
        } else {
          response = await fetch(
            `http://127.0.0.1:8000/physicians/users/${userInfo.sub}`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
        }
        if (response.status === 403) {
          setError("Forbidden access");
          return;
        }

        const data = await response.json();
        setUser(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  if (error) {
    return (
      <div>
        <Link to="/findAppointments">Find Appointments</Link>
        <br />
        <Link to="/addAppointment">Add Appointment</Link>
        <br />

        <p> {error}</p>
      </div>
    );
  }

  return (
    <div>
      {user !== null && info.role === "admin" && (
        <>
          <Link to="/addPatient">Add Patient</Link>
          <br />
          <Link to="/findPatient">Find Patient</Link>
          <br />
          <Link to="/deletePatient">Delete Patient</Link>
          <br />
        </>
      )}

      <div>
        {user !== null && info.role === "patient" ? (
          <>
            <h2>My Patient Data:</h2>
            {user != null ? (
              <ul>
                <li>
                  <p>CNP: {user.cnp}</p>
                </li>
                <li>
                  <p>Name: {`${user.nume} ${user.prenume}`}</p>
                </li>
                <li>
                  <p>Email: {user.email}</p>
                </li>
                <li>
                  <Link to={`/editPatient/${user.userId}`}>Edit</Link>
                </li>
                <li>
                  <Link to={`/addAppointment/${user.cnp}`}>
                    Add Appointment
                  </Link>
                </li>
                <li>
                  <Link to={`/myAppointments/${user.cnp}`}>
                    My Appointments
                  </Link>
                </li>
              </ul>
            ) : (
              <p>Patient data is not available</p>
            )}
          </>
        ) : user !== null && info.role === "physician" ? (
          <>
            <h2>My Physician Data:</h2>
            {user != null ? (
              <ul>
                <li>
                  <p>Id: {user.id_doctor}</p>
                </li>
                <li>
                  <p>Name: {`${user.nume} ${user.prenume}`}</p>
                </li>
                <li>
                  <p>Email: {user.email}</p>
                </li>
                <li>
                  <p>Specializare: {user.specializare}</p>
                </li>
                <li>
                  <Link to={`/editPhysician/${user.id_doctor}`}>Edit</Link>
                </li>
                <li>
                  <Link to={`/physicianPatients/${user.id_doctor}`}>
                    My Patients
                  </Link>
                </li>
              </ul>
            ) : (
              <p>Physician data is not available</p>
            )}
          </>
        ) : (
          <p>Profile data is not available</p>
        )}
      </div>
    </div>
  );
};

export default MyData;
