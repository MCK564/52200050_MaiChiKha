import React, { useEffect, useState } from "react";
import {
  Box,
  Grid,
  InputBase,
  IconButton,
  RadioGroup,
  Radio,
  FormControlLabel,
  useTheme,
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  Typography,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  List,
  ListItem,
  ListItemText
} from "@mui/material";
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import Header from "../../../components/Header";
import { tokens } from "../../../theme";
import { getAllOrders, deleteOrder, changeStatus } from "../../../utils/ApiFunction";
import DoneOutlineIcon from '@mui/icons-material/DoneOutline';

const OrderAdmin = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [keyword, setKeyword] = useState("");
  const [status, setStatus] = useState("pending");
  const [orders, setOrders] = useState([]);
  const typeVietNamese = ['Đang xét duyệt', 'Đang vận chuyển', 'Đã giao', 'Đã hủy'];
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState(null);

  const handleClickOpenDialog = () => {
    setOpenDialog(true);
  }
  const handleSearch = () => {
    getOrder()
  };

  const handleKeyWordChange = (e) => {
    setKeyword(e.target.value);
  };
  const handleStatusOrderChange = (e) => {
    setStatus(e.target.value);
  }

  const handleStatusChange = (id, status) => {
    changeStatus(id, status).then((response) => {
      if(response){
        getOrder();
      }
    })
  };

  const getOrder = () => {
    getAllOrders(keyword, 0, 100, status).then((response) => {
      if (response) {
        setOrders(response.orders);
      }
    })
  }

  const handleSelectedOrderChange = (id) => {
    const selected = orders.find(order => order.id === id);
    setSelectedOrder(selected);
    handleClickOpenDialog();
  }

  const handleDelete = (id) => {
    deleteOrder(id).then((response) => {
      getOrder();
    })
  }

  useEffect(() => {
    getOrder();
  }, [status])

  console.log(orders);
  return (
    <>
      <Box p="20px">
        <Header
          title="QUẢN LÝ ĐƠN ĐẶT HÀNG"
          subtitle="Danh sách đơn hàng"
        />
        <Grid container spacing={2} alignItems="center">
          <Grid item xs={12} sm={6}>
            <Box
              display="flex"
              alignItems="center"
              bgcolor={colors.blackCustom[800]}
              borderRadius="3px"
              position="relative"
              height="fit-content"
            >
              <InputBase
                sx={{ ml: 2, flex: 1 }}
                placeholder="name || email || address"
                value={keyword}
                onChange={handleKeyWordChange}
              />
              <IconButton type="button" sx={{ p: 1 }} onClick={handleSearch}>
                <SearchOutlinedIcon />
              </IconButton>
            </Box>
          </Grid>
          <Grid item xs={12} sm={6}>
            <RadioGroup value={status} onChange={handleStatusOrderChange}>
              {[
                { value: "all", label: "Tất cả" },
                { value: "pending", label: "Chưa duyệt" },
                { value: "on_shipping", label: "Đang vận chuyển" },
                {
                  value: "waiting_to_be_shipped",
                  label: "Đang chờ giao",
                },
                { value: "completed", label: "Đã giao" },
                { value: "cancel", label: "Đã hủy" },
              ].map((item) => (
                <FormControlLabel
                  key={item.value}
                  value={item.value}
                  control={<Radio color="info" />}
                  label={item.label}
                />
              ))}
            </RadioGroup>
          </Grid>
        </Grid>
        <TableContainer style={{ maxHeight: 400 }}>
          <Table stickyHeader>
            <TableHead>
              <TableRow>
                <TableCell sx={{ color: "yellow", fontWeight: '600' }}>Đơn hàng</TableCell>
                <TableCell >Người nhận</TableCell>
                <TableCell align="center">Thời gian đặt</TableCell>
                <TableCell sx={{ color: "yellow", fontWeight: '600' }} align="center">Trạng thái</TableCell>
                <TableCell align="center">Tổng hóa đơn</TableCell>
                <TableCell sx={{ color: "yellow", fontWeight: '600' }} align="right">Thao Tác</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {orders && orders.map((order) => (
                <TableRow key={order.id} >
                  <TableCell sx={{cursor:"pointer"}}onClick={() => handleSelectedOrderChange(order.id)}>
                    <Typography value={order.id}>
                      {order.id}
                    </Typography>
                  </TableCell>
                  <TableCell>
                    {order.fullname}
                  </TableCell>
                  <TableCell align="center">
                    {order.order_date.join('/')}
                  </TableCell>
                  <TableCell align="center">
                    {order.status === 'pending' && typeVietNamese[0]}
                    {order.status === 'on_shipping' && typeVietNamese[1]}
                  </TableCell>
                  <TableCell align="center">
                    {order.total_money}
                  </TableCell>
                  <TableCell align="right" >

                    {order.status === 'pending' && <>
                      <Button variant="contained" color="error" onClick={() => handleDelete(order.id)}>
                        Hủy đơn hàng
                      </Button>
                      <Button variant="contained" color="success" onClick={() => handleStatusChange(order.id, 'on_shipping')} endIcon={<DoneOutlineIcon />}>
                        Duyệt
                      </Button>
                    </>}
                    {order.status === 'on_shipping' && <>
                      <Typography>
                        NO actions
                      </Typography>
                    </>}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

        <Dialog open={openDialog} onClose={() => setOpenDialog(false)}>
          <DialogTitle>Thông tin đơn hàng</DialogTitle>
          <DialogContent>
            <List>
              <ListItem>
                <ListItemText primary={`ID: ${selectedOrder ? selectedOrder.id : ''}`} />
              </ListItem>
              <ListItem>
                <ListItemText primary={`Người nhận: ${selectedOrder ? selectedOrder.fullname : ''}`} />
              </ListItem>
              <ListItem>
                <ListItemText primary={`Email: ${selectedOrder ? selectedOrder.email : ''}`} />
              </ListItem>
              <ListItem>
                <ListItemText primary={`Địa chỉ: ${selectedOrder ? selectedOrder.address : ''}`} />
              </ListItem>
              <ListItem>
                <ListItemText primary={`Thời gian đặt: ${selectedOrder ? selectedOrder.order_date.join('/') : ''}`} />
              </ListItem>
              <TableContainer>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Tên sản phẩm</TableCell>
                <TableCell>Số lượng</TableCell>
                <TableCell>Giá</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {selectedOrder &&
                selectedOrder.order_details.map((product) => (
                  <TableRow key={product.id}>
                    <TableCell>{product.product.name}</TableCell>
                    <TableCell>{product.numberOfProducts}</TableCell>
                    <TableCell>{product.price}</TableCell>
                  </TableRow>
                ))}
            </TableBody>
          </Table>
        </TableContainer>
            </List>
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpenDialog(false)} color="primary">
              Đóng
            </Button>
          </DialogActions>
        </Dialog>
      </Box>
    </>
  );
};

export default OrderAdmin;
