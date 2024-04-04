import { useEffect, useState } from "react";
import { Sidebar, Menu, MenuItem } from "react-pro-sidebar";
import { Box, IconButton, Typography, useTheme } from "@mui/material";
import { Link,useNavigate  } from "react-router-dom";
import { tokens } from "../theme";
import LightModeOutlinedIcon from "@mui/icons-material/LightModeOutlined";
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import PeopleIcon from "@mui/icons-material/PeopleOutlined";
import ContactsIcon from "@mui/icons-material/ContactsOutlined";
import PersonIcon from "@mui/icons-material/PersonOutlined";
import CalendarTodayIcon from "@mui/icons-material/CalendarTodayOutlined";
import InvoicesIcon from '@mui/icons-material/ReceiptOutlined';
import StackedLineChartOutlinedIcon from '@mui/icons-material/StackedLineChartOutlined';
import BarChartIcon from "@mui/icons-material/BarChartOutlined";
import PieChartIcon from "@mui/icons-material/PieChartOutlined";
import QuestionAnswerOutlinedIcon from '@mui/icons-material/QuestionAnswerOutlined';
import MenuIcon from "@mui/icons-material/MenuOutlined";
import avatar from "../assets/images/avatar1.png";
import MapOutlinedIcon from '@mui/icons-material/MapOutlined';
import HomeWorkOutlined from "@mui/icons-material/HomeWorkOutlined";

const fullname = localStorage.getItem('fullname');
const Item = ({title, to , icon, selected, setSelected})=>{
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const navigate = useNavigate();
    const handleClick = () => {
      setSelected(title);
      navigate(to); // Use history.push to navigate to the specified path
  };
    return (
      <MenuItem
      active={selected === title}
      style={{ color: colors.gray[100] }}
      onClick={handleClick} // Use handleClick instead of setSelected directly
      icon={icon}
  >
      <Typography>
          {title}
      </Typography>
  </MenuItem>
    );
};

const Sidebarr = (object) => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const [isCollapsed, setIsCollapsed] = useState(false);
    const [selected, setSelected] = useState("users");
    return (
        <>
            <Box 
                sx={{
                    "& .sidebar-inner": {
                        background: `${colors.primary[100]} !important`,
                    },
                    "& .icon-wrapper": {
                        backgroundColor: "transparent !important",
                    },
                    "& .inner-item": {
                        padding: "5px 35px 5px 15px !important",
                    },
                    "& .inner-item:hover": {
                        color: "#868dfb !important",
                    },
                    "& .menu-item.active": {
                        color: "#6870fa !important",
                    },
                }}
            >
                <Sidebar collapsed= {isCollapsed} backgroundColor={colors.primary[500]} borderRadius="10px" >
                    <Menu 
                        iconShape ="square"
                    >
                        <MenuItem 
                            onClick ={()=> setIsCollapsed(!isCollapsed)}
                            icon={isCollapsed? <MenuIcon/> : undefined}
                            style ={{
                                margin: "10px 0 0 0",
                                color: colors.gray[100]
                            }}
                        >
                            <Box
                                display="flex"
                                justifyContent="space-between"
                                alignItems="center"
                                ml="15px"
                            >
                                {!isCollapsed &&(<Typography variant="h3" color={colors.gray[100]}>
                                    ADMIN
                                </Typography>)}
                                <IconButton onClick={() => setIsCollapsed(!isCollapsed)}>
                                    <MenuIcon />
                                </IconButton>
                            </Box>
                        </MenuItem>

                        {!isCollapsed && (
                            <Box mb="25px">
                                <Box display="flex" justifyContent="center" alignItems="center">
                                    <img
                                        alt="profile-user"
                                        width="100px"
                                        height="100px"
                                        src={avatar}
                                        style={{ cursor: "pointer", borderRadius: "50%" }}
                                    />
                                </Box>
                                <Box textAlign="center">
                                    <Typography
                                        variant="h2"
                                        color={colors.gray[100]}
                                        fontWeight="bold"
                                        sx={{ m: "10px 0 0 0" }}
                                    >
                                        {/* {account.fullname} */}
                                        {fullname}
                                    </Typography>
                                </Box>
                            </Box>
                        )}

                        <Box paddingLeft={isCollapsed ?undefined : "10%"}>
                            <Item 
                                title = "VIEW"
                                to="#"
                                icon={<HomeOutlinedIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Typography variant="h6" color={colors.gray[300]} 
                                sx={{m: "15px 0 5px 20px"}}
                            >
                                Data
                            </Typography>
                            <Item 
                                title = "Manage Users"
                                to="/admin/users"
                                icon={<PeopleIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "Manage Products"
                                to="/admin/products?page=1&limit=100"
                                icon={<HomeWorkOutlined/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "Manage Orders"
                                to="/admin/orders"
                                icon={<ContactsIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "nothing Balances"
                                to="#"
                                icon={<InvoicesIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Typography variant="h6" color={colors.gray[300]} 
                                sx={{m: "15px 0 5px 20px"}}
                            >
                                Pages
                            </Typography>
                            <Item 
                                title = "Profile Form"
                                to="#"
                                icon={<PersonIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "Calendar"
                                to="#"
                                icon={<CalendarTodayIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "FAQ Page"
                                to="#"
                                icon={<QuestionAnswerOutlinedIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Typography variant="h6" color={colors.gray[300]} 
                                sx={{m: "15px 0 5px 20px"}}
                            >
                                Data
                            </Typography>
                            <Item 
                                title = "Bar Chart"
                                to="#"
                                icon={<BarChartIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "Pie Chart"
                                to="#"
                                icon={<PieChartIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "Line Chart"
                                to="#"
                                icon={<StackedLineChartOutlinedIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                            <Item 
                                title = "Geography Chart"
                                to="#"
                                icon={<MapOutlinedIcon/>} 
                                selected={selected} 
                                setSelected={setSelected}
                            />
                        </Box>
                    </Menu>
                </Sidebar>
            </Box>
        </>
    );
};

export default Sidebarr;
