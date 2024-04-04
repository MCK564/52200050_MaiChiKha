import {Box, IconButton, useTheme,ListItem, ListItemIcon, ListItemText, Collapse,List, ListItemButton, Button,Badge}  from "@mui/material";
import {useContext,useEffect,useState} from "react";
import {ColorModeContext, tokens} from "../theme";
import InputBase from "@mui/material/InputBase";
import LightModeOutlinedIcon from "@mui/icons-material/LightModeOutlined";
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import NotificationsActiveOutlinedIcon from '@mui/icons-material/NotificationsActiveOutlined';
import SettingsOutlinedIcon from '@mui/icons-material/SettingsOutlined';
import PersonOutlineOutlinedIcon from '@mui/icons-material/PersonOutlineOutlined';
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import LogoutOutlinedIcon from '@mui/icons-material/LogoutOutlined';
import PostAddOutlinedIcon from '@mui/icons-material/PostAddOutlined';
import BadgeOutlinedIcon from '@mui/icons-material/BadgeOutlined';
import { AccountContext } from '../contexts/AccountContext';
import { useNavigate } from "react-router-dom";
import { isAdmin,addProductToCart, getQuantity } from "../utils/ApiFunction";
import AdminPanelSettingsIcon from '@mui/icons-material/AdminPanelSettings';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { useCart } from "../contexts/CartContext";

const Topbar = () =>{
    const theme =  useTheme();
    const colors = tokens(theme.palette.mode);
    const colorMode = useContext(ColorModeContext);
    const [open, setOpen] = useState(false);
    const [keyword, setKeyword] = useState("");
    const [admin, setAdmin] = useState(false);
    const [isExpired, SetIsExpired] = useState(false);
    const [isLogIn, setIsLogIn] = useState(false);
    const [fullname, setFullname] = useState('');
    const [isAdmin, setIsAdmin] = useState(false);
    const [quantity, setQuantity] = useState(0);
    
    const navigate = useNavigate();

    const [searchParams, setSearchParams] = useState({
      keyword: "",
      categoryId: "",
      priceFrom: 0,
      priceTo: 0,
      order: "",
      sortBy: "",
      page: 0,
      limit: 50,
    });
    const handleSearchParamChange = (e) => {
      const { name, value } = e.target;
      setSearchParams((prevState) => ({
        ...prevState,
        [name]: value,
      }));
    };
    const handleClick = ()=>{
        setOpen(!open);
    }
    const handleChange = (event) => {
      setKeyword(event.target.value);
    };

useEffect(() => {
  const token = localStorage.getItem('token');
  setIsLogIn(token !== null);
  const storedFullname = localStorage.getItem('fullname');
  setFullname(storedFullname || '');
  const role = localStorage.getItem('role');
  setIsAdmin(role === 'ROLE_ADMIN');
  handleQuantity();
  }, []); 

  const handleQuantity = ()=>{
  getQuantity().then((response)=>{
    if(response){
     setQuantity(response);
    }
  })
}
     
    const handleLogout= ()=>{ 
      localStorage.clear();
      window.location.href='/login';
    }
    const handleSearch = (e) =>
    {
      e.preventDefault();
      try{
        const params = new URLSearchParams({ keyword });
        window.location.href='/products?'+params.toString() + '&page=0&limit=20';
      }catch(error){
        alert(error);
      }
    }
    const handleKeyPress = (event) => {
      if (event.key === 'Enter') {
        handleSearch(event);
      }
    };

    const handleAdminClick = ()=>{
      if(isExpired === false){
        window.location.href ="/admin/users";
      }
      else{
        window.location.href ="/login";
      }
    }
    const handleLogin = ()=>{
      window.location.href = '/login';
    }
    const handleGoToCart = ()=>{

      navigate("/cart");
    }

    const LinkToProfile =()=>{
      handleClick();
      navigate('/profile');
    }

    const LinkToOrder = ()=>{
      handleClick();
      navigate('/orders');
    }
    return(
        <Box display="flex" justifyContent="space-between" p={2} >
        {/* Search bar */}
        <Box
      display="flex"
      backgroundColor={colors.blackCustom[900]}
      color={colors.blackCustom[900]}
      borderRadius="3px"
      position="relative"
    >
      <InputBase
        sx={{ ml: 2, flex: 1 }}
        placeholder="Search"
        value={keyword}
        onChange={handleChange}
        onKeyDown={handleKeyPress}
      />
      <IconButton type="button" sx={{ p: 1 }} onClick={handleSearch}>
        <SearchOutlinedIcon />
      </IconButton>

      
    </Box>
        {/* Icons */}
        <Box display="flex">
          <Box>
            <IconButton onClick={colorMode.toggleColorMode}>
              {theme.palette.mode === 'dark' ? (
                <DarkModeOutlinedIcon />
              ) : (
                <LightModeOutlinedIcon />
              )}
            </IconButton>
            {isAdmin === true &&   <IconButton sx={{color:"yellowgreen"}} onClick = {handleAdminClick} >
            <AdminPanelSettingsIcon/>
            </IconButton>}

            {isLogIn && <Badge color="warning" badgeContent={quantity}>
              <IconButton color="orange" onClick={handleGoToCart}>
                <ShoppingCartIcon/>
              </IconButton>
              </Badge>}

           <IconButton >
              <NotificationsActiveOutlinedIcon />
            </IconButton>
            <IconButton>
              <SettingsOutlinedIcon />
            </IconButton>
          </Box>
          { isLogIn === false ? 
          <Button variant="contained" onClick={handleLogin} color="success">
            Đăng nhập
            </Button>
            :
            <Box position="relative">
            <Button onClick={handleClick} variant="contained"  color="info" startIcon={ <PersonOutlineOutlinedIcon />}>
                Xin chào {fullname}
            </Button>
            <Collapse
              in={open}
              timeout="auto"
              unmountOnExit
              sx={{
                width: 'auto',
                position: 'absolute',
                backgroundColor: colors.blackCustom[800],
                top: '40px', 
                right: 0, 
                zIndex: 999,
                borderRadius: '3px',
                boxShadow: '0 2px 4px rgba(0,0,0,0.2)',
              }}
            >
              <List component="div" disablePadding>
              <ListItemButton onClick={LinkToProfile}>
                  <ListItemIcon>
                    <BadgeOutlinedIcon/>
                  </ListItemIcon>
                  <ListItemText primary="Thông tin cá nhân" primaryTypographyProps={{ noWrap: true }}/>
                </ListItemButton> 
                <hr width="90%" style={{borderColor:"#858585"}}></hr>
                <ListItemButton onClick={LinkToOrder}>
                  <ListItemIcon>
                    <PostAddOutlinedIcon/>
                  </ListItemIcon>
                  <ListItemText primary="Đơn hàng" primaryTypographyProps={{ noWrap: true }} />
                </ListItemButton>
                <hr width="90%" style={{borderColor:"#858585"}}></hr>
                <ListItemButton onClick={handleLogout}>
                  <ListItemIcon>
                    <LogoutOutlinedIcon />
                  </ListItemIcon>
                  <ListItemText primary="Đăng xuất" />
                </ListItemButton>
              </List>
            </Collapse>
          </Box>
            }
      
        </Box>
      </Box>
  
    )
    
}

export default Topbar;