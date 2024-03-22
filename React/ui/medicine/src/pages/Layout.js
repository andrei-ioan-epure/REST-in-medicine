import React from "react";
import { Link, Outlet } from "react-router-dom";

const Layout = () => {
  const handleLogout = () => {
    const data = { token: sessionStorage.getItem("authToken") };

    fetch("http://127.0.0.1:8000/destroyToken", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    sessionStorage.removeItem("authToken");
  };

  return (
    <>
      <nav>
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/login">Login</Link>
          </li>
          <li>
            <Link to="/register">Register</Link>
          </li>
          <li>
            <Link to="/user">My data</Link>
          </li>
          <li>
            <Link to="/allPatients">All Patients</Link>
          </li>
          <li>
            <Link to="/physicians">Physicians</Link>
          </li>

          {sessionStorage.getItem("authToken") && (
            <li>
              <Link to="/" onClick={handleLogout}>
                Logout
              </Link>
            </li>
          )}
        </ul>
      </nav>

      <Outlet />
    </>
  );
};

export default Layout;
