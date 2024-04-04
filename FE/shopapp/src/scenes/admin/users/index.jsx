import {
    Box,
    useTheme,
    InputBase,
    IconButton,
    TableContainer,
    TableRow,
    Paper,
    TableHead,
    TableCell,
    Button,
    Pagination,
    Typography,
    CircularProgress,
    Table,
    TableBody
  } from "@mui/material";
  import Header from "../../../components/Header";
  import { useEffect, useState } from "react";
  import { tokens } from "../../../theme";
  import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
  import { getAllUserByKeyWord, deleteOrActive } from "../../../utils/ApiFunction";
  
  const UserAdmin = () => {
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
    const [keyword, setKeyword] = useState("");
    const [users, setUsers] = useState([]);
    const [pages, setPages] = useState(1);
    const [currentPage, setCurrentPage] = useState(0);
    const [searchParams, setSearchParams] = useState({
      keyword: "",
      page: 0,
      limit: 50
    });
    const [loading, setLoading] = useState(false);
  
    const handlePageChange = (e, value) => {
      setCurrentPage(value);
    };
  
    const handleSearchParamChange = (e) => {
      const { name, value } = e.target;
      setSearchParams((prevState) => ({
        ...prevState,
        [name]: value,
      }));
    };
  
    const handleKWchange = (e) => {
     setSearchParams((prevState)=>({
        ...prevState,
        'keyword':e.target.value
     }))
    };
  
    const handleSearch = (e) => {
      e.preventDefault();
      setLoading(true);
      getUser();
    };
  
    const getUser = () => {
      const params = new URLSearchParams(searchParams);
      const queryString = params.toString();
      console.log(queryString);
      getAllUserByKeyWord(queryString).then((response) => {
        console.log(response);
        setLoading(false);
        if (response) {
          setUsers(response.users);
        }
      });
    };
  
    const handleKeyDown = (event) => {
      if (event.key === "Enter") {
        handleSearch(event);
      }
    };
  
    useEffect(() => {
      getUser();
    }, [currentPage]);
  
    return (
      <>
        <Box p="20px">
          <Header
            title="Quản lý người dùng"
            subtitle="Danh sách người dùng của hệ thống"
          />
          <Box
            display="flex"
            backgroundColor={colors.blackCustom[900]}
            color={colors.blackCustom[900]}
            borderRadius="3px"
            position="relative"
          >
            <InputBase
              sx={{ ml: 2, flex: 1 }}
              placeholder="Fullname or Email or Address or PhoneNumber"
              value={searchParams.keyword}
              onChange={handleKWchange}
              onKeyDown={handleKeyDown}
            />
            <IconButton type="button" sx={{ p: 1 }} onClick={handleSearch}>
              <SearchOutlinedIcon />
            </IconButton>
          </Box>
          <TableContainer
            component={Paper}
            sx={{ marginTop: "10px", maxHeight: "70vh", overflow: "auto" }}
          >
            <Table>
              <TableHead>
                <TableRow sx={{ padding: "10px", backgroundColor: colors.redAccent[600] }}>
                  <TableCell style={{ position: "sticky", top: 0, zIndex: 1, backgroundColor: colors.redAccent[600] }}>ID</TableCell>
                  <TableCell style={{ position: "sticky", top: 0, zIndex: 1, backgroundColor: colors.redAccent[600] }}>Họ tên</TableCell>
                  <TableCell style={{ position: "sticky", top: 0, zIndex: 1, backgroundColor: colors.redAccent[600] }}>SDT</TableCell>
                  <TableCell style={{ position: "sticky", top: 0, zIndex: 1, backgroundColor: colors.redAccent[600] }}>Địa chỉ</TableCell>
                  <TableCell style={{ position: "sticky", top: 0, zIndex: 1, backgroundColor: colors.redAccent[600] }}>Số đơn hàng</TableCell>
                  <TableCell style={{ position: "sticky", top: 0, zIndex: 1, backgroundColor: colors.redAccent[600] }}>Thao tác</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {loading ? (
                  <TableRow>
                    <TableCell colSpan={6} align="center">
                      <CircularProgress />
                    </TableCell>
                  </TableRow>
                ) : (
                  <>
                    {users.length > 0 ? (
                      users.map((user) => (
                        <TableRow key={user.id}>
                          <TableCell>{user.id}</TableCell>
                          <TableCell>{user.fullname}</TableCell>
                          <TableCell>{user.phone_number}</TableCell>
                          <TableCell>{user.address}</TableCell>
                          <TableCell>{user.number_of_orders}</TableCell>
                          <TableCell>
                            {user.is_active ? (
                              <Button variant="contained" color="error">
                                Khóa tài khoản
                              </Button>
                            ) : (
                              <Button variant="contained" color="success">
                                Mở tài khoản
                              </Button>
                            )}
                          </TableCell>
                        </TableRow>
                      ))
                    ) : (
                      <TableRow>
                        <TableCell colSpan={6} align="center">
                          <Typography variant="body1">Không tìm thấy người dùng.</Typography>
                        </TableCell>
                      </TableRow>
                    )}
                  </>
                )}
              </TableBody>
            </Table>
          </TableContainer>
          <Pagination
            count={pages}
            page={currentPage}
            onChange={handlePageChange}
            color="primary"
            size="large"
            shape="rounded"
            variant="outlined"
            sx={{
              padding: "10px",
              backgroundColor: colors.redAccent[600],
              color: colors.blackCustom[900],
              marginTop: "10px",
            }}
          />
        </Box>
      </>
    );
  };
  
  export default UserAdmin;
  