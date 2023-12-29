import React from "react";
import "./components/Footer.css";
import gitHub from "./Assets/github.svg";
import Instagram from "./Assets/instagram.svg";
import Twitter from "./Assets/twitter.svg";
import Linkedin from "./Assets/linkedin.svg";

const Footer = () => {
  return (
    <section className="footer-section">
      <div className="footer-content">
        <div className="footer-links">
          <div className="footer-links-div">
            <h4>For Studing</h4>
            <a href="#">
              <p>TESTE</p>
            </a>
            <a href="#">
              <p>TESTE</p>
            </a>
            <a href="#">
              <p>TESTE</p>
            </a>
          </div>
          <div className="footer-links-div">
            <h4>Resources</h4>
            <a href="#">
              <p>TESTE</p>
            </a>
            <a href="#">
              <p>TESTE</p>
            </a>
            <a href="#">
              <p>TESTE</p>
            </a>
          </div>
          <div className="footer-links-div">
            <h4>Swing Tech</h4>
            <a href="#">
              <p>TESTE</p>
            </a>
            <a href="#">
              <p>TESTE</p>
            </a>
            <a href="#">
              <p>TESTE</p>
            </a>
          </div>
          <div className="footer-links-div">
            <h4>Company</h4>
            <a href="#">
              <p>About</p>
            </a>
            <a href="#">
              <p>Press</p>
            </a>
            <a href="#">
              <p>Career</p>
            </a>
            <a href="#">
              <p>Contact</p>
            </a>
          </div>
          <div className="footer-links-div">
            <h4>Heitor</h4>
            <div className="socialmedia">
              <p>
                <img src={gitHub} alt="" />
              </p>
              <p>
                <img src={Twitter} alt="" />
              </p>
              <p>
                <img src={Linkedin} alt="" />
              </p>
              <p>
                <img src={Instagram} alt="" />
              </p>
            </div>
            <h4>Ryan</h4>
            <div className="socialmedia">
              <p>
                <img src={gitHub} alt="" />
              </p>
              <p>
                <img src={Twitter} alt="" />
              </p>
              <p>
                <img src={Linkedin} alt="" />
              </p>
              <p>
                <img src={Instagram} alt="" />
              </p>
            </div>
            <h4>Nikolas</h4>
            <div className="socialmedia">
              <p>
                <img src={gitHub} alt="" />
              </p>
              <p>
                <img src={Twitter} alt="" />
              </p>
              <p>
                <img src={Linkedin} alt="" />
              </p>
              <p>
                <img src={Instagram} alt="" />
              </p>
            </div>
          </div>
        </div>
        <hr></hr>
        <div className="footer-bellow">
          <div className="footer-copyright">
            <p>@{new Date().getFullYear()} Elf. All right reserved</p>
          </div>
          <div className="footer-bellow-links">
            <a href="#">
              <div>
                <p>Terms & Conditions</p>
              </div>
            </a>
            <a href="#">
              <div>
                <p>Privacy</p>
              </div>
            </a>
            <a href="#">
              <div>
                <p>Security</p>
              </div>
            </a>
            <a href="#">
              <div>
                <p>Cookie Declaration</p>
              </div>
            </a>
          </div>
        </div>
      </div>
    </section>
  );
};

export default Footer;
