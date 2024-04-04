import { Box, Button, TextField, Typography, useTheme } from "@mui/material";
import { tokens } from "../../theme";
import Header from "../../components/Header";
import { useState } from "react";
import { Link, redirect } from "react-router-dom";
import { register } from "../../utils/ApiFunction";

const Register = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [fullname, setFullname] = useState("");
  const [phone_number, setPhoneNumber] = useState("");
  const [password, setPassword] = useState("");
  const [retype_password, setRetypePassword] = useState("");
  const [date_of_birth, setDateOfBirth] = useState("");
  const [address, setAddress] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleChange = (event) => {
    const { name, value } = event.target;
    if (name === "phone_number") {
      setPhoneNumber(value);
    } else if (name === "fullname") {
      setFullname(value);
    } else if (name === "password") {
      setPassword(value);
    } else if (name === "retype_password") {
      setRetypePassword(value);
    } else if (name === "address") {
      setAddress(value);
    } else if (name === "date_of_birth") {
      setDateOfBirth(value);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const isValid = validate();
    if (isValid) {
      const formData = new FormData();
      formData.append("fullname", fullname);
      formData.append("phone_number", phone_number);
      formData.append("password", password);
      formData.append("retype_password", retype_password);
      formData.append("address", address);
      formData.append("date_of_birth", date_of_birth);
      formData.append("role_id", 1);
      formData.append("facebook_account_id", 0);
      formData.append("google_account_id", 0);
      register(formData).then(response=>
        { 
          if(response){
           window.location.href='/login';
          }else{
            setErrorMessage("Đăng ký không thành công");
          }
        })
      
    }
  };

  const validate = () => {
    if (
      fullname === "" ||
      phone_number === "" ||
      password === "" ||
      retype_password === "" ||
      address === "" ||
      date_of_birth === ""
    ) {
      setErrorMessage("Form đăng ký đang thiếu thông tin, vui lòng kiểm tra lại");
      return false;
    }
    if (password !== retype_password) {
      setErrorMessage("Mật khẩu và xác nhận mật khẩu không khớp");
      return false;
    }
    setErrorMessage("");
    return true;
  };

  return (
    <Box padding="100px 500px" display="flex" >
      <Box
        backgroundColor={colors.primary[700]}
        width="100%"
        height="100%"
        display="flex"
        flexDirection="column"
        justifyContent="center"
        borderRadius="10px"
        textAlign="center"
        padding="15px"
      >
        <Header title="ĐĂNG KÝ" subtitle="Quý khách vui lòng nhập đầy đủ thông tin ạ" />

        <form onSubmit={handleSubmit}>
          {errorMessage && (
            <Typography color={colors.redAccent[400]} fontWeight="600" marginBottom="20px" padding="10px" borderRadius="15px" bgcolor="yellow">
              {errorMessage}
            </Typography>
          )}
          <Box display="grid" gap="20px" padding="0 50px">
            <TextField fullWidth name="fullname" type="text" variant="filled" required label="Họ và tên" onChange={handleChange} />
            <TextField fullWidth name="phone_number" type="text" variant="filled" required label="Số điện thoại" onChange={handleChange} />
            <TextField fullWidth name="password" type="password" variant="filled" required label="Mật khẩu" onChange={handleChange} />
            <TextField fullWidth name="retype_password" type="password" variant="filled" required label="Xác nhận mật khẩu" onChange={handleChange} />
            <TextField fullWidth name="address" type="text" variant="filled" required label="Địa chỉ" onChange={handleChange} />
            <TextField fullWidth name="date_of_birth" type="date" variant="filled" required placeholder="01/01/2000" onChange={handleChange} />
            <Button type="submit" variant="contained" color="success">
              Đăng ký
            </Button>
          </Box>
        </form>
        <hr />
        <Box display="flex" justifyContent="end" textAlign="center" padding="0 20px">
          <Typography paddingRight="5px">Bạn đã có tài khoản?</Typography>
          <Link to="/login" style={{ textDecoration: "none", color: "gray" }}>
            <Typography>Đăng nhập</Typography>
          </Link>
        </Box>
      </Box>
    </Box>
  );
};

export default Register;
