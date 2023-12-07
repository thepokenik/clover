import React, { useState } from "react";
import "./assets/App.css";
import logo from "../assets/PTCC.png";
import engrenagem from "./assets/icons8-engrenagem-48.png";
import menuImg from "./assets/icons8-cardápio-50.png";
import { useGlobal } from "../GlobalContext"; 
import { useLocation, Link } from 'react-router-dom';

const Navbar = () => {
  const [configs, setConfigs] = useState(false);
  const [navBarVisible, setNavBarVisible] = useState(false);
  const {username, updateUsername} = useGlobal();
  
  const location = useLocation();
  // Parse os parâmetros de consulta da URL
  const searchParams = new URLSearchParams(location.search);
  // Obtenha o valor da variável da URL
  const variavelRecebida = searchParams.get('username');
  

  const toggleNavBar = () => {
    setNavBarVisible(!navBarVisible);
  };

  const toggleConfigs = () => {
    setConfigs(!configs);
  };

  return (
    <header>
      <Link to="/">
        <div className="navbarLogo">
          <h1>Clover</h1>
          <img src={logo} alt="Clover" width={50} />
        </div>
      </Link>
      <nav>
        <div className="menuIcon" onClick={toggleNavBar}>
          <img src={menuImg} alt="menu" />
        </div>
        <ul className={`navLinks${navBarVisible?'ativo' : ''}`}>
          <Link to="/">
            <li>Home</li>
          </Link>
          <Link to="/Login">
            <li>Login</li>
          </Link>
          <Link to="/Register">
            <li>Cadastre-se</li>
          </Link>
        </ul>
      </nav>
      {/* <div className="configurations">
        <div className={`menuConfig${configs?'closerotate' : ''}`} onClick={toggleConfigs}>
          <img src={engrenagem} alt="configurations" className={`rotate${configs?'closerotate' : ''}`} />
        </div>
        <ul className={`menuConfigOptions${configs?'configAtiv' : ''}`}>
          <li>{username}</li>
          <li>Teste</li>
          <li>Teste</li>
          <li>Teste</li>
          <li>Teste</li>
        </ul>
      </div> */}
    </header>
  );
};

export default Navbar;
