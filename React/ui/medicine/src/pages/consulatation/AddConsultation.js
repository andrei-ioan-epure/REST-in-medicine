import React, { useState } from "react";
import { useLocation, Link } from "react-router-dom";

const AddConsultation = () => {
  const { search } = useLocation();
  const params = new URLSearchParams(search);
  const date = params.get("date");

  const id_doctor = params.get("id_doctor");
  const id_pacient = params.get("id_pacient");
  const [newConsultation, setNewConsultation] = useState({
    id_pacient: "",
    id_doctor: 0,
    data: "",
    diagnostic: "",
    investigatii: [],
  });

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;

    const inputValue = type === "checkbox" ? checked : value;

    setNewConsultation((prevPatient) => ({
      ...prevPatient,
      [name]: inputValue,
    }));
  };

  const handleAddConsultation = () => {
    console.log("Adding consultation:", newConsultation);
    const token = sessionStorage.getItem("authToken");

    const consultationData = {
      id_pacient: id_pacient,
      id_doctor: id_doctor,
      data: new Date(date).toISOString(),
      diagnostic: newConsultation.diagnostic,
      investigatii: newConsultation.investigatii,
    };
    console.log(JSON.stringify(consultationData));
    fetch("http://127.0.0.1:8000/consultations", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(consultationData),
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
      <h2>Add Consultation:</h2>
      <form>
        <label>Diagnostic:</label>
        <select
          name="diagnostic"
          value={newConsultation.diagnostic}
          onChange={handleInputChange}
        >
          <option value="sanatos">Sanatos</option>
          <option value="bolnav">Bolnav</option>
        </select>

        <br />

        <button type="button" onClick={handleAddConsultation}>
          Add
        </button>
      </form>
    </div>
  );
};

export default AddConsultation;
