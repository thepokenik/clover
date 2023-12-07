import React from "react";
import "./Components/Creators.css";
import img1 from "./assets/chicken.jpg";
import img2 from "./assets/mclovin.png";
import img3 from "./assets/judas.jpg";
import waveImg from "./assets/wave.svg";

function Creators() {
  return (
    <section className="creators-section">
      <div className="creators-title">
        <h2>Nossa Equipe</h2>
      </div>
      <div className="creators-img">
        <div className="icon">
          <img src={img1} alt="" />
          <p>Ryan G.G.</p>
        </div>
        <div className="icon">
          <img src={img2} alt="" />
          <p>Heitor</p>
        </div>
        <div className="icon">
          <img src={img3} alt="" />
          <p>Judas M.</p>
        </div>
      </div>
      {/* <div className="waveProject">
        <img src={waveImg} alt="Wave" draggable="false"/>
      </div> */}
    </section>
  );
}

export default Creators;
