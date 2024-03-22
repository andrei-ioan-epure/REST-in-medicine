import React, { useState, useEffect } from "react";
import { useLocation, Link } from "react-router-dom";

const PatientConsultations = ({ onFindSuccess, onFindError }) => {
  const { search } = useLocation();
  const params = new URLSearchParams(search);

  const id_doctor = params.get("id_doctor");
  const id_pacient = params.get("id_pacient");
  const date = params.get("date");
  const [foundConsultations, setfoundConsultations] = useState(null);
  const [info, setInfo] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    handleConsultations();
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = sessionStorage.getItem("authToken");
        console.log("Token" + token);
        const validateTokenResponse = await fetch(
          "http://127.0.0.1:8000/validate",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ token: token }),
          }
        );
        const userInfo = await validateTokenResponse.json();
        setInfo(userInfo);
      } catch (error) {
        setError("Forbidden access");

        console.error("Error fetching data:", error);
      }
    };
    fetchData();
  }, []);
  const handleConsultations = () => {
    const token = sessionStorage.getItem("authToken");

    fetch(
      `http://127.0.0.1:8000/consultations/appointments?id_pacient=${id_pacient}&id_doctor=${id_doctor}&date=${date}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Get consultations failed");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Find response:", data);
        setfoundConsultations(data);
      })
      .catch((error) => {
        console.error("Error GET request:", error);
      });
  };
  return (
    <div>
      <div>
        {info.role === "physician" && (
          <Link
            to={`/addConsultation?id_doctor=${id_doctor}&id_pacient=${id_pacient}&date=${date}`}
          >
            Add Consultation
          </Link>
        )}
        <h3> Consultations:</h3>
        {foundConsultations && foundConsultations.length > 0 ? (
          <ul>
            {foundConsultations.map((consultation, index) => (
              <li key={index}>
                <p>{index + 1}</p>
                <p>Data: {consultation.data}</p>
                <p>Rezultat: {consultation.diagnostic}</p>
                <Link to={`/investigations/${consultation.id}`}>
                  Investigations
                </Link>
                <br />
                {info.role === "physician" && (
                  <Link to={`/editConsultation/${consultation.id}`}>Edit</Link>
                )}
                <br />
              </li>
            ))}
          </ul>
        ) : (
          <p>No consultations found.</p>
        )}
      </div>
    </div>
  );
};

export default PatientConsultations;
