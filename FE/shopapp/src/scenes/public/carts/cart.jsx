import React, { useState, useEffect } from 'react';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography, Button, TextField, Checkbox, Dialog, DialogActions, DialogContent, DialogTitle, FormControl, Select, InputLabel, MenuItem, Grid } from '@mui/material';
import Header from "../../../components/Header";
import { getProductInCart, deleteCartProduct,addProductToCart,createNewOrder  } from "../../../utils/ApiFunction";
import MOMO from "../../../assets/images/momo.jpg";
import VISA from "../../../assets/images/visa.jpg";
import COD from "../../../assets/images/COD.jpg";


const Cart = () => {
  const [products, setProducts] = useState(null);
  const [selectedProducts, setSelectedProducts] = useState([]);
  const [selectAll, setSelectAll] = useState(false);
  const [totalMoney, setTotalMoney] = useState(0);
  const [openDialog, setOpenDialog] = useState(false);
  const [orderData, setOrderData] = useState({
    full_name: '',
    email: '',
    phone_number: '',
    note: '',
    total_money: 0,
    shipping_method: '',
    shipping_address: '',
    payment_method: '',

  });


  const getProducts = ()=>{
    getProductInCart().then((response) => {
        if (response) {
          setProducts(response.cartResponses);
        }
      });
  }
  useEffect(()=>{
    console.log(selectedProducts);
  },[selectedProducts])
  useEffect(() => {
    getProducts();
  }, []);

  useEffect(() => {
    calculateTotalMoney();
  }, [selectedProducts, products]);

  const handleDelete = (productId) => {
    deleteCartProduct(productId).then((response) => {
      if (response) {
        getProducts();
      }
      else {
        alert(response.data);
      }
    })
  };

  const onChangeQuantity = (productId, newQuantity) => {
   try{
        addProductToCart(productId,newQuantity).then((response)=>{
           if(response){
            getProducts();
           }
        })
   }catch(error){
    alert("Thay đổi không thành công");
   }
  };

  const handleCheckboxChange = (productId, productPrice) => {
    const selectedIndex = selectedProducts.indexOf(productId);
    let newSelected = [...selectedProducts];
    if (selectedIndex === -1) {
      newSelected.push(productId);
    } else {
      newSelected.splice(selectedIndex, 1);
    }
    setSelectedProducts(newSelected);
  };

  const handleSelectAllChange = (event) => {
    setSelectAll(event.target.checked);
    if (event.target.checked) {
      const allProductIds = products.map((product) => product.id);
      setSelectedProducts(allProductIds);
    } else {
      setSelectedProducts([]);
    }
  };

  const calculateTotalMoney = () => {
    let total = 0;
    selectedProducts.forEach((productId) => {
      const selectedProduct = products.find((product) => product.id === productId);
      if (selectedProduct) {
        total += selectedProduct.productResponse.price * selectedProduct.quantity;
      }
    });
    setTotalMoney(total);
    setOrderData({...orderData,['total_money']:total});
  };


  const openDialogClick = () => {
    if (selectedProducts.length === 0) {
      alert('Bạn chưa chọn sản phẩm nào hết');
      return;
    }
    setOpenDialog(true);
  }

  const closeDialogClick = () => {
    setOpenDialog(false);
  }

  const handlePayment = () => {
    const updatedOrderData = {
      ...orderData,
      cart_ids: selectedProducts,
      total_money: totalMoney + totalMoney / 10,
    };
    console.log(updatedOrderData);
    setOrderData(updatedOrderData);
    
    createNewOrder(updatedOrderData).then((response) => {
      if (response === 'create order successfully') {
        alert(response);
        getProducts();
      }
    });
    setOpenDialog(false);
  }
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setOrderData({ ...orderData, [name]: value });
  };
  return (
    <>
      <Box marginLeft="20px">
        <Header title='Giỏ hàng' subtitle='Đây là những món hàng bạn quan tâm' />
        <Box mt="10px" p="20px">
          <TableContainer>
            <Table>  
              <TableHead>
                <TableRow>
                  <TableCell>
                    <Checkbox
                      checked={selectAll}
                      onChange={handleSelectAllChange}
                      color="warning"
                    />
                  </TableCell>
                  <TableCell>Sản Phẩm</TableCell>
                  <TableCell align="right">Đơn Giá</TableCell>
                  <TableCell align="right">Số Lượng</TableCell>
                  <TableCell align="right">Thành Tiền</TableCell>
                  <TableCell align="right">Thao Tác</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {products &&
                  products.map((product) => (
                    <TableRow key={product.id}>
                      <TableCell>
                        <Checkbox
                          checked={selectedProducts.includes(product.id)}
                          onChange={() => handleCheckboxChange(product.id, product.price)}
                          color="warning"
                        />
                      </TableCell>
                      <TableCell component="th" scope="row">
                        {product.productResponse.name}
                      </TableCell>
                      <TableCell align="right">${product.productResponse.price.toFixed(2)}</TableCell>
                      <TableCell align="right">
                        <TextField
                          type="number"
                          value={product.quantity}
                          onChange={(e) => onChangeQuantity(product.productResponse.id, e.target.value)}
                          inputProps={{ min: 1 }}
                        />
                      </TableCell>
                      <TableCell align="right">${(product.productResponse.price * product.quantity).toFixed(2)}</TableCell>
                      <TableCell align="right">
                        <Button variant="contained" color="error" onClick={() => handleDelete(product.id)}>
                          Xóa
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))}
              </TableBody>
            </Table>
          </TableContainer>

          <Box display="flex" justifyContent="space-between" p="20px" >
            <Typography color="gold" variant='h3'>
              Tổng tiền : ${totalMoney.toFixed(2)}
            </Typography>

            <Button color="success" variant='contained' onClick={openDialogClick}>
              Thanh toán
            </Button>
          </Box>
        </Box>
      </Box>

      <Dialog open={openDialog} onClose={closeDialogClick}>
        <DialogTitle fontWeight="700" fontSize="20px" textAlign="center">ĐẶT HÀNG</DialogTitle>
        <DialogContent>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth name="full_name" label="Họ và tên" value={orderData.full_name} onChange={handleChange} />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth name="email" label="Email" value={orderData.email} onChange={handleChange} />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField fullWidth name="phone_number" label="SDT" value={orderData.phone_number} onChange={handleChange} />
            </Grid>
             
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth>
                <InputLabel id="payment-method-label">Phương thức vận chuyển</InputLabel>
                <Select
                  labelId="payment-method-label"
                  id="payment-method-select"
                  value={orderData.shipping_method}
                  label="Phương thức vận chuyển"
                  name="shipping_method"
                  onChange={handleChange}
                >
                  <MenuItem value="normal">Normal: ${parseFloat(totalMoney) / 10} </MenuItem>
                  <MenuItem value="express">Express: ${parseFloat(totalMoney) / 10 + 80}</MenuItem>
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} >
              <TextField fullWidth name="shipping_address" label="Địa chỉ nhận hàng" value={orderData.shipping_address} onChange={handleChange} />
            </Grid>
          
            <Grid item xs={12}>
              <TextField fullWidth name="note" label="Ghi chú" value={orderData.note} onChange={handleChange} />
            </Grid>
         


            <Grid item xs={12}>
              <FormControl fullWidth>
                <InputLabel id="payment-method-label">Phương thức thanh toán</InputLabel>
                <Select
                  labelId="payment-method-label"
                  id="payment-method-select"
                  value={orderData.payment_method}
                  label="Payment Method"
                  name="payment_method"
                  onChange={handleChange}
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
                  {products &&
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
                                <Typography fontWeight="500">
                                phí vận chuyển
                                </Typography>
                                </TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                            <TableCell align="right" >${(totalMoney/10).toFixed(2)}</TableCell>
                     </TableRow>
                     <TableRow>
                            <TableCell>
                                <Typography fontWeight="700">
                                TỔNG HÓA ĐƠN
                                </Typography>
                                </TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                            <TableCell align="right" >${(totalMoney+(totalMoney/10)).toFixed(2)}</TableCell>
                          </TableRow>
                   
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handlePayment} variant="contained" color="success">Đặt hàng</Button>
          <Button onClick={closeDialogClick} variant="contained" color="error">Đóng</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Cart;
