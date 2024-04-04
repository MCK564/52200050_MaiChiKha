import { Box, Typography, CardActionArea, CardContent, CardMedia, Card, Dialog, DialogTitle, DialogContent, DialogActions, Button, Grid, TextField, FormControl, InputLabel, Select, MenuItem } from "@mui/material";
import { useEffect, useState } from "react";
import { getProductByKeyWord } from "../../../utils/ApiFunction";
import productImage from "../../../assets/images/whiteimage.jpg"
import { addProductToCart } from "../../../utils/ApiFunction";

const Product = () => {
    const [keyword, setKeyWord] = useState('');
    const [products, setProducts] = useState([]);
    const [isAfterSearch, setIsAfterSearch] = useState(false);
    const [selectedProduct, setSelectedProduct] = useState(null);
    const [openDialog, setOpenDialog] = useState(false);
    const [quantity, setQuantity] = useState(1);
    const [pages, setPages] = useState(0); // Initialize pages to 0
    const [currentPage, setCurrentPage] = useState(1);
    const [searchParams, setSearchParams] = useState({
        keyword: "",
        categoryId: "",
        priceFrom: 0,
        priceTo: 0,
        order: "",
        sortBy: "price",
        page: 1, // Initialize page to 1
        limit: 9, // Limit items per page
    });

    const handleSearchParamChange = (e) => {
        const { name, value } = e.target;
        setSearchParams((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        if (params) {
            getProductByKeyWord(params.toString())
                .then((response) => {
                    setProducts(response.products);
                    setPages(response.totalPages);
                    setSearchParams((prevState) => ({
                        ...prevState, 'keyword': params.get('keyword')
                    }));
                    setIsAfterSearch(true);
                })
        }
    }, [])
    const handleAddToCart = () => {
        addProductToCart(selectedProduct.id,quantity)
        .then((response)=>{
         if(response){
            alert("Đã thêm vào giỏ hàng");
         }
        })
         setQuantity(1);
         handleCloseDialog(); 
     };
    useEffect(() => {
        // Fetch products when searchParams change
        getProductByKeyWord(buildQueryString(searchParams))
            .then((response) => {
                setProducts(response.products);
            })
    }, [searchParams]);

    const buildQueryString = (params) => {
        const queryString = new URLSearchParams(params).toString();
        return queryString;
    };

    const handleOpenDialog = (product) => {
        setSelectedProduct(product);
        setOpenDialog(true);
    }

    const handleCloseDialog = () => {
        setOpenDialog(false);
    }

    const handleQuantityChange = (event) => {
        setQuantity(event.target.value);
    };

    const handleSearch = () => {
        setSearchParams((prevState) => ({
            ...prevState,
            page: 1 // Reset page to 1 when searching
        }));
    };

    const handleKeyPress = (event) => {
        if (event.key === 'Enter') {
            handleSearch();
        }
    };

    const handlePriceFromChange = (event) => {
        setSearchParams(prevState => ({
            ...prevState,
            'priceFrom': event.target.value
        }));
    };

    const handlePriceToChange = (event) => {
        setSearchParams(prevState => ({
            ...prevState,
            'priceTo': event.target.value
        }));
    };

    const handleSortByChange = (event) => {
        setSearchParams(prevState => ({
            ...prevState,
            'order': event.target.value
        }));

    };

    const handlePageChange = (newPage) => {
        setSearchParams(prevState => ({
            ...prevState,
            'page': newPage
        }));
        setCurrentPage(newPage);
    };

    return (
        <>
            <Box marginLeft="20px">
                {isAfterSearch && <Typography variant="h2" fontWeight="600" marginBottom="20px">
                    Kết quả tìm kiếm cho từ khóa '{searchParams.keyword}'
                </Typography>}

                <Typography variant="h3" fontWeight="500" p="10px">
                    Bộ lọc
                </Typography>
                <Box display="flex" p="15px" justifyContent="space-between">
                    <TextField
                        label="Giá từ"
                        type="number"
                        variant="outlined"
                        value={searchParams.priceFrom}
                        onChange={handlePriceFromChange}
                        onKeyDown={handleKeyPress}
                        color="warning"
                    />
                    <TextField
                        label="Giá đến"
                        type="number"
                        variant="outlined"
                        value={searchParams.priceTo}
                        onChange={handlePriceToChange}
                        onKeyDown={handleKeyPress}
                        color="warning"
                    />
                    <FormControl variant="outlined" sx={{ minWidth: 120 }}>
                        <InputLabel id="sort-by-label">Sắp xếp theo giá</InputLabel>
                        <Select
                            labelId="sort-by-label"
                            id="sort-by"
                            value={searchParams.order}
                            onChange={handleSortByChange}
                            label="Sắp xếp theo giá"
                            color="warning"
                        >
                            <MenuItem value="asc">Tăng dần</MenuItem>
                            <MenuItem value="desc">Giảm dần</MenuItem>
                        </Select>
                    </FormControl>
                    <Button onClick={handleSearch} variant="contained" color="warning">Lọc</Button>
                </Box>
                <Grid container spacing={2}>
                    {products.map((product, index) => (
                        <Grid item xs={12} sm={6} md={4} lg={3} key={index}>
                            <Card height="200px">
                                <CardActionArea onClick={() => handleOpenDialog(product)}>
                                    <CardMedia
                                        component="img"
                                        height="140"
                                        image={productImage}
                                        alt={product.name}
                                    />
                                    <CardContent>
                                        <Typography gutterBottom variant="h5" component="div">
                                            {product.name}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            {product.description}
                                        </Typography>

                                        <Typography variant="h3" color="red" fontWeight="700">
                                            $ {product.price}
                                        </Typography>
                                    </CardContent>
                                </CardActionArea>
                            </Card>
                        </Grid>
                    ))}
                </Grid>

                {/* Pagination */}
                <Box mt={3} display="flex" justifyContent="center" bgcolor="#fff">
                    {Array.from({ length: pages }, (_, i) => i + 1).map(page => (
                        <Button
                            key={page}
                            variant={currentPage === page ? "contained" : "outlined"}
                            color="primary"
                            onClick={() => handlePageChange(page)}
                        >
                            {page}
                        </Button>
                    ))}
                </Box>
            </Box>

            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>{selectedProduct && selectedProduct.name}</DialogTitle>
                <DialogContent>
                    <CardMedia
                        component="img"
                        height="200"
                        image={productImage}
                        alt={selectedProduct && selectedProduct.name}
                    />
                    <Typography>{selectedProduct && selectedProduct.description}</Typography>
                    <Typography variant="h3" color="red" fontWeight="700">
                        $ {selectedProduct && selectedProduct.price}
                    </Typography>
                    <Box mt={2} display="flex" justifyContent="space-between">
                        <Typography variant="h6">Số lượng</Typography>
                        <TextField
                            type="number"
                            variant="outlined"
                            value={quantity}
                            onChange={handleQuantityChange}
                            inputProps={{ min: 1, max: 10 }}
                        />
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleAddToCart} variant="contained" color="success">Thêm vào giỏ hàng</Button>
                    <Button onClick={handleCloseDialog} variant="contained" color="error">Đóng</Button>
                </DialogActions>
            </Dialog>
        </>
    )
}

export default Product;
