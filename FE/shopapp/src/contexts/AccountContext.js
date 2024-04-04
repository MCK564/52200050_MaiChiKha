import React, { createContext, useState } from 'react';

export const AccountContext = createContext();

export const AccountProvider = ({ children }) => {
  const [account, setAccount] = useState(null);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  
  return (
    <AccountContext.Provider value={{ account, setAccount, isLoggedIn, setIsLoggedIn }}>
      {children}
    </AccountContext.Provider>
  );
};
