import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './components/style.css';
import imgLogin from './assets/Computer login-bro.png';
import imgClover from './assets/Clover__1_-removebg-preview.png';
import imgWave from './assets/wave.svg';
import imgError from './assets/icons8-erro-48 (1).png';
import cloverLogo from './assets/PTCC.png';

const Register = () => {
  // State para controlar os campos do formulário
  const [inpUsername, setInpUsername] = useState('');
  const [inpEmail, setInpEmail] = useState('');
  const [inpSenha, setInpSenha] = useState('');
  const [inpConfSenha, setInpConfSenha] = useState('');

  // State para lidar com erros de validação no formulário
  const [errors, setErrors] = useState([]);

  // States para controlar a exibição do modal de erro
  const [modal, setModal] = useState({ display: 'none' });
  const [modalOpacity, setModalOpacity] = useState({ display: 'none' });

  // Função para lidar com o envio do formulário
  const handleSubmit = async (event) => {
    event.preventDefault();
    await cadastrar();
    limpar();
  };

  // Função para limpar os campos do formulário após o envio
  const limpar = () => {
    setInpUsername('');
    setInpEmail('');
    setInpSenha('');
    setInpConfSenha('');
  };

  // Função para enviar os dados do formulário para o backend
  const cadastrar = async () => {
    const data = {
      username: inpUsername,
      email: inpEmail,
      password: inpSenha,
    };

    try {
      const response = await fetch('http://localhost:8080/auth/register', {
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify(data),
      });

      if (response.status === 201) {
        console.log('Cadastro bem-sucedido!');
        window.location.href = 'http://localhost:5173/Login';
      } else if (response.status === 400) {
        const errorData = await response.json();
        const errorArray = [];

        // Mapeia os erros recebidos do backend para um formato mais legível
        for (const fieldName in errorData) {
          const errorMessage = errorData[fieldName];
          errorArray.push({ fieldName, errorMessage });
        }

        // Adiciona erro caso as senhas não sejam iguais
        if (inpSenha !== inpConfSenha) {
          const passwordError = ({ campo: "password", mensagem: "Senhas não são iguais!" });
          errorArray.push({ errorMessage: passwordError });
        }

        // Exibe o modal de erro
        setModalOpacity({ display: 'block' });
        setErrors(errorArray);
        setModal({ display: 'block' });
      } else {
        console.log('Ocorreu um erro inesperado: ' + response.status);
      }
    } catch (error) {
      console.log('Erro ao enviar a solicitação:', error);
      // Tratamento adicional de erro aqui, se necessário
    }
  };

  // Função para fechar o modal de erro
  const closeModalOpacity = () => {
    setModalOpacity({ display: 'none' });
    setModal({ display: 'none' });
  };

  return (
    <main className="registerMain">
      <article>
        <fieldset>
          <div className="imgLogin">
            <div>
              <img className="imgClover" src={imgClover} alt="CloverName" />
              <span>Desbrave o mundo do código<br />confiança e conhecimento</span>
            </div>
            <img className="imglog" src={imgLogin} alt="imageLogin" />
          </div>
          <div className="logoClover">
            <img src={cloverLogo} alt="Clover" />
          </div>
          <form>
            <h2>Seja Bem Vindo!</h2>
            {/* ... (Campos do formulário) ... */}
            <div className="inpLab">
              <label>Username</label>
              <input type="text" value={inpUsername} onChange={(e) => setInpUsername(e.target.value)} />
            </div>
            <div className="inpLab">
              <label>E-mail</label>
              <input type="email" value={inpEmail} onChange={(e) => setInpEmail(e.target.value)} />
            </div>
            <div className="inpLab">
              <label>Senha</label>
              <input type="password" value={inpSenha} onChange={(e) => setInpSenha(e.target.value)} />
            </div>
            <div className="inpLab">
              <label>Confirme sua senha</label>
              <input type="password" value={inpConfSenha} onChange={(e) => setInpConfSenha(e.target.value)} />
            </div>
            <div className="btnCad">
              <button onClick={handleSubmit}>Cadastrar</button>
              <Link to={'/'}>
                <button>Cancelar</button>
              </Link>
            </div>
          </form>
        </fieldset>
        {/* Modal de erro */}
        <div className="modal" style={{ display: modal.display }}>
          <div className="errorModal">
            <div className="errorIcon">
              <img src={imgError} alt="Error" />
              <h2>Erro!</h2>
            </div>
            <hr />
            <div className="errorMessages">
              {/* Mapeia e exibe os erros */}
              {errors.map((error, index) => (
                <div key={index}>
                  <strong>{error.errorMessage.campo}</strong> {error.errorMessage.mensagem}
                </div>
              ))}
            </div>
          </div>
        </div>
      </article>
      {/* Modal de fundo */}
      <div className="modalOpacity" onClick={closeModalOpacity} style={{ display: modalOpacity.display }}></div>
      <div className="waveRegister">
        <img src={imgWave} alt="Wave" />
      </div>
    </main>
  );
};

export default Register;
