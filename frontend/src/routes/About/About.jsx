import React, { useEffect } from "react";
import ScrollReveal from 'scrollreveal';
import "./components/About.css";
import cloverImg from "./assets/PTCC.png";
import waveImg from "./assets/wave.svg";

function About() {
  useEffect(() => {
    const sr = ScrollReveal();
    
    const calculateDistance = () => {
        // Lógica para calcular a distância com base em fatores responsivos
        return window.innerWidth > 768 ? '200px' : '0px';
    };

    sr.reveal('.about-description', {
        origin: 'bottom',
        duration: 1000,
        distance: calculateDistance(),
        reset: true,
    });
}, []);
  return (
    <section className="about-section">
      <div>
        <div className="about-title">
          <h2>Sobre</h2>
          <img className="cloverImg" src={cloverImg} alt="" />
        </div>
        <div className="about-description">
          <p>
            A escolha desse tema se baseia na constante evolução do mercado de
            tecnologia, que demanda profissionais capacitados e atualizados. Além
            disso, a educação a distância tem se mostrado uma ferramenta eficaz
            para democratizar o acesso à educação, permitindo que indivíduos de
            diferentes realidades geográficas e socioeconômicas tenham a
            oportunidade de desenvolver habilidades em programação. 
          </p>
          <p>Lorem ipsum
            dolor sit amet consectetur, adipisicing elit. Officia asperiores eum
            doloremque eaque dolor tempora perferendis quam atque perspiciatis
            rem! Officia, eius vel ratione velit perspiciatis quod quisquam atque
            commodi! Lorem ipsum dolor sit amet consectetur adipisicing elit.
           </p>
           <p> Accusamus magnam esse voluptas, enim iste neque architecto tempora quo
            perferendis quos totam repellendus in eos eveniet assumenda vitae.
            Molestiae, nulla expedita. Lorem ipsum dolor sit amet, consectetur
            adipisicing elit. Voluptas perspiciatis vero et? Possimus quo magnam
            quae id! Facere ipsa aspernatur molestiae, commodi fugiat voluptate
            deleniti et temporibus voluptatum saepe recusandae?</p>
        </div>
      </div>
      <div className="waveProject">
        <img className="waveAbout" src={waveImg} alt="Wave" draggable="false"/>
      </div>
    </section>
  );
}

export default About;
