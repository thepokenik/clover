import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import './index.css';
import { GlobalProvider } from './GlobalContext';

import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Home from './routes/Home/Home.jsx';
import Projects from './routes/projects/Projects.jsx';
import ErrorPage from './routes/ErrorPage/ErrorPage.jsx';
import Register from './routes/Register/Register.jsx';
import Login from './routes/Login/Login.jsx';
import About from './routes/About/About.jsx';
import Creators from './routes/Creators/Creators.jsx';
import Modeler from './routes/Database modeler/modeler.jsx';
import Repository from './routes/Repository/Repository.jsx';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "Projects",
        element: <Projects />,
      },
      {
        path:"About",
        element: <About />,
      },
      {
        path:"Creators",
        element: <Creators />
      },
    ]
  },
  {
    path: "Login",
    element: <Login />,
  },
  {
    path: "Register",
    element: <Register />,
  },
  {
    path: "modeler",
    element: <Modeler />,
  },
  {
    path: "repository",
    element: <Repository/>
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <GlobalProvider>
      <RouterProvider router={router} />
    </GlobalProvider>
  </React.StrictMode>,
)
