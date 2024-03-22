import React, { useState } from "react";

const AddPatient = () => {
  const [newPatient, setNewPatient] = useState({
    cnp: "",
    userId: 0,
    nume: "",
    prenume: "",
    email: "",
    telefon: "",
    dataNasterii: "",
    is_active: true,
  });

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;

    const inputValue = type === "checkbox" ? checked : value;

    setNewPatient((prevPatient) => ({
      ...prevPatient,
      [name]: inputValue,
    }));
  };

  const handleAddPatient = () => {
    console.log("Adding patient:", newPatient);
    const token = sessionStorage.getItem("authToken");

    const patientData = {
      cnp: newPatient.cnp,
      userId: newPatient.userId,
      nume: newPatient.nume,
      prenume: newPatient.prenume,
      email: newPatient.email,
      telefon: newPatient.telefon,
      dataNasterii: new Date(newPatient.dataNasterii).toISOString(),
      is_active: newPatient.is_active,
    };

    fetch("http://127.0.0.1:8000/patients", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,

        "Content-Type": "application/json",
      },
      body: JSON.stringify(patientData),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Response:", data);

        // Reset the form
        // setNewPatient({
        //   cnp: "",
        //   userId: 0,
        //   nume: "",
        //   prenume: "",
        //   email: "",
        //   telefon: "",
        //   dataNasterii: "",
        //   is_active: false,
        // });
      })
      .catch((error) => {
        console.error("Error making POST request:", error);
      });
  };

  return (
    <div>
      <h2>Add Patient:</h2>
      <form>
        <label>CNP:</label>
        <input
          type="text"
          name="cnp"
          value={newPatient.cnp}
          onChange={handleInputChange}
        />
        <br />

        <label>User ID:</label>
        <input
          type="number"
          name="userId"
          value={newPatient.userId}
          onChange={handleInputChange}
        />
        <br />

        <label>Nume:</label>
        <input
          type="text"
          name="nume"
          value={newPatient.nume}
          onChange={handleInputChange}
        />
        <br />

        <label>Prenume:</label>
        <input
          type="text"
          name="prenume"
          value={newPatient.prenume}
          onChange={handleInputChange}
        />
        <br />

        <label>Email:</label>
        <input
          type="email"
          name="email"
          value={newPatient.email}
          onChange={handleInputChange}
        />
        <br />

        <label>Telefon:</label>
        <input
          type="text"
          name="telefon"
          value={newPatient.telefon}
          onChange={handleInputChange}
        />
        <br />

        <label>Data Nasterii:</label>
        <input
          type="date"
          name="dataNasterii"
          value={newPatient.dataNasterii}
          onChange={handleInputChange}
        />
        <br />

        <label>Is Active:</label>
        <input
          type="checkbox"
          name="is_active"
          checked={newPatient.is_active}
          onChange={handleInputChange}
        />
        <br />

        <button type="button" onClick={handleAddPatient}>
          Add Patient
        </button>
      </form>
    </div>
  );
};

export default AddPatient;
