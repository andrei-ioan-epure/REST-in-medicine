import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const Consultations = () => {
  const [consulatations, setNewConsultation] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch("http://127.0.0.1:8000/consultations", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (response.status === 403) {
          setError("Forbidden access");
          return;
        }

        const data = await response.json();
        setNewConsultation(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  if (error) {
    return (
      <div>
        <p> {error}</p>
      </div>
    );
  }
  return (
    <div>
      <Link to="/addConsultation">Add Consultation</Link>
      <br />
      <Link to="/findConsultations">Find Consultation</Link>
      <br />
      <Link to="/editConsultation">Edit Consultation</Link>
      <br />
      <Link to="/deleteConsultation">Delete Consultation</Link>
      <br />
      <Link to="/investigations">Investigations</Link>
      <br />

      <h2>Consultation Data:</h2>
      {consulatations.length > 0 ? (
        <ul>
          {consulatations.map((consulatation, index) => (
            <li key={index}>
              <p>id: {consulatation.id}</p>

              <p>id_pacient: {consulatation.id_pacient}</p>
              <p>id_doctor: {consulatation.id_doctor}</p>
              <p>data: {consulatation.data}</p>
              <p>diagnostic: {consulatation.diagnostic}</p>
              {/* <p>investigatii: {consulatation.investigatii}</p> */}

              <Link
                to={`/editInvestigation/?$id_pacient=${consulatation.id_pacient}&id_doctor=${consulatation.id_doctor}`}
              >
                Edit
              </Link>
            </li>
          ))}
        </ul>
      ) : (
        <p>No consultations available.</p>
      )}
    </div>
  );
};

export default Consultations;
