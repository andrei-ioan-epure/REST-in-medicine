import React, { useState } from "react";

const FindAppointments = ({ onFindSuccess, onFindError }) => {
  const [patientId, setPatientId] = useState("");
  const [foundAppoinment, setFoundAppoinment] = useState(null);

  const handleAppoinmtents = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/appointments/${patientId}`, {
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
        setFoundAppoinment(data);
      })
      .catch((error) => {
        console.error("Error GET request:", error);
      });
  };

  return (
    <div>
      <h2>Find appointments:</h2>
      <label>Patient CNP:</label>
      <input
        type="text"
        value={patientId}
        onChange={(e) => setPatientId(e.target.value)}
      />
      <button type="button" onClick={handleAppoinmtents}>
        Find
      </button>
      {foundAppoinment && (
        <div>
          <h3>Appointments:</h3>
          <pre>{JSON.stringify(foundAppoinment, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default FindAppointments;
