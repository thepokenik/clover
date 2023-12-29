import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import ScrollReveal from "scrollreveal";
import "./Components/App.css";
import imgWave from "./Assets/wave.svg";
import imgCalendario from "./Assets/icons8-calendário-50.png";
import imgMais from "./Assets/icons8-mais-50.png";
import imgPapel from "./Assets/icons8-papel-50.png";

const Projects = () => {
  useEffect(() => {
    const sr = ScrollReveal();

    const calculateDistance = () => {
      return window.innerWidth > 768 ? "70px" : "0px";
    };

    sr.reveal(".projectsSection", {
      origin: "left",
      duration: 1000,
      distance: calculateDistance(),
      reset: true,
    });

    sr.reveal(".ProjectsBox", {
      origin: "right",
      duration: 1000,
      distance: calculateDistance(),
      reset: true,
    });

    sr.reveal(".boxProject", {
      origin: "bottom",
      duration: 1000,
      distance: calculateDistance(),
      reset: true,
    });

    sr.reveal(".projectsMore", {
      origin: "right",
      duration: 1000,
      distance: calculateDistance(),
      reset: true,
    });
  }, []);

  return (
    <article className="projectsArticle">
      <div className="waveProject">
        <img src={imgWave} alt="Wave" draggable="false" />
      </div>

      <section className="projectsSection">
        <div>
          <h2>Área de Trabalho</h2>
          <Link to="#">
            <div className="ProjectsBox">
              <div className="projectsMore">
                <Link to="">
                  <img src={imgMais} alt="mais" />
                </Link>
              </div>
              <div className="img-calend">
                <img src={imgCalendario} alt="Calendário" />
              </div>
              <div className="projectsCalendario">
                <h3>Calendario de Aulas</h3>
                <p>
                  Este é calendário onde você pode controlar os dias dos seus
                  estudos!
                </p>
              </div>
            </div>
          </Link>
        </div>

        {/* Novos blocos de Anotações */}
        <div className="projectsAglomerados">
          {/* Bloco 1 */}
          <Link to="#">
            <div className="boxProject1">
              <div className="projectsMore">
                <Link to="">
                  <img src={imgMais} alt="mais" />
                </Link>
              </div>
              <div className="anotacaoTxt">
                <img src={imgPapel} alt="Anotações" />
              </div>
              <div className="projectsAnimation">
                <h3>Calendario de Aulas</h3>
                <p>
                  Lorem ipsum dolor sit amet consectetur adipisicing elit. At
                  voluptatem iusto quidem doloribus reiciendis officia vero ad
                  dolor eaque aliquid tempore ab, beatae, fuga sequi voluptas
                  expedita cum id incidunt.
                </p>
              </div>
            </div>
          </Link>

          {/* Bloco 2 */}
          <Link to="#">
            <div className="boxProject2">
              <div className="projectsMore">
                <Link to="">
                  <img src={imgMais} alt="mais" />
                </Link>
              </div>
              <div className="anotacaoTxt">
                <img src={imgPapel} alt="Anotações" />
              </div>
              <div className="projectsAnimation">
                <h3>Calendario de Aulas</h3>
                <p>
                  Este é calendário onde você pode controlar os dias dos seus
                  estudos!
                </p>
              </div>
            </div>
          </Link>

          {/* Bloco 3 */}
          <Link to="#">
            <div className="boxProject3">
              <div className="projectsMore">
                <Link to="">
                  <img src={imgMais} alt="mais" />
                </Link>
              </div>
              <div className="anotacaoTxt">
                <img src={imgPapel} alt="Anotações" />
              </div>
              <div className="projectsAnimation">
                <h3>Calendario de Aulas</h3>
                <p>
                  Este é calendário onde você pode controlar os dias dos seus
                  estudos!
                </p>
              </div>
            </div>
          </Link>

          {/* Bloco 4 */}
          <Link to="#">
            <div className="boxProject4">
              <div className="projectsMore">
                <Link to="">
                  <img src={imgMais} alt="mais" />
                </Link>
              </div>
              <div className="anotacaoTxt">
                <img src={imgPapel} alt="Anotações" />
              </div>
              <div className="projectsAnimation">
                <h3>Calendario de Aulas</h3>
                <p>
                  Este é calendário onde você pode controlar os dias dos seus
                  estudos!
                </p>
              </div>
            </div>
          </Link>
        </div>
      </section>
    </article>
  );
};

export default Projects;
