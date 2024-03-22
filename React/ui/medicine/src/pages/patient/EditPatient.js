import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

const EditPatient = () => {
  const { id } = useParams();
  const [editedPatient, setEditedPatient] = useState({
    cnp: "",
    userId: 0,
    nume: "",
    prenume: "",
    email: "",
    telefon: "",
    dataNasterii: "",
    is_active: true,
  });

  useEffect(() => {
    const fetchPatient = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(
          `http://127.0.0.1:8000/patients/users/${id}`,
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

        editedDataWithoutLinks.dataNasterii = new Date(
          editedDataWithoutLinks.dataNasterii
        );

        setEditedPatient(editedDataWithoutLinks);
      } catch (error) {
        console.error("Error fetching patient data:", error);
      }
    };

    fetchPatient();
  }, [id]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedPatient((prevPatient) => ({
      ...prevPatient,
      [name]: value,
    }));
  };

  const handleUpdatePatient = () => {
    const token = sessionStorage.getItem("authToken");

    editedPatient.dataNasterii = new Date(
      editedPatient.dataNasterii
    ).toISOString();
    fetch(`http://127.0.0.1:8000/patients/${editedPatient.cnp}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(editedPatient),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Update response:", data);
      })
      .catch((error) => {
        console.error("Error PUT request:", error);
      });
  };

  const formatDate = (date) => {
    if (isNaN(date.getTime())) {
      return "";
    }

    return date.toISOString().split("T")[0];
  };

  return (
    <div>
      <h2>Edit Patient:</h2>
      <form>
        <label>CNP:</label>
        <input
          type="text"
          name="cnp"
          value={editedPatient.cnp}
          onChange={handleInputChange}
        />
        <br />
        <label>Nume:</label>
        <input
          type="text"
          name="nume"
          value={editedPatient.nume}
          onChange={handleInputChange}
        />
        <br />
        <label>Prenume:</label>
        <input
          type="text"
          name="prenume"
          value={editedPatient.prenume}
          onChange={handleInputChange}
        />
        <br />
        <label>Email:</label>
        <input
          type="email"
          name="email"
          value={editedPatient.email}
          onChange={handleInputChange}
        />

        <br />
        <label>Telefon:</label>
        <input
          type="text"
          name="telefon"
          value={editedPatient.telefon}
          onChange={handleInputChange}
        />
        <br />
        <label>Data Nasterii:</label>
        <input
          type="date"
          name="dataNasterii"
          value={formatDate(new Date(editedPatient.dataNasterii))}
          onChange={handleInputChange}
        />
        <br />
        {/* <label>Is Active:</label>
        <input
          type="checkbox"
          name="is_active"
          checked={editedPatient.is_active}
          onChange={handleInputChange}
        />
        <br /> */}
        <button type="button" onClick={handleUpdatePatient}>
          Update Patient
        </button>
      </form>
    </div>
  );
};

export default EditPatient;
