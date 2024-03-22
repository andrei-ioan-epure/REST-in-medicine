import React, { useState } from "react";

const FindPatient = ({ onFindSuccess, onFindError }) => {
  const [patientIdToFind, setPatientIdToFind] = useState("");
  const [foundPatient, setFoundPatient] = useState(null);

  const handleFindPatient = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/patients/${patientIdToFind}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Get by id failed");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Find response:", data);
        setFoundPatient(data);
      })
      .catch((error) => {
        console.error("Error GET request:", error);
      });
  };

  return (
    <div>
      <h2>Find Patient:</h2>
      <label>Patient CNP:</label>
      <input
        type="text"
        value={patientIdToFind}
        onChange={(e) => setPatientIdToFind(e.target.value)}
      />
      <button type="button" onClick={handleFindPatient}>
        Find
      </button>
      {foundPatient && (
        <div>
          <h3>Found Patient:</h3>
          <pre>{JSON.stringify(foundPatient, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default FindPatient;
