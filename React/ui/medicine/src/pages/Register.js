import React, { useState } from "react";

const Register = ({ onLogin }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("patient");

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

  const validateFields = () => {
    for (const key in newPatient) {
      if (
        newPatient[key] === null ||
        newPatient[key] === undefined ||
        newPatient[key] === ""
      ) {
        throw new Error(`Field '${key}' empty or null.`);
      }
    }
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(newPatient.email)) {
      throw new Error("Invalid email format");
    }
    const phonePattern = /^0?7[0-9]{8}$/;
    if (!phonePattern.test(newPatient.telefon)) {
      throw new Error("Invalid phone number");
    }
  };

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;

    const inputValue = type === "checkbox" ? checked : value;

    setNewPatient((prevPatient) => ({
      ...prevPatient,
      [name]: inputValue,
    }));
  };

  const handleRegister = async () => {
    try {
      validateFields();

      const response = await fetch("http://127.0.0.1:8000/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username,
          password,
          role,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        console.log(data);
        const patientData = {
          cnp: newPatient.cnp,
          userId: data.id,
          nume: newPatient.nume,
          prenume: newPatient.prenume,
          email: newPatient.email,
          telefon: newPatient.telefon,
          dataNasterii: new Date(newPatient.dataNasterii).toISOString(),
          is_active: newPatient.is_active,
        };
        console.log(patientData);
        const responsePatient = await fetch("http://127.0.0.1:8000/patients", {
          method: "POST",
          headers: {
            Authorization: `Bearer ""`,

            "Content-Type": "application/json",
          },
          body: JSON.stringify(patientData),
        });

        const authToken = data.token;

        sessionStorage.setItem("authToken", authToken);
      } else {
        console.error("Authentication failed");
      }
    } catch (error) {
      console.error("Error during authentication:", error);
    }
  };

  return (
    <div>
      <h2>Register</h2>
      <form>
        {/* //  onSubmit={handleRegister}> */}
        <label>
          Username:
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </label>
        <br />
        <label>
          Password:
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>
        <br />
        {/* <label>Role:</label>
        <select
          name="role"
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          <option value="patient">Pacient</option>
          <option value="physician">Doctor</option>
        </select>
        <br /> */}

        <br />
        <br />
        <label>CNP:</label>
        <input
          type="text"
          name="cnp"
          pattern="^\d{13}$"
          value={newPatient.cnp}
          required
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
          type="tel"
          name="telefon"
          value={newPatient.telefon}
          onChange={handleInputChange}
          required
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
        <button type="button" onClick={handleRegister}>
          Register
        </button>
      </form>
    </div>
  );
};

export default Register;
