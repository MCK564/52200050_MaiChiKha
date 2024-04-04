import { Box, Tab, Tabs, useTheme,Button, 
    Typography,Table,TableBody,TableContainer
    ,TableHead,TableRow,TableCell,CheckboxCheckbox,Dialog,DialogContent,DialogTitle
    ,DialogActions,Grid,TextField,FormControl, Select, MenuItem, InputLabel
} from "@mui/material";
import Header from "../../../components/Header";
import { useEffect, useState } from "react";
import { tokens } from "../../../theme";
import { getOrderByUser } from "../../../utils/ApiFunction";
import MOMO from "../../../assets/images/momo.jpg";
import VISA from "../../../assets/images/visa.jpg";
import COD from "../../../assets/images/COD.jpg";

const Order = () => {
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const [value, setValue] = useState(0);
  const [orderList, setOrderList] = useState([]);
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState('');
  const type = ['all','pending','on_shipping','waiting_to_be_shipped','completed','cancel','reimburse'];
  const typeVietNamese = ['Đang xét duyệt', 'Đang vận chuyển','Đã giao','Đã hủy'];
  const handleTabChange = (event, newValue) => {
    setValue(newValue);
  };
  
  useEffect(()=>{
    getOrderByUser(type[value]).then((response)=>{
       if(response){
        setOrderList(response.orders);
       }
       
    }).catch((error)=>{
        console.log("no no no");  
    })
  },[value])
  useEffect(()=>{
    if(localStorage.getItem('token')===null){
        window.location.href="/login";
    }
  },[])

  const handleDelete = (id)=>{
    console.log(id);
  }
  const closeDialogClick = () => {
    setOpenDialog(false);
  }
  const handleCancel = ()=>{
    console.log("");
  }
  const handleSelectedOrderChange = (event , id)=>{
    console.log(id);
  }
  return (
    <Box p="20px">
      <Header
        title="Thông tin đơn hàng"
        subtitle="Đây là những hóa đơn của bạn"
      />
      <Box width="100%" bgcolor={colors.redAccent[400]} borderRadius="15px" p="10px">
        <Tabs
          value={value}
          onChange={handleTabChange}
          variant="scrollable"
          scrollButtons="auto"
          aria-label="scrollable auto tabs example"
          
        >
          <Tab sx={{fontSize:"16px",fontWeight:"600"}} label="Tất cả" />
          <Tab sx={{fontSize:"16px",fontWeight:"600"}} label="Chờ thanh toán" />
          <Tab sx={{fontSize:"16px",fontWeight:"600"}} label="Vận chuyển" />
          <Tab sx={{fontSize:"16px",fontWeight:"600"}} label="Chờ giao hàng" />
          <Tab sx={{fontSize:"16px",fontWeight:"600"}} label="Hoàn thành" />
          <Tab sx={{fontSize:"16px",fontWeight:"600"}} label="Đã hủy" />
          <Tab sx={{fontSize:"16px",fontWeight:"600"}} label="Trả hàng/Hoàn tiền" />
        </Tabs>
      </Box>
      <hr style={{width:"85%",alignItems:"center"}}></hr>
         <Box mt="10px" p="20px">
          <TableContainer>
            <Table>  
              <TableHead>
                <TableRow>
                  <TableCell sx={{color:"yellow", fontWeight:'600'}}>Đơn hàng</TableCell>
                  <TableCell align="center">Thời gian đặt</TableCell>
                  <TableCell  sx={{color:"yellow", fontWeight:'600'}} align="center">Trạng thái</TableCell>
                  <TableCell align="center">Tổng hóa đơn</TableCell>
                  <TableCell  sx={{color:"yellow", fontWeight:'600'}} align="right">Thao Tác</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {orderList &&
                  orderList.map((order) => (
                    <TableRow key={order.id} sx={{cursor:"pointer"}}  onClick={()=>handleSelectedOrderChange(order.id)}>
                        <TableCell>
                            <Typography  value={order.id}>
                                {order.id}
                            </Typography>
                        </TableCell >
                        <TableCell align="center">
                            {order.order_date.join('/')}
                        </TableCell>
                        <TableCell align = "center">
                            {order.status === 'pending' && typeVietNamese[0]}
                            {order.status === 'on_shipping' && typeVietNamese[1]}
                        </TableCell>
                        <TableCell align="center">
                      {order.total_money.toFixed(2)}
                        </TableCell>
                      <TableCell align="right">
                       {order.status === 'pending' &&  <Button variant="contained" color="error" onClick={() => handleDelete(order.id)}>
                          Hủy đơn hàng
                        </Button>}
                        {order.status === 'on_shipping' && <Button variant="contained" color="warning" disabled>
                          WAiting
                        </Button> }
                      </TableCell>
                    </TableRow>
                  ))}
              </TableBody>
            </Table>
          </TableContainer>

          <Box display="flex" justifyContent="space-between" p="20px" >
            <Typography color="gold" variant='h3'>
              {/* Tổng tiền : ${totalMoney.toFixed(2)} */}
            </Typography>
          </Box>
        </Box>

        <Dialog open={openDialog} onClose={closeDialogClick}>
        <DialogTitle fontWeight="700" fontSize="20px" textAlign="center">ĐẶT HÀNG</DialogTitle>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth name="full_name" label="Họ và tên"  />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth name="email" label="Email" />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth name="phone_number" label="SDT" />
            </Grid>
             
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth>
                <InputLabel id="payment-method-label">Phương thức vận chuyển</InputLabel>
                <Select
                  labelId="payment-method-label"
                  id="payment-method-select"
                 
                  label="Phương thức vận chuyển"
                  name="shipping_method"
                  
                >
                  <MenuItem value="normal">Normal: ${parseFloat(orderList) / 10} </MenuItem>
                  <MenuItem value="express">Express: ${parseFloat(orderList) / 10 + 80}</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} >
              <TextField fullWidth name="shipping_address" label="Địa chỉ nhận hàng"  />
            </Grid>
          
            <Grid item xs={12}>
              <TextField fullWidth name="note" label="Ghi chú"  /> 
            </Grid>
         
            <Grid item xs={12}>
              <FormControl fullWidth>
                <InputLabel id="payment-method-label">Phương thức thanh toán</InputLabel>
                <Select
                  labelId="payment-method-label"
                  id="payment-method-select"
               
                  label="Payment Method"
                  name="payment_method"
                >
                  <MenuItem value="momo" >
                    <img src={MOMO} alt="Momo" style={{ width: '30px', marginRight: '10px' }} /> MOMO
                  </MenuItem>
                  <MenuItem value="visa">
                    <img src={VISA} alt="VISA" style={{ width: '30px', marginRight: '10px' }} /> VISA
                  </MenuItem>
                  <MenuItem value="cod">
                    <img src={COD} alt="COD" style={{ width: '30px', marginRight: '10px' }} /> COD
                  </MenuItem>
                </Select>
              </FormControl>
            </Grid>
          </Grid>

          <Box mt={4}>
            <Typography variant="h6">Danh sách sản phẩm đã chọn:</Typography>
            <TableContainer>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Tên Sản Phẩm</TableCell>
                    <TableCell align="right">Đơn Giá</TableCell>
                    <TableCell align="right">Số Lượng</TableCell>
                    <TableCell align="right">Thành Tiền</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {/* {products &&
                    products.map((product) => {
                      if (selectedProducts.includes(product.id)) {
                        return (
                          <TableRow key={product.id}>
                            <TableCell>{product.productResponse.name}</TableCell>
                            <TableCell align="right">${product.productResponse.price.toFixed(2)}</TableCell>
                            <TableCell align="right">{product.quantity}</TableCell>
                            <TableCell align="right">${(product.productResponse.price * product.quantity).toFixed(2)}</TableCell>
                          </TableRow>
                        );
                      }
                      return null;
                    })}
                     <TableRow>
                            <TableCell>
                                <Typography fontWeight="700">
                                TỔNG HÓA ĐƠN
                                </Typography>
                                </TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                            <TableCell align="right" >${totalMoney}</TableCell>
                          </TableRow>
                    */}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCancel} variant="contained" color="success">Hủy đơn hàng</Button>
          <Button onClick={closeDialogClick} variant="contained" color="error">Đóng</Button>
        </DialogActions>
      </Dialog>
    
    </Box>
    
  );
};

export default Order;
