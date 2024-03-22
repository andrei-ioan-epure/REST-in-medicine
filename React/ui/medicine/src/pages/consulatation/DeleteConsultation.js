import React, { useState } from "react";

const DeleteConsultation = ({ onDeleteSuccess, onDeleteError }) => {
  const [consultationIdToDelete, setConsultationIdToDelete] = useState("");

  const handleDeleteConsultation = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/consultations/${consultationIdToDelete}`, {
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
      <h2>Delete Consultation:</h2>
      <label>Consultation ID:</label>
      <input
        type="text"
        value={consultationIdToDelete}
        onChange={(e) => setConsultationIdToDelete(e.target.value)}
      />
      <button type="button" onClick={handleDeleteConsultation}>
        Delete
      </button>
    </div>
  );
};

export default DeleteConsultation;
