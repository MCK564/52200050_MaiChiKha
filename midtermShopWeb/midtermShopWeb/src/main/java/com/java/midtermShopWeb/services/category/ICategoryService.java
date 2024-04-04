package com.java.midtermShopWeb.services.category;



import com.java.midtermShopWeb.dtos.CategoryDTO;
import com.java.midtermShopWeb.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO category) throws Exception;
    Category getCategoryById(long id);
    List<Category> getAllCategories();
    Category updateCategory(long categoryId, CategoryDTO category);
    Category deleteCategory(long id) throws Exception;

}
