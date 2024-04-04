import React, { useState, useEffect } from "react";
import { Box, Button, Checkbox, TextField, Typography, Grid } from "@mui/material";
import Header from "../../components/Header";
import { Link, useNavigate } from "react-router-dom";
import FacebookOutlinedIcon from "@mui/icons-material/FacebookOutlined";
import GoogleIcon from "@mui/icons-material/Google";
import { login } from "../../utils/ApiFunction";
import { useCart } from "../../contexts/CartContext";

const LoginForm = () => {
  const [phoneNumber, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");
  const [isRemember, setIsRemember] = useState(false);
  const {cartChange} = useCart();
  const navigate = useNavigate();

  const handleChange = (event) => {
    const { name, value, checked } = event.target;
    if (name === "phoneNumber") {
      setPhoneNumber(value);
    } else if (name === "password") {
      setPassword(value);
    } else if (name === "isRemember") {
      setIsRemember(checked);
    }
  };
  const getQuantity = ()=>{
    
  }
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await login(phoneNumber, password, isRemember);
      if (response) {
        console.log(response);
        cartChange(response.quantity);
        const roles = response.roles;
       if (roles && roles.length > 0 && roles[0] === "ROLE_ADMIN") {
        window.location.href= "/admin/products";
      } else if(roles && roles.length >0 &&roles[0] === "ROLE_USER") {
        window.location.href="/";
      }
      } else {
        alert("Login failed");
      }
    } catch (error) {
      console.error("Login fail", error);
    }
  };

  useEffect(() => {
    if (localStorage.getItem("token") !== null) {
      navigate("/products");
    }
  }, []);

  return (
    <>
      <Box p={{ xs: "20px", sm: "100px" }}>
        <Grid container justifyContent="center">
          <Grid item xs={12} sm={8} md={6} lg={4}>
            <Box
              bgcolor="#131926"
              borderRadius="10px"
              p="20px"
              textAlign="center"
              color="white"
            >
              <Header title="ĐĂNG NHẬP" subtitle="NHẬP SỐ ĐIỆN THOẠI VÀ MẬT KHẨU" />
              <form onSubmit={handleSubmit}>
                <Box my="20px">
                  <TextField
                    variant="filled"
                    type="text"
                    name="phoneNumber"
                    label="Số điện thoại"
                    fullWidth
                    required
                    onChange={handleChange}
                  />
                </Box>
                <Box my="20px">
                  <TextField
                    variant="filled"
                    type="password"
                    name="password"
                    label="Mật khẩu"
                    fullWidth
                    required
                    onChange={handleChange}
                  />
                </Box>
                <Box display="flex" justifyContent="space-between" alignItems="center" mb="20px">
                  <Box display="flex" alignItems="center"> <Checkbox
                    name="isRemember"
                    checked={isRemember}
                    onChange={handleChange}
                    color="secondary"
                  />
                  <Typography variant="body2">Nhớ mật khẩu</Typography></Box>
                 
                  <Link to="/forgot-password" style={{ textDecoration: "none", color: "gray" }}>
                    <Typography variant="body2">Quên mật khẩu?</Typography>
                  </Link>
                </Box>
                  <Box display="flex" justifyContent="space-between">
                  <Button
                  variant="contained"
                  color="success"
                  type="submit"
                  fullWidth
                  onClick={handleSubmit}
                >
                  Đăng nhập
                </Button>
                <Button
                  variant="contained"
                  color="error"
                  fullWidth
                  onClick={() => {
                    window.location.href = "/register";
                  }}
                >
                  Đăng ký
                </Button>
                  </Box>
               
              </form>
              <hr style={{ borderColor: "gray", margin: "20px 0" }} />
              <Typography variant="body1">ĐĂNG NHẬP VỚI</Typography>
              <Box display="flex" justifyContent="center" mt="10px">
                <Button variant="contained" color="info" startIcon={<FacebookOutlinedIcon />}>
                  Facebook
                </Button>
                <Button
                  variant="contained"
                  sx={{ bgcolor: "orange" }}
                  startIcon={<GoogleIcon />}
                  style={{ marginLeft: "10px" }}
                >
                  Google+
                </Button>
              </Box>
            </Box>
          </Grid>
        </Grid>
      </Box>
    </>
  );
};

export default LoginForm;
