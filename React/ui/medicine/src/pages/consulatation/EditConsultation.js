import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const EditConsultation = () => {
  const { id } = useParams();
  const [editedConsultation, setEditedConsultation] = useState({
    id_pacient: "",
    id_doctor: 0,
    data: "",
    diagnostic: "",
    investigatii: [],
  });

  useEffect(() => {
    const fetchConsultation = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(
          `http://127.0.0.1:8000/consultations/${id}`,
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

        setEditedConsultation(editedDataWithoutLinks);
      } catch (error) {
        console.error("Error fetching patient data:", error);
      }
    };

    fetchConsultation();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedConsultation((prevConsultation) => ({
      ...prevConsultation,
      [name]: value,
    }));
  };

  const handleUpdateConsultation = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/consultations/${editedConsultation.id}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(editedConsultation),
    })
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
      <h2>Edit Consultations:</h2>
      <form>
        <label>Diagnostic:</label>

        <select
          name="diagnostic"
          value={editedConsultation.diagnostic}
          onChange={handleInputChange}
        >
          <option value="sanatos">Sanatos</option>
          <option value="bolnav">Bolnav</option>
        </select>

        <br />
        <button type="button" onClick={handleUpdateConsultation}>
          Update
        </button>
      </form>
    </div>
  );
};

export default EditConsultation;
