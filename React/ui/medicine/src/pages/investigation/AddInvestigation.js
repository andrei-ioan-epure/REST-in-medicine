import React, { useState } from "react";
import { useLocation } from "react-router-dom";

const AddInvestigation = () => {
  const { search } = useLocation();
  const params = new URLSearchParams(search);

  const consultationId = params.get("consultationId");

  const [newInvestigation, setNewCInvestigation] = useState({
    id: "",
    denumire: "",
    durateDeProcesare: 0,
    rezultat: "",
  });

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;

    const inputValue = type === "checkbox" ? checked : value;

    setNewCInvestigation((prevPatient) => ({
      ...prevPatient,
      [name]: inputValue,
    }));
  };

  const handleAddConsultation = () => {
    console.log("Adding investigation:", newInvestigation);
    const token = sessionStorage.getItem("authToken");

    const investigationData = {
      id: newInvestigation.id,
      denumire: newInvestigation.denumire,
      durateDeProcesare: newInvestigation.durateDeProcesare,
      rezultat: newInvestigation.rezultat,
    };
    fetch(
      `http://127.0.0.1:8000/consultations/${consultationId}/investigations`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(investigationData),
      }
    )
      .then((response) => response.json())
      .then((data) => {
        console.log("Response:", data);
      })
      .catch((error) => {
        console.error("Error making POST request:", error);
      });
  };

  return (
    <div>
      <h2>Add Investigation:</h2>
      <form>
        <label>Denumire:</label>
        <input
          type="text"
          name="denumire"
          value={newInvestigation.denumire}
          onChange={handleInputChange}
        />
        <br />

        <label>Durate De Procesare:</label>
        <input
          type="number"
          name="durateDeProcesare"
          value={newInvestigation.durateDeProcesare}
          onChange={handleInputChange}
        />
        <br />

        <label>Rezultat:</label>
        <input
          type="text"
          name="rezultat"
          value={newInvestigation.rezultat}
          onChange={handleInputChange}
        />

        <br />

        <button type="button" onClick={handleAddConsultation}>
          Add
        </button>
      </form>
    </div>
  );
};

export default AddInvestigation;
