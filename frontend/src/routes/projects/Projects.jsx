import React, { useEffect } from "react";
import ScrollReveal from 'scrollreveal';
import { Link } from "react-router-dom";
import './Components/App.css'
import imgWave from './Assets/wave.svg'
import imgCalendario from './Assets/icons8-calendário-50.png'
import imgMais from './Assets/icons8-mais-50.png'
import imgPapel from './Assets/icons8-papel-50.png'

/**
 * Componente React para a página de Projetos.
 * Utiliza a biblioteca ScrollReveal para animações de revelação ao rolar.
 */
const Projects = () => {
    // Efeito useEffect para configurar animações com ScrollReveal
    useEffect(() => {
        const sr = ScrollReveal();
        
        const calculateDistance = () => {
            // Lógica para calcular a distância com base em fatores responsivos
            return window.innerWidth > 768 ? '70px' : '0px';
        };

        sr.reveal('.projectsSection', {
            origin: 'left',
            duration: 1000,
            distance: calculateDistance(),
            reset: true,
        });

        sr.reveal('.ProjectsBox', {
            origin: 'right',
            duration: 1000,
            distance: calculateDistance(),
            reset: true,
        });

        sr.reveal('.boxProjec', {
            origin: 'bottom',
            duration: 1000,
            distance: calculateDistance(),
            reset: true,
        });

        sr.reveal('.projectsMore', {
            origin: 'right',
            duration: 1000,
            distance: calculateDistance(),
            reset: true,
        });
    }, []);

    return (
        <article className="projectsArticle">
            {/* Imagem de Onda */}
            <div className="waveProject"><img src={imgWave} alt="Wave" draggable="false"/></div>

            {/* Seção de Projetos */}
            <section className="projectsSection">
                <div>
                    <h2>Área de Trabalho</h2>
                    {/* Box do Projeto - Calendário de Aulas */}
                    <Link to="#">
                        <div className="ProjectsBox">
                            <div className="projectsMore">
                                <Link to="">
                                    <img src={imgMais} alt="mais" />
                                </Link>
                            </div>
                            <div className="projectsCalendario">
                                <img src={imgCalendario} alt="Calendário"/>
                                <h3>Calendario de Aulas</h3>
                            </div>
                        </div>
                    </Link>
                </div>

                {/* Projetos Aglomerados */}
                <div className="projectsAglomerados">
                    {/* Box do Projeto - Anotação */}
                    {[1, 2, 3, 4].map((index) => (
                        <Link to="" key={index}>
                            <div className="boxProjec">
                                <div className="projectsMore">
                                    <Link to="">
                                        <img src={imgMais} alt="mais" />
                                    </Link>
                                </div>
                                <div className="Txt">
                                    <img src={imgPapel} alt="Anotação" />
                                    <h3>Anotação</h3>
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>
            </section>
        </article>
    );
};

export default Projects;
