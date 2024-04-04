import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  headers: {
    "Content-Type": "application/json", // Default content type
  },
});

export const getToken = () =>{
  const token = localStorage.getItem("token");
    if (!token) {
      console.error("Token not found in localStorage");
      return false;
    }
    return token;
}
// login and get token from server, append token into localStorage
export async function login(phoneNumber, password, remember) {
  const formData = new FormData();
  formData.append("phone_number", phoneNumber);
  formData.append("password", password);

  try {
    const response = await api.post("/users/login", formData);
    if (response.status === 200) {
      const { token, id, roles, fullname, quantity } = response.data;
      if (token) {
        localStorage.setItem("token", token);
      }
      if (id) {
        localStorage.setItem("user_id", id);
      }
      if (roles && roles.length > 0) {
        localStorage.setItem("role", roles[0]);
      }
      if (fullname) {
        localStorage.setItem("fullname", fullname);
      }
      if (quantity) {
        localStorage.setItem("quantity", quantity);
      }
      return response.data;
    } else {
      return false;
    }
  } catch (error) {
    console.error("Error occurred during login:", error);
    return false;
  }
}

export async function getUserById(id) {
  try {
    const token = localStorage.getItem("token");
    const response = await api.get("/users/" + id, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.status === 200) {
      const user = response.data;
      return user;
    } else {
      return false;
    }
  } catch (error) {
    console.error("Error occurred while getting user by id:", error);
    return false;
  }
}

export async function isAdmin() {
  try {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("Token not found in localStorage");
      return false;
    }
    const response = await api.post("/users/check", null, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.status === 401) {
      return 401;
    } else if (response.status === 200) {
      return response.data;
    } else {
      console.error("Server responded with status:", response.status);
      return false;
    }
  } catch (error) {
    console.error("Error occurred while checking user by token", error);
    localStorage.clear();
    return false;
  }
}
export async function getQuantity() {
  try {
    const token = localStorage.getItem("token");
    if (!token) {
      console.error("Token not found in localStorage");
      return false;
    }
    const response = await api.get("/carts/quantity", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if(response.status===200){
      return response.data;
    }
    return false;
  } catch (error) {
    console.error("Error occurred while getting quantity", error);
    return false;
  }
}

// create new user
export async function register(user) {
  try {
    const response = await api.post("/users/register", user);
    if (response.status === 200) {
      return "/login";
    }
  } catch (error) {
    console.error("Registration error", error);
    return false;
  }
}

//get all products by the key word in the big search input, and page, and limits
export async function getProducts(params) {
  try {
    const response = await api.get("/products?" + params);
    if (response.status === 200) {
      return response;
    }
    return false;
  } catch (error) {
    throw new Error("Error fetching products");
    return false;
  }
}

export async function getProductByKeyWord(keyword) {
  try {
    const response = await api.get("/products?" + keyword);
    if (response.status === 200) {
      return response.data;
    }
    return 0;
  } catch (error) {
    return false;
  }
}


export async function addProductToCart(productId, quantity) {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            console.error("Token not found in localStorage");
            return false;
        }

        const formData = new FormData();
        formData.append("product_id", productId);
        formData.append("quantity", quantity);

        const response = await api.post("/carts", formData, {
            headers: { Authorization: `Bearer ${token}` },
        });
        
        if(response.status === 200){
            return true;
        }
        return false; 
    } catch (error) {
        console.error("Error occurred while adding product to cart:", error);
        return false; 
    }
}


export async function getProductInCart() {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }

    const response = await api.get("/carts", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response) {
      localStorage.setItem('quantity',response.data.quantity);
      return response.data; 
    } 
    return false;
  } catch (error) {
    console.error('Error fetching product in cart:', error);
    return false;
  }
}

export async function onChangeQuantity(quantity,cartId){
  try{
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }

    const response = await api.put(`/carts/${cartId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response) {
      return response.data; 
    } 

  }catch(error){
    return false;
  }
}

export async function deleteCartProduct(cartId){
  try{
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }

    const response = await api.delete(`/carts/${cartId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if(response.status === 200){
      return response;
    }
    else return false;
  }catch(error){
    return false;
  }
}

export async function createNewOrder(orderData){
  try{
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }
    const response = await api.post(`/orders`, orderData ,{
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response);
    if(response.status===200){
      return response.data;
    }
    return false;
  }catch(error){
    return false;
  }
}

// order
export async function getOrderByUser(type){
  try{
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }
    const response = await api.get(`/orders/${type}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if(response.status === 200){
      return response.data;
    }
    else return false;
  }catch(error){
    localStorage.clear();
    return false;
  }
}
// end order
//end global

//admin
// add a new product
export async function createProduct(product) {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }
    const response = await api.post("/products", product,{
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.status === 200) {
      return response;
    }
    return false;
  } catch (error) {
    console.error("created failed");
    return false;
  }
}

export async function getAllUserByKeyWord(keyword) {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }
    const response = await api.get(`/users/search?${keyword}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.status === 200) {
      return response.data;
    } else {
      return false;
    }
  } catch (error) {
    localStorage.clear();
    return false;
  }
}


export async function deleteOrActive(userId, active) {
  try{
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }
    const formData = new FormData();
    formData.append('id',userId);
    formData.append('active',active)
    const response = await api.get(`/block-or-enable`, formData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if(response.status === 200){
      return response.data;
    }
    else return false;
  }catch(error){
    localStorage.clear();
    return false;
  }
}

export async function deleteProduct(id) {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }

    const response = await api.delete(`/products/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 200) {
      return true; // Trả về true nếu xóa thành công
    } else {
      console.error('Delete request failed with status:', response.status);
      return false; // Trả về false nếu xóa không thành công
    }
  } catch (error) {
    console.error('Delete request failed with error:', error);
    return false; // Trả về false nếu có lỗi xảy ra
  }
}

// export async function getCategories(){
//   try{
//     const token = localStorage.getItem('token');
//     if (!token) {
//       console.error('Token not found in localStorage');
//       return false;
//     }
//     const response = await api.get('/categories',null,{
//       headers: {
//         Authorization: `Bearer ${token}`,
//       },
//     })
//   }catch(error){
//     console.lor(error);
//     return false;
//   }
// }

export async function getAllOrders(keyword, page, limit, status) {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }

    const response = await api.get(`/orders/search`, {
      params: {
        keyword: keyword,
        status: status,
        page: page,
        limit: limit
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 200) {
      return response.data; 
    }
  } catch (error) {
    console.error(error);
    return false;
  }
}

export async function changeStatus(orderId, status) {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }
    const formData = new FormData();
    formData.append('id',orderId);
    formData.append('status',status);
    const response = await api.post(`/orders/changing-status`, formData, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 200) {
      return response.data;
    } else {
      console.error('Failed to change order status:', response.statusText);
      return false;
    }
  } catch (error) {
    console.error('An error occurred while changing order status:', error);
    return false;
  }
}

export async function deleteOrder(id) {
  try {
    const token = localStorage.getItem('token');
    if (!token) {
      console.error('Token not found in localStorage');
      return false;
    }

    const response = await api.delete(`/orders/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.status === 200) {
      return response.data;
    } else {
      console.error('Failed to delete order:', response.statusText);
      return false;
    }
  } catch (error) {
    console.error('An error occurred while deleting order:', error);
    return false;
  }
}
export async function getCategories(){
  const response = await api.get('/categories?page=0&limit=100')
  if(response.status===200){
    return response.data;
  }
  return false;
}
//end admin
