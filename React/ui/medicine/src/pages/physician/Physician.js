import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const Physicians = () => {
  const [physicians, setPhysicians] = useState([]);
  const [catUrl, setCatUrl] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("authToken");

        const response = await fetch(
          "http://127.0.0.1:8000/physicians?page=1&items_per_page=10",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        const newCatUrl = `https://http.cat/${response.status}.jpg`;
        setCatUrl(newCatUrl);
        console.log("Cat url: " + newCatUrl);
        if (response.status === 403) {
          setError("Forbidden access");
          return;
        }
        const data = await response.json();
        setPhysicians(data);
      } catch (error) {
        setCatUrl(`https://http.cat/${error.status}.jpg`);
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, []);
  if (error) {
    return (
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <div>
          <p> {error}</p>
        </div>
        <div>
          {catUrl && <img src={catUrl} alt="cat" />}
          {!catUrl && <p>No cat image available.</p>}
        </div>
      </div>
    );
  }
  return (
    <div style={{ display: "flex", justifyContent: "space-between" }}>
      <div>
        <Link to="/addPhysician">Add Physician</Link>
        <br />
        <Link to="/deletePhysician">Delete Physician</Link>
        <br />
        <Link to="/consultations">Consultations</Link>
        <h2>Physicians Data:</h2>
        {physicians.length > 0 ? (
          <ul>
            {physicians.map((physician, index) => (
              <li key={index}>
                <p>Id: {physician.id_doctor}</p>
                <p>Name: {`${physician.nume} ${physician.prenume}`}</p>
                <p>Email: {physician.email}</p>
                <p>Specializare: {physician.specializare}</p>

                <Link to={`/editPhysician/${physician.id_doctor}`}>Edit</Link>
              </li>
            ))}
          </ul>
        ) : (
          <p>No physicians available.</p>
        )}
      </div>
      <div>
        {catUrl && <img src={catUrl} alt="cat" width={600} height={600} />}
        {!catUrl && <p>No cat image available</p>}
      </div>
    </div>
  );
};

export default Physicians;
