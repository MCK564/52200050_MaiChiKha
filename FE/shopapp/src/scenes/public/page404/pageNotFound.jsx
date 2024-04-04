import React from "react";
import { Typography, Box, SvgIcon, useTheme } from "@mui/material";
import { Link } from "react-router-dom";
import ArrowCircleLeftIcon from '@mui/icons-material/ArrowCircleLeft';
const PageNotFound = () => {
  const theme = useTheme();

  return (
    <>
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <Box textAlign="center">
          <Typography variant="h4">404, page not found.</Typography>
          <Link to="/products?keyword=a&page=0&limit=20" style={{textDecoration:"none"}}>
            <Typography variant="h4" marginTop="15px" color="yellowgreen">  <ArrowCircleLeftIcon/> Go back home</Typography>
          </Link>
        </Box>
      </Box>
     

      <SvgIcon
        style={{ fill: theme.palette.text.secondary, width: "20px", margin: "15px", cursor: "pointer" }}
        viewBox="0 0 279.9 297.3"
      >
        <path d="M269.4,162.6c-2.7,66.5-55.6,120.1-121.8,123.9c-77,4.4-141.3-60-136.8-136.9C14.7,81.7,71,27.8,140,27.8 c1.8,0,3.5,0,5.3,0.1c0.3,0,0.5,0.2,0.5,0.5v15c0,1.5,1.6,2.4,2.9,1.7l35.9-20.7c1.3-0.7,1.3-2.6,0-3.3L148.6,0.3 c-1.3-0.7-2.9,0.2-2.9,1.7v15c0,0.3-0.2,0.5-0.5,0.5c-1.7-0.1-3.5-0.1-5.2-0.1C63.3,17.3,1,78.9,0,155.4 C-1,233.8,63.4,298.3,141.9,297.3c74.6-1,135.1-60.2,138-134.3c0.1-3-2.3-5.4-5.3-5.4l0,0C271.8,157.6,269.5,159.8,269.4,162.6z" />
      </SvgIcon>
    </>
  );
};

export default PageNotFound;
