import React, { Fragment, useEffect } from "react";
import LoginForm from "./scenes/public/login";
import "./app.css";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
  useNavigate
} from "react-router-dom";
import { useState } from "react";
import { ColorModeContext, useMode } from "./theme";
import { AccountProvider } from "./contexts/AccountContext";
import { CssBaseline, ThemeProvider } from "@mui/material";
import Register from "./scenes/public/register";
import PageNotFound from "./scenes/public/page404/pageNotFound";
import PageForbidden from "./scenes/public/page403/ForbiddenPage";
import ProductAdmin from "./scenes/admin/products/product";
import OrderAdmin from "./scenes/admin/orders";
import UserAdmin from "./scenes/admin/users";
import Product from "./scenes/public/products/product";
import Cart from "./scenes/public/carts/cart";
import Order from "./scenes/public/orders/order";
import HomePage from "./scenes/public/home";
import SidebarComponent from "./scenes/Sidebar";
import { getUserById } from "./utils/ApiFunction";
import Topbar from "./scenes/Topbar";
import Profile from "./scenes/public/user";
import { CartProvider } from "./contexts/CartContext";

const isLoggedInn = localStorage.getItem("token") !== null;
const role = localStorage.getItem("role");
function App() {
  const [teamAccountUsername, setUsername] = useState();
  const [theme, colorMode] = useMode();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isCollapsed, setIsCollapsed] = useState(false);
  
  
  return (
    <>
      <Router>
        <AccountProvider
          value={{
            isLoggedIn,
            setIsLoggedIn,
            teamAccountUsername,
            setUsername,
          }}
        >
          <CartProvider>
          <ColorModeContext.Provider value={colorMode}>
            <ThemeProvider theme={theme}>
              <CssBaseline />
            {(window.location.href !== 'http://localhost:3000/register' 
            && window.location.href !== 'http://localhost:3000/login') && <Topbar /> }    
              <Routes>
                <Route path="/login" element={<LoginForm />} />
                <Route path="/register" element={<Register />} />
                <Route path="/page_not_found" element={<PageNotFound />} />
                <Route path="/page_forbidden" element={<PageForbidden />} />
                <Route path="/" element={<HomePage />} />
                <Route path="/products" element={<Product />} />
               
                {/* global */}
                <Route
                  path="/*"
                  element={
                    <Fragment>
                      {isLoggedInn ? (
                        <div className="app" style={{ display: "flex", position: "relative" }}>
                          {isLoggedInn && role === "ROLE_ADMIN" && (
                            <SidebarComponent isCollapsed={isCollapsed} />
                          )}
                          <main className={isCollapsed ? "content-collapsed" : "content-expanded"}>
                            {role === "ROLE_ADMIN" ? (
                              <Routes>
                                <Route
                                  path="admin/products"
                                  element={<ProductAdmin />}
                                />
                                <Route
                                  path="admin/users"
                                  element={<UserAdmin />}
                                />
                                <Route
                                  path="admin/orders"
                                  element={<OrderAdmin />}
                                />
                                <Route path="/cart" element={<Cart />} />
                                <Route path="/orders" element={<Order />} />
                                <Route path="/profile" element={<Profile/>}/>
                                <Route
                                  path="*"
                                  element={<Navigate to="/page_not_found" />}
                                />
                              </Routes>
                            ) : (
                              <main className="content">
                                <Routes>
                                  <Route path="/cart" element={<Cart />} />
                                  <Route path="/orders" element={<Order />} />
                                  <Route path="/profile" element={<Profile/>}/>
                                  <Route
                                    path="/admin/*"
                                    element={<Navigate to="/page_forbidden" />}
                                  />
                                </Routes>
                              </main>
                            )}
                          </main>
                        </div>
                      ) : (
                        <Navigate to="/login" />
                      )}
                    </Fragment>
                  }
                />

                <Route path="*" element={<Navigate to="/page_not_found" />} />
              </Routes>
            </ThemeProvider>
          </ColorModeContext.Provider>
          </CartProvider>
        </AccountProvider>
      </Router>
    </>
  );
}

export default App;
