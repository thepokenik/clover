import { useState } from 'react';
import './App.css';
import { Outlet } from 'react-router-dom';
import Navbar from './Components/Navbar';
import Projects from './routes/projects/Projects';
import About from './routes/About/About';
import Creators from './routes/Creators/Creators';
import SplashScreen from './Components/SplashScreen';
import { GlobalProvider } from './GlobalContext';
function App() {

  const [count, setCount] = useState(0)
  
  return (
    <>
    <GlobalProvider>
      <div>
      <SplashScreen />
      <Navbar />
        <main>
          <Outlet />
          <Projects />
          <About />
          <Creators />
        </main>
      </div>
    </GlobalProvider>
    </>
  )
}

export default App
