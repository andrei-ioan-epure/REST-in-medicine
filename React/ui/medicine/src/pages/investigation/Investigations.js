import React, { useState, useEffect } from "react";
import { Link, useParams } from "react-router-dom";

const Investigations = () => {
  const { consultationId } = useParams();

  const [investigations, setNewInvestigations] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(
          `http://127.0.0.1:8000/consultations/${consultationId}/investigations`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 403) {
          setError("Forbidden access");
          return;
        }

        const data = await response.json();
        setNewInvestigations(data);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);

  const handleDeleteInvestigation = (id) => {
    const token = sessionStorage.getItem("authToken");

    fetch(
      `http://127.0.0.1:8000/consultations/${consultationId}/investigations/${id}`,
      {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Delete failed");
        }
      })
      .then((data) => {
        console.log("Delete response:", data);
        window.location.reload();
      })
      .catch((error) => {
        console.error("Error making DELETE request:", error);
      });
  };

  if (error) {
    return (
      <div>
        <p> {error}</p>
      </div>
    );
  }
  return (
    <div>
      <Link to={`/addInvestigation?consultationId=${consultationId}`}>
        Add Investigation
      </Link>

      <br />

      <h2>Investigation Data:</h2>
      {investigations.length > 0 ? (
        <ul>
          {investigations.map((investigation, index) => (
            <li key={index}>
              <p>Denumire: {investigation.denumire}</p>
              <p>Durate de Procesare: {investigation.durateDeProcesare}</p>
              <p>Rezultat: {investigation.rezultat}</p>

              <Link
                to={`/editInvestigation?consultationId=${consultationId}&id=${investigation.id}`}
              >
                Edit
              </Link>
              <br />
              <button
                type="button"
                onClick={() => handleDeleteInvestigation(investigation.id)}
              >
                Delete
              </button>
            </li>
          ))}
        </ul>
      ) : (
        <p>No investigations available.</p>
      )}
    </div>
  );
};

export default Investigations;
