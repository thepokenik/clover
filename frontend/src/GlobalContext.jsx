// GlobalContext.js
import React, { createContext, useContext, useState } from 'react';

const GlobalContext = createContext();

export const GlobalProvider = ({ children }) => {
  const [token, setToken] = useState(null);

  const updateToken = (newValue) => {
    setToken(newValue);
  };

  return (
    <GlobalContext.Provider value={{ token, updateToken }}>
      {children}
    </GlobalContext.Provider>
  );
};

export const useGlobal = () => {
  return useContext(GlobalContext);
};
