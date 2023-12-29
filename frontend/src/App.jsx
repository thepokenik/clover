import { useState } from "react";
import "./App.css";
import { Outlet } from "react-router-dom";
import Navbar from "./Components/Navbar";
import Projects from "./routes/projects/Projects";
import About from "./routes/About/About";
import Creators from "./routes/Creators/Creators";
import SplashScreen from "./Components/SplashScreen";
import Footer from "./routes/Footer/Footer";
function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <div>
        <SplashScreen />
        <Navbar />
        <main>
          <Outlet />
          <Projects />
          <About />
          <Creators />
          <Footer />
        </main>
      </div>
    </>
  );
}

export default App;
