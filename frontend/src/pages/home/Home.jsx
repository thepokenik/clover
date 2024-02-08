// Imports of other components and libraries

import { useState, useEffect } from "react";
import wave from "./assets/wave.svg";
import mais from "./assets/iconMais.png";
import Modal from "../components/Modal";
import { getStatusClass } from "./components/utils/getStatusClass/getStatusClass";
import { CreateNewRequest } from "./components/utils/createNewRequest/CreateNewRequest";
import {
  closeModal,
  openModal,
} from "./components/utils/ModalFunctions/ModalFunctions";
import { fetchRequestsPage } from "./components/utils/fetchRequestsPagination/FetchRequestPage";
import FilterBar from "./components/subNav/FilterBar";
import { ToolBox } from "./components/toolBox/ToolBox";
import { Pagination } from "./components/pagination/Pagination";
import RequestForm from "./components/requestForm/RequestForm";
import { fetchRequests } from "./components/utils/fetchRequests/FetchRequest";

const HomeSecurity = () => {
  // Retrieve token from local storage
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  // State variables
  const [toolBoxes, setToolBoxes] = useState([]);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    idProject: "",
    projectName: "",
    projectDescription: "",
    creationDate: "",
    projectProgress: "",
    user: [
      {
        idUsers: "",
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        role: "",
      },
    ],
  });
  const [showId, setShowId] = useState(true);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [requestsLoaded, setRequestsLoaded] = useState(false);

  const [currentPage, setCurrentPage] = useState(0);

  // Fetch requests when the component mounts and requests are not loaded
  useEffect(() => {
    if (!requestsLoaded) {
      fetchData();
    }
  }, [requestsLoaded]);

  // Fetch requests periodically (every 5 seconds)
  useEffect(() => {
    const intervalId = setInterval(() => {
      fetchData(currentPage);
    }, 10000);

    // Clear the interval when the component is unmounted
    return () => clearInterval(intervalId);
  }, [currentPage, token]);

  // Function to add a new request
  const handleAddBox = () => {
    setToolBoxes([...toolBoxes, formData]);
    setFormData({
      idProject: "",
      projectName: "",
      projectDescription: "",
      creationDate: "",
      projectProgress: "",
      user: [
        {
          idUsers: "",
          username: "",
          firstName: "",
          lastName: "",
          email: "",
          role: "",
        },
      ],
    });
    closeModal(modalIsOpen);
  };

  // Function to save the form data and create a new request
  const handleSave = () => {
    setFormData({
      ...formData,
      projectName: document.getElementById("projectName").value,
      projectDescription: document.getElementById("projectDescription").value,
    });
    createNewRequest();
    handleAddBox();
  };

  const handleNextPage = () => {
    setCurrentPage((prevPage) => {
      // Chama a função fetchRequests imediatamente após a mudança da página
      const nextPage = prevPage + 1;
      role === "ADMIN"
        ? fetchRequestsPage(
          nextPage,
          setLoading,
          token,
          setToolBoxes,
          getStatusClass,
          setRequestsLoaded
        )
        : fetchRequests(
          nextPage,
          setLoading,
          token,
          setToolBoxes,
          getStatusClass,
          setRequestsLoaded
        );
      return nextPage;
    });
  };

  const handlePreviousPage = () => {
    setCurrentPage((prevPage) => {
      // Chama a função fetchRequests imediatamente após a mudança da página
      const previousPage = prevPage - 1;
      role === "ADMIN"
        ? fetchRequestsPage(
          previousPage,
          setLoading,
          token,
          setToolBoxes,
          getStatusClass,
          setRequestsLoaded
        )
        : fetchRequests(
          previousPage,
          setLoading,
          token,
          setToolBoxes,
          getStatusClass,
          setRequestsLoaded
        );

      return previousPage;
    });
  };

  // Function to handle search input
  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
  };

  const fetchData = async () => {
    role === "ADMIN"
      ? await fetchRequestsPage(
        currentPage,
        setLoading,
        token,
        setToolBoxes,
        getStatusClass,
        setRequestsLoaded
      )
      : await fetchRequests(
        currentPage,
        setLoading,
        token,
        setToolBoxes,
        getStatusClass,
        setRequestsLoaded
      );
  };

  const createNewRequest = async () => {
    await CreateNewRequest(formData, token);
    setModalIsOpen(false);
  };

  // Move the declaration to the appropriate location
  const filteredAndSortedToolBoxes = Array.isArray(toolBoxes)
    ? toolBoxes.filter((box) =>
      box.projectName.toLowerCase().includes(searchTerm.toLowerCase())
    )
    : [];

  useEffect(() => {
    const intervalId = setInterval(() => {
      setShowId((prevShowId) => !prevShowId);
    }, 4000);

    return () => {
      clearInterval(intervalId);
    };
  }, []);

  return (
    <section className="homeSection">
      <div className="wave">
        <img src={wave} alt="" />
      </div>
      <div className="alignCont">
        <FilterBar
          handleSearch={handleSearch}
          searchTerm={searchTerm}
          role={role}
        />
        <div className="boxTools">
          <div
            className="tool"
            style={{ display: "grid", placeItems: "center" }}
            onClick={() => openModal(setModalIsOpen)}
          >
            <h2>Create new Project</h2>
            <img src={mais} alt="Add" width={40} />
          </div>
          {filteredAndSortedToolBoxes.map((box, index) => (
            // Verifica se o item atende aos critérios de filtro antes de renderizá-lo
            <ToolBox
              key={index}
              box={box}
              loading={loading}
              showId={showId}
              role={role}
            />
          ))}
        </div>
        <Pagination
          currentPage={currentPage}
          handlePreviousPage={handlePreviousPage}
          handleNextPage={handleNextPage}
          role={role}
        />
      </div>

      <Modal isOpen={modalIsOpen} onClose={() => closeModal(setModalIsOpen)}>
        <RequestForm
          formData={formData}
          setFormData={setFormData}
          handleSave={handleSave}
          role={role}
        />
      </Modal>
    </section>
  );
};

export default HomeSecurity;
