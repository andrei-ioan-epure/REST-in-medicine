import React, { useState } from "react";

const DeleteInvestigation = ({ onDeleteSuccess, onDeleteError }) => {
  const [investigationIdToDelete, setInvestigationIdToDelete] = useState("");

  const handleDeleteInvestigation = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(
      `http://127.0.0.1:8000/consultations/investigation/${DeleteInvestigation}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
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
      <h2>Delete Investigation:</h2>
      <label>Investigation ID:</label>
      <input
        type="text"
        value={investigationIdToDelete}
        onChange={(e) => setInvestigationIdToDelete(e.target.value)}
      />
      <button type="button" onClick={handleDeleteInvestigation}>
        Delete
      </button>
    </div>
  );
};

export default DeleteInvestigation;
