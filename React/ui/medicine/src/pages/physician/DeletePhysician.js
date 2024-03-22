import React, { useState } from "react";

const DeletePhysician = ({ onDeleteSuccess, onDeleteError }) => {
  const [physicianIdToDelete, setPhysicianIdToDelete] = useState("");

  const handleDeletePhysician = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/physicians/${physicianIdToDelete}`, {
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
        console.error("Error DELETE request:", error);
      });
  };

  return (
    <div>
      <h2>Delete Physician:</h2>
      <label>Physician ID:</label>
      <input
        type="number"
        value={physicianIdToDelete}
        onChange={(e) => setPhysicianIdToDelete(e.target.value)}
      />
      <button type="button" onClick={handleDeletePhysician}>
        Delete Physician
      </button>
    </div>
  );
};

export default DeletePhysician;
