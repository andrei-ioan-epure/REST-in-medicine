import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

const EditInvestigation = () => {
  const { search } = useLocation();
  const params = new URLSearchParams(search);

  const consultationId = params.get("consultationId");
  const investigationId = params.get("id");

  const [editedInvestigation, setEditedInvestigation] = useState({
    id: "",
    denumire: "",
    durateDeProcesare: 0,
    rezultat: "",
  });

  useEffect(() => {
    const fetchConsultation = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(
          `http://127.0.0.1:8000/consultations/${consultationId}/investigations/${investigationId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 403) {
          console.error("Forbidden access");
          return;
        }

        const data = await response.json();
        const { _links, ...editedDataWithoutLinks } = data;

        setEditedInvestigation(editedDataWithoutLinks);
      } catch (error) {
        console.error("Error fetching patient data:", error);
      }
    };

    fetchConsultation();
  }, [investigationId]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedInvestigation((prevInvestigation) => ({
      ...prevInvestigation,
      [name]: value,
    }));
  };

  const handleUpdateConsultation = () => {
    const token = sessionStorage.getItem("authToken");
    console.log(editedInvestigation);

    fetch(
      `http://127.0.0.1:8000/consultations/${consultationId}/investigations/${editedInvestigation.id}`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(editedInvestigation),
      }
    )
      .then((response) => response.json())
      .then((data) => {
        console.log("Update response:", data);
      })
      .catch((error) => {
        console.error("Error PUT request:", error);
      });
  };

  return (
    <div>
      <h2>Edit Investigation:</h2>
      <form>
        <label>Denumire:</label>
        <input
          type="text"
          name="denumire"
          value={editedInvestigation.denumire}
          onChange={handleInputChange}
        />
        <br />

        <label>Durate De Procesare:</label>
        <input
          type="number"
          name="durateDeProcesare"
          value={editedInvestigation.durateDeProcesare}
          onChange={handleInputChange}
        />
        <br />

        <label>Rezultat:</label>
        <input
          type="text"
          name="rezultat"
          value={editedInvestigation.rezultat}
          onChange={handleInputChange}
        />

        <br />
        <button type="button" onClick={handleUpdateConsultation}>
          Update
        </button>
      </form>
    </div>
  );
};

export default EditInvestigation;
