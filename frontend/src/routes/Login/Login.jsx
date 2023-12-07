import './components/style.css';
import imgError from './assets/icons8-erro-48.png';
import imgWave from './assets/wave (1).svg';
import imgLogin from './assets/Sign up-bro.png';
import imgClover from './assets/PTCC.png';
import { Link } from "react-router-dom";
import { useState } from 'react';

const Login = () => {
  const [inpUsername, setInpUsername] = useState('');
  const [inpSenha, setInpSenha] = useState('');
  const [Modal, setModal] = useState({ display: 'none' });
  const [ModalOpacity, setModalOpacity] = useState({ display: 'none' });

  const [errors, SetErrors] = useState([]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    await cadastrar();
    limpar();
  };

  const limpar = () => {
    setInpUsername('');
    setInpSenha('');
  };

  const cadastrar = async () => {
    const data = {
      username: inpUsername,
      password: inpSenha,
    };

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(data)
      });

      if (response.ok) {
        const token = await response.json();
        console.log("Login bem sucedido!");
        localStorage.setItem('token' , token)
        window.location.href = `http://localhost:5173`;
      } else if (response.status === 401) {
        const error = await response.json(Error);
        console.log(error);
        SetErrors([error]);
        setModalOpacity({ display: 'block' });
        setModal({ display: 'block' });
      } else {
        console.log("Ocorreu um erro inesperado: " + response.status);
      }
    } catch (error) {
      console.error("Erro ao enviar a solicitação:", error);
    }
  };
  const closeModalOpacity = () => {
    setModalOpacity({display: 'none'});
    setModal({display: 'none'});
  }
  return (
    <main className='loginMain'>
      <article>
        <fieldset>
          <div className='imgLogin'>
            <div>
              <span>Desbrave o mundo do código com<br/>confiança e conhecimento</span>
            </div>
            <img className='imglog' src={imgLogin} alt="imageLogin" />
          </div>
          <div className='logoClover'>
            <img src={imgClover} alt="Clover" />
          </div>
          <form>
            <h2>Seja Bem Vindo!</h2>
            <div className='inpLab'>
              <label>Username</label>
              <input type="text" value={inpUsername} onChange={e => setInpUsername(e.target.value)} />
            </div>
            <div className='inpLab'>
              <label>Senha</label>
              <input type="password" value={inpSenha} onChange={e => setInpSenha(e.target.value)} />
            </div>
            <div className="btnCad">
              <button onClick={handleSubmit}>
                Entrar
              </button>
              <Link to={'/'}>
                <button>
                  Cancelar
                </button>
              </Link>
            </div>
             <Link to={`/register`}>
                <label>
                  Não possui uma conta? Registre-se agora!
                </label>
              </Link>
          </form>
        </fieldset>
      </article>
      <div className="modal" style={{ display: Modal.display }}>
        <div className="errorModal">
          <div className="errorIcon">
            <img src={imgError} alt="Error" />
            <h2>Erro!</h2>
          </div>
          <hr />
          <div className="errorMessages">
              {errors.map((erro,index) =>(
              <div key={index}>
                <strong>{erro.Error}</strong>
              </div>
              ))}
          </div>
        </div>
      </div>
      <div className='modalOpacity' onClick={closeModalOpacity} style={{ display: ModalOpacity.display }}></div>
      <div className="waveLogin"><img src={imgWave} alt="Wave" /></div>
    </main>
  );
};

export default Login;
