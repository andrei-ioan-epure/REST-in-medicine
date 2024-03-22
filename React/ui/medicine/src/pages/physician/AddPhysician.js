import React, { useState } from "react";

const AddPhysician = () => {
  const [newPhysician, setNewPhysician] = useState({
    id_doctor: 0,
    id_user: 0,
    nume: "",
    prenume: "",
    email: "",
    telefon: "",
    specializare: "",
  });

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;

    const inputValue = type === "checkbox" ? checked : value;

    setNewPhysician((prevPhysician) => ({
      ...prevPhysician,
      [name]: inputValue,
    }));
  };

  const handleAddPhysician = () => {
    console.log("Adding physician:", newPhysician);
    const token = sessionStorage.getItem("authToken");

    const physicianData = {
      userId: newPhysician.id_user,
      nume: newPhysician.nume,
      prenume: newPhysician.prenume,
      email: newPhysician.email,
      telefon: newPhysician.telefon,
      specializare: newPhysician.specializare,
    };

    fetch("http://127.0.0.1:8000/physicians", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(physicianData),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Response:", data);

        // Reset the form
        // setNewPhysician({
        //   userId: newPhysician.id_user,
        //   nume: newPhysician.nume,
        //   prenume: newPhysician.prenume,
        //   email: newPhysician.email,
        //   telefon: newPhysician.telefon,
        //   specializare: newPhysician.specializare,
        // });
      })
      .catch((error) => {
        console.error("Error POST request:", error);
      });
  };

  return (
    <div>
      <h2>Add Physician:</h2>
      <form>
        <label>User ID:</label>
        <input
          type="number"
          name="id_user"
          value={newPhysician.id_user}
          onChange={handleInputChange}
        />
        <br />

        <label>Nume:</label>
        <input
          type="text"
          name="nume"
          value={newPhysician.nume}
          onChange={handleInputChange}
        />
        <br />

        <label>Prenume:</label>
        <input
          type="text"
          name="prenume"
          value={newPhysician.prenume}
          onChange={handleInputChange}
        />
        <br />

        <label>Email:</label>
        <input
          type="email"
          name="email"
          value={newPhysician.email}
          onChange={handleInputChange}
        />
        <br />

        <label>Telefon:</label>
        <input
          type="text"
          name="telefon"
          value={newPhysician.telefon}
          onChange={handleInputChange}
        />
        <br />

        <label>Specializare:</label>
        <input
          type="text"
          name="specializare"
          checked={newPhysician.specializare}
          onChange={handleInputChange}
        />
        <br />

        <button type="button" onClick={handleAddPhysician}>
          Add Physician
        </button>
      </form>
    </div>
  );
};

export default AddPhysician;
