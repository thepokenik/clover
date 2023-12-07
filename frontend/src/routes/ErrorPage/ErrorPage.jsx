import React, { useEffect, useState } from "react";
import logoClover from './assets/PTCC.png';
import "./components/style.css";
import { Link } from "react-router-dom";

const ErrorPage = () => {
  // Estado para armazenar a data e hora atual
  const [currentDateTime, setCurrentDateTime] = useState(new Date());

  // Efeito para atualizar a data e hora a cada segundo
  useEffect(() => {
    const intervalId = setInterval(() => {
      setCurrentDateTime(new Date());
    }, 1000);

    // Limpa o intervalo quando o componente é desmontado
    return () => clearInterval(intervalId);
  }, []);

  // Formatação da data e hora para exibição
  const formattedDateTime = currentDateTime.toLocaleString();

  return (
    <article className="articleErrorPage404">
      <section>
        <div className="title404">
          <h1>404!</h1>
        </div>
        <div className="txtError">
          <h2>ERROR</h2>
          <h3>PAGE NOT FOUND</h3>
          {/* Utilize a tag <time> para representar data e hora */}
          <time dateTime={currentDateTime.toISOString()}>{formattedDateTime}</time>
        </div>
        <img src={logoClover} alt="Clover" />
        {/* Adiciona um botão que leva de volta à página inicial */}
        <Link to="/">
          <button>Retornar para Home</button>
        </Link>
      </section>
    </article>
  );
};

export default ErrorPage;
