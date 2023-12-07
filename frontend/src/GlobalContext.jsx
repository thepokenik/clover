// GlobalContext.js
import React, { createContext, useContext, useState } from 'react';

const GlobalContext = createContext();

export const GlobalProvider = ({ children }) => {
  const [username, setUsername] = useState('Valor Inicial');

  const updateUsername = (newValue) => {
    setUsername(newValue);
  };

  return (
    <GlobalContext.Provider value={{ username, updateUsername }}>
      {children}
    </GlobalContext.Provider>
  );
};

export const useGlobal = () => {
  return useContext(GlobalContext);
};
