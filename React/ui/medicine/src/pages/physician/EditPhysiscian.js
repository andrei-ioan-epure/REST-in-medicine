import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const EditPhysician = () => {
  const { id } = useParams();
  const [editedPhysician, setEditedPhysician] = useState({
    id_doctor: 0,
    id_user: 0,
    nume: "",
    prenume: "",
    email: "",
    telefon: "",
    specializare: "",
  });

  useEffect(() => {
    const fetchPatient = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(`http://127.0.0.1:8000/physicians/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.status === 403) {
          console.error("Forbidden access");
          return;
        }

        const data = await response.json();
        data.dataNasterii = new Date(data.dataNasterii);

        setEditedPhysician(data);
      } catch (error) {
        console.error("Error fetching patient data:", error);
      }
    };

    fetchPatient();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedPhysician((prevPatient) => ({
      ...prevPatient,
      [name]: value,
    }));
  };

  const handleUpdatePatient = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/physicians/${editedPhysician.id_doctor}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(editedPhysician),
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
      <h2>Edit Physician:</h2>
      <form>
        <label>Nume:</label>
        <input
          type="text"
          name="nume"
          value={editedPhysician.nume}
          onChange={handleInputChange}
        />
        <br />

        <label>Prenume:</label>
        <input
          type="text"
          name="prenume"
          value={editedPhysician.prenume}
          onChange={handleInputChange}
        />
        <br />

        <label>Email:</label>
        <input
          type="email"
          name="email"
          value={editedPhysician.email}
          onChange={handleInputChange}
        />
        <br />

        <label>Telefon:</label>
        <input
          type="text"
          name="telefon"
          value={editedPhysician.telefon}
          onChange={handleInputChange}
        />
        <br />

        <label>Specializare:</label>
        <input
          type="text"
          name="specializare"
          value={editedPhysician.specializare}
          onChange={handleInputChange}
        />
        <br />

        <button type="button" onClick={handleUpdatePatient}>
          Update Physicians
        </button>
      </form>
    </div>
  );
};

export default EditPhysician;
