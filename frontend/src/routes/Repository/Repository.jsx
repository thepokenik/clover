import React, { useState } from 'react';

const Repository = () => {
  const token = localStorage.getItem('token');
  const [formData, setFormData] = useState({
    projectName: '',
    projectReadme: '',
    projectDescription: '',
    projectFile: '',
  });
  const [errors, setErrors] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    await cadastrar();
    limpar();
  };

  const limpar = () => {
    setFormData({
      projectName: '',
      projectReadme: '',
      projectDescription: '',
      projectFile: '',
    });
  };

  const cadastrar = async () => {
    try {
      const response = await fetch('http://localhost:8080/projects/newRepository', {
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`, 
        },
        method: 'POST',
        body: JSON.stringify(formData),
      });

      if (response.status === 201) {
        console.log('Cadastro bem-sucedido!');
        // Substitua com o redirecionamento apropriado
      } else if (response.status === 400) {
        const errorData = await response.json();
        const errorArray = [];

        for (const fieldName in errorData) {
          const errorMessage = errorData[fieldName];
          errorArray.push({ fieldName, errorMessage });
        }

        setErrors(errorArray);
      } else {
        console.log('Ocorreu um erro inesperado: ' + response.status);
      }
    } catch (error) {
      console.log('Erro ao enviar a solicitação:', error);
    }
  };

  return (
    <form>
      <h5>{token}</h5>
      <label>
        Project Name:
        <input
          type="text"
          name="projectName"
          value={formData.projectName}
          onChange={handleChange}
        />
      </label>
      <br />

      <label>
        Project Readme:
        <textarea
          name="projectReadme"
          value={formData.projectReadme}
          onChange={handleChange}
        />
      </label>
      <br />

      <label>
        Project Description:
        <input
          type="text"
          name="projectDescription"
          value={formData.projectDescription}
          onChange={handleChange}
        />
      </label>
      <br />

      <label>
        Project File:
        <input
          type="text"
          name="projectFile"
          value={formData.projectFile}
          onChange={handleChange}
        />
      </label>
      <br />

      <button type="button" onClick={handleSubmit}>
        Submit
      </button>

      {/* Exibir erros */}
      {errors.length > 0 && (
        <div>
          <h4>Erros:</h4>
          <ul>
            {errors.map((error, index) => (
              <li key={index}>{`${error.fieldName}: ${error.errorMessage}`}</li>
            ))}
          </ul>
        </div>
      )}
    </form>
  );
};

export default Repository;
