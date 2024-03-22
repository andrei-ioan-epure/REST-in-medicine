import React, { useState } from "react";

const FindConsultation = ({ onFindSuccess, onFindError }) => {
  const [consultationIdToFind, setConsultationIdToFind] = useState("");
  const [foundConsultation, setConsultationPatient] = useState(null);

  const handleFindConsultation = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(`http://127.0.0.1:8000/consultations/${consultationIdToFind}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Get by id failed");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Find response:", data);
        setConsultationPatient(data);
      })
      .catch((error) => {
        console.error("Error GET request:", error);
      });
  };

  return (
    <div>
      <h2>Find Consultation:</h2>
      <label>Consultation Id:</label>
      <input
        type="text"
        value={consultationIdToFind}
        onChange={(e) => setConsultationIdToFind(e.target.value)}
      />
      <button type="button" onClick={handleFindConsultation}>
        Find
      </button>
      {foundConsultation && (
        <div>
          <h3>Found Consultation:</h3>
          <pre>{JSON.stringify(foundConsultation, null, 2)}</pre>
        </div>
      )}
    </div>
  );
};

export default FindConsultation;
