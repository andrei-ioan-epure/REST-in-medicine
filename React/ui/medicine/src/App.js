import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./pages/Layout";
import Home from "./pages/Home";
import Login from "./pages/Login";
import NoPage from "./pages/NoPage";
import AddPatient from "./pages/patient/AddPatient";
import DeletePatient from "./pages/patient/DeletePatient";
import EditPatient from "./pages/patient/EditPatient";
import AddPhysician from "./pages/physician/AddPhysician";
import Physicians from "./pages/physician/Physician";
import DeletePhysician from "./pages/physician/DeletePhysician";
import FindPatient from "./pages/patient/FindPatientByCNP";
import FindAppointments from "./pages/patient/HistoryPatient";
import AddAppointment from "./pages/patient/AddAppointment";
import AddConsultation from "./pages/consulatation/AddConsultation";
import Consultations from "./pages/consulatation/Consultations";
import Register from "./pages/Register";
import DeleteConsultation from "./pages/consulatation/DeleteConsultation";
import Investigations from "./pages/investigation/Investigations";
import AddInvestigation from "./pages/investigation/AddInvestigation";
import DeleteInvestigation from "./pages/investigation/DeleteInvestigation";
import FindConsultation from "./pages/consulatation/FindConsultation";
import EditPhysician from "./pages/physician/EditPhysiscian";
import AllPatients from "./pages/patient/AllPatients";
import PatientAppointments from "./pages/patient/PatientAppointments";
import PatientConsultations from "./pages/patient/PatientConsultations";
import MyData from "./pages/patient/MyData";
import PhysicianPatients from "./pages/physician/PhysicianPatients";
import EditConsultation from "./pages/consulatation/EditConsultation";
import EditInvestigation from "./pages/investigation/EditInvestigation";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="user" element={<MyData />} />
          <Route path="allPatients" element={<AllPatients />} />
          <Route path="login" element={<Login />} />
          <Route path="register" element={<Register />} />
          <Route path="*" element={<NoPage />} />
          <Route path="addPatient" element={<AddPatient />} />
          <Route path="deletePatient" element={<DeletePatient />} />
          <Route path="editPatient/:id" element={<EditPatient />} />
          <Route path="findPatient" element={<FindPatient />} />
          <Route path="findAppointments" element={<FindAppointments />} />
          <Route path="addAppointment/:id" element={<AddAppointment />} />
          <Route path="myAppointments/:id" element={<PatientAppointments />} />
          <Route
            path="/patientConsultations"
            element={<PatientConsultations />}
          />
          <Route path="physicians" element={<Physicians />} />
          <Route path="physicianPatients/:id" element={<PhysicianPatients />} />
          <Route path="addPhysician" element={<AddPhysician />} />
          <Route path="deletePhysician" element={<DeletePhysician />} />
          <Route path="editPhysician/:id" element={<EditPhysician />} />
          <Route path="consultations" element={<Consultations />} />
          <Route path="addConsultation" element={<AddConsultation />} />
          <Route path="deleteConsultation" element={<DeleteConsultation />} />
          <Route path="findConsultations" element={<FindConsultation />} />
          <Route path="editConsultation/:id" element={<EditConsultation />} />
          <Route
            path="investigations/:consultationId"
            element={<Investigations />}
          />
          <Route path="editInvestigation" element={<EditInvestigation />} />
          <Route path="addInvestigation" element={<AddInvestigation />} />
          <Route path="deleteInvestigation" element={<DeleteInvestigation />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
