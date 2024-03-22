import React, { useState } from "react";

const DeletePatient = ({ onDeleteSuccess, onDeleteError }) => {
  const [patientIdToDelete, setPatientIdToDelete] = useState("");

  const handleDeletePatient = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/patients/${patientIdToDelete}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Delete failed");
        }
      })
      .then((data) => {
        console.log("Delete response:", data);
      })
      .catch((error) => {
        console.error("Error making DELETE request:", error);
      });
  };

  return (
    <div>
      <h2>Delete Patient:</h2>
      <label>Patient ID:</label>
      <input
        type="text"
        value={patientIdToDelete}
        onChange={(e) => setPatientIdToDelete(e.target.value)}
      />
      <button type="button" onClick={handleDeletePatient}>
        Delete Patient
      </button>
    </div>
  );
};

export default DeletePatient;
