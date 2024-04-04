import { useEffect, useState } from "react";
import {
  getProductByKeyWord,
  getCategories,
  createProduct,
  deleteProduct,
} from "../../../utils/ApiFunction";
import Header from "../../../components/Header";
import {
  Box,
  TextField,
  Button,
  Grid,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Pagination,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  useTheme
} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import AddIcon from "@mui/icons-material/Add";
import { tokens } from "../../../theme";

const ProductAdmin = () => { 
    const theme = useTheme();
    const colors = tokens(theme.palette.mode);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [pages, setPages] = useState();
  const [currentPage, setCurrentPage] = useState(0);
  const [openDialog, setOpenDialog] = useState(false);
  const [dialogActionType, setDialogActionType] = useState("create");
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [searchParams, setSearchParams] = useState({
    keyword: "",
    category_id: "",
    priceFrom: 0,
    priceTo: 0,
    order: "",
    sortBy: "",
    page: 0,
    limit: 50,
  });

  const [formData, setFormData] = useState({
    id: "",
    name: "",
    price: 0,
    description: "",
    category_id: "",
    thumnail: "",
  });
  const handleSearchParamChange = (e) => {
    const { name, value } = e.target;
    setSearchParams((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleFormDataChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };
  const handleSearch = (e) => {
    e.preventDefault();
    search();
  };
  const search = () => {
    const params = new URLSearchParams(searchParams);
    const queryString = params.toString();
    getProductByKeyWord(queryString).then((response) => {
      if (response) {
        setProducts(response.products);
        setPages(response.totalPages);
      }
    });
  };

  useEffect(() => {
    getCategories().then((response) => {
      if (response) {
        setCategories(response);
      }
    });
    getProductByKeyWord("keyword=a&page=0&limit=50").then((response) => {
      if (response) {
        setProducts(response.products);
        setPages(response.totalPages);
      }
    });
  }, []);

  const handlePageChange = (e, value) => {
    setCurrentPage(value);
  };
  useEffect(() => {
    setSearchParams((prevState) => ({
      ...prevState,
      page: currentPage,
    }));
    search();
  }, [currentPage]);

  const handleDeleteProduct = (id) => {
    deleteProduct(id).then((response) => {
      console.log(response);
    });
  };

  const handleOpenDialog = (actionType, product = null) => {
    setOpenDialog(true);
    setDialogActionType(actionType);
    if (actionType === "update" && product) {
      setFormData((prevData) => {
        return {
          ...prevData,
          id: product.id,
          name: product.name,
          price: product.price,
          description: product.description,
          category_id: product.category_id,
          thumbnail: product.thumbnail,
        };
      });
    } else {
      setFormData({
        id: "",
        name: "",
        price: 0,
        description: "",
        category_id: "",
        thumbnail: "",
      });
    }
  };
  const handleCloseDialog = () => {
    setOpenDialog(false);
    setFormData({
      id: "",
      name: "",
      price: 0,
      description: "",
      category_id: "",
      thumbnail: "",
    });
  };

  const handleCreateOrUpdate = () => {
    console.log(formData);
    createProduct(formData).then((response) => {
     if(response.status===200){
        handleCloseDialog();
     }
    });
  };
  return (
    <>
      <Box p="20px">
        <Header
          title="Quản lý sản phẩm"
          subtitle="Danh sách sản phẩm của shop"
        />
        <form>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Keyword"
                name="keyword"
                value={searchParams.keyword}
                onChange={handleSearchParamChange}
                variant="outlined"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <FormControl fullWidth>
                <InputLabel>Category</InputLabel>
                <Select
                  name="category_id"
                  value={searchParams.category_id}
                  onChange={handleSearchParamChange}
                >
                  <MenuItem value="">All Categories</MenuItem>
                  {categories.map((category) => (
                    <MenuItem key={category.id} value={category.id}>
                      {category.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                type="number"
                label="Price From"
                name="priceFrom"
                value={searchParams.priceFrom || ""}
                onChange={handleSearchParamChange}
                variant="outlined"
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                type="number"
                label="Price To"
                name="priceTo"
                variant="outlined"
                value={searchParams.priceTo || ""}
                onChange={handleSearchParamChange}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Order"
                name="order"
                variant="outlined"
                value={searchParams.order}
                onChange={handleSearchParamChange}
                disabled
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Sort By"
                name="sortBy"
                variant="outlined"
                value={searchParams.sortBy}
                onChange={handleSearchParamChange}
                disabled
              />
            </Grid>
            <Grid item xs={12}>
              <Button
                variant="contained"
                color="success"
                type="button"
                endIcon={<SearchIcon />}
                onClick={handleSearch}
              >
                Search
              </Button>
              <Button
                sx={{ marginLeft: "10px" }}
                variant="contained"
                color="info"
                type="button"
                endIcon={<AddIcon />}
                onClick={() => handleOpenDialog("create")}
              >
                create
              </Button>
            </Grid>
          </Grid>
        </form>
       < TableContainer component={Paper} sx={{ marginTop: "10px", maxHeight: "70vh", overflow: "auto" }}>
  <Table>
    <TableHead>
      <TableRow sx={{padding:"10px", backgroundColor:colors.redAccent[600]}}>
        <TableCell style={{ position: "sticky", top: 0, zIndex: 1,backgroundColor:colors.redAccent[600] }}>ID</TableCell>
        <TableCell style={{ position: "sticky", top: 0, zIndex: 1,backgroundColor:colors.redAccent[600] }}>Tên sản phẩm</TableCell>
        <TableCell style={{ position: "sticky", top: 0, zIndex: 1,backgroundColor:colors.redAccent[600] }}>Đơn giá</TableCell>
        <TableCell style={{ position: "sticky", top: 0, zIndex: 1,backgroundColor:colors.redAccent[600] }}>Mô tả</TableCell>
        <TableCell style={{ position: "sticky", top: 0, zIndex: 1,backgroundColor:colors.redAccent[600] }}>Thao tác</TableCell>
      </TableRow>
    </TableHead>
    <TableBody>
      {products.map(product => (
        <TableRow key={product.id}>
          <TableCell>{product.id}</TableCell>
          <TableCell>{product.name}</TableCell>
          <TableCell>{product.price}</TableCell>
          <TableCell>{product.description}</TableCell>
          <TableCell>
            <Button variant="contained" color="error" onClick={() => handleDeleteProduct(product.id)}>
              Xóa
            </Button>
            <Button variant="contained" color="warning" onClick={() => handleOpenDialog("update", product)}>
              Chỉnh sửa
            </Button>
          </TableCell>
        </TableRow>
      ))}
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
          sx={{padding:"10px", backgroundColor:colors.redAccent[600],color:colors.blackCustom[900]}}
        />
     
        <Dialog open={openDialog} onClose={handleCloseDialog}>
          <DialogTitle textAlign="center" fontWeight="700" color={colors.blackCustom[100]}>
            {dialogActionType === "create"
              ? "CREATE PRODUCT"
              : "UPDATE PRODUCT"}
          </DialogTitle>
          <DialogContent>
            {dialogActionType === "update" && (
              <TextField
                label="ID"
                name="id"
                value={formData.id}
                fullWidth
                margin="normal"
                variant="outlined"
                disabled
              />
            )}
            <TextField
              label="Name"
              name="name"
              value={formData.name}
              onChange={handleFormDataChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Price"
              name="price"
              type="number"
              value={formData.price}
              onChange={handleFormDataChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Description"
              name="description"
              value={formData.description}
              onChange={handleFormDataChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />

            <FormControl fullWidth>
              <InputLabel>Category</InputLabel>
              <Select
                name="category_id"
                value={formData.category_id}
                onChange={handleFormDataChange}
              >
                <MenuItem value="">All Categories</MenuItem>
                {categories.map((category) => (
                  <MenuItem key={category.id} value={category.id}>
                    {category.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>

            <TextField
              label="Thumbnail"
              name="thumbnail"
              value={formData.thumbnail}
              onChange={handleFormDataChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
          </DialogContent>
          <DialogActions>
            <Button
              onClick={handleCloseDialog}
              variant="contained"
              color="error"
            >
              Cancel
            </Button>
            <Button
              onClick={handleCreateOrUpdate}
              variant="contained"
              color="success"
            >
              {dialogActionType}
            </Button>
          </DialogActions>
        </Dialog>
      </Box>
    </>
  );
};

export default ProductAdmin;
