// CartContext.js
import React, { createContext, useState, useContext } from 'react';

const CartContext = createContext();

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState(0);

  const addToCart = (quantity) => {
    setCartItems((prevItems) => prevItems + quantity);
  };

  const cartChange = (quantity) => {
    setCartItems(quantity);
  };

  return (
    <CartContext.Provider value={{ cartItems, addToCart, cartChange }}>
      {children}
    </CartContext.Provider>
  );
};

export const useCart = () => useContext(CartContext);
