import React from "react";
import { useState } from "react";
import './Components/App.css';
import Homeimage from '../../assets/Intelligence.png';
import { Link } from "react-router-dom";

// Importação da imagem de fundo
import imgBack from './assets/background.png';

/**
 * Componente React para a página inicial (Home).
 * Apresenta uma imagem, texto e botão de chamada para ação.
 */
const Home = () => {
    const [validarLogin, setValidarLogin] = useState();

    const token = localStorage.getItem('token');

    console.log(token);

    fetch("http://localhost:8080/users"),{
        headers:{
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
        }
    }
    return (
        <article className="homeArticle">
            <section className="homeSection">
                {/* Imagem na seção inicial */}
                <div className="imgHome">
                    <img src={Homeimage} alt="HomeImage" width={400} />
                </div>

                {/* Conteúdo de texto e botão */}
                <div className="homeDivTxtButton">
                    {/* Divisão para o texto da Home */}
                    <div className="homeDivTxt">
                        {/* Título com animação de digitação (typing) */}
                        <h2 className="typing-animation">
                            SUA PLATAFORMA DE<br/> <span className="highlight-text">ESTUDOS </span> ORGANIZADOS
                        </h2>
                        {/* Subtítulo */}
                        <span>Vamos Começar?</span>
                    </div>

                    {/* Botão de chamada para ação */}
                    <div className='btn'>
                        <Link to="/">
                            <button>Veja Mais!</button>
                        </Link>
                    </div>
                </div>
            </section>
        </article>
    );
};

export default Home;
