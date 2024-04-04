package com.java.midtermShopWeb.services.category;

import com.java.midtermShopWeb.dtos.CategoryDTO;
import com.java.midtermShopWeb.models.Category;
import com.java.midtermShopWeb.models.Product;
import com.java.midtermShopWeb.repositories.CategoryRepository;
import com.java.midtermShopWeb.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Override
    public Category createCategory(CategoryDTO categoryDTO) throws Exception  {
        if(categoryRepository.existsByName(categoryDTO.getName())){
            throw new Exception(categoryDTO.getName()+" is already exist ");
        }
        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .build();
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(long id) {
       return categoryRepository.findById(id).orElseThrow(()->
               new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository
            .findAll()
            .stream().sorted(Comparator.comparing(Category::getId))
            .collect(Collectors.toList());
    }

    @Override
    public Category updateCategory(long categoryId, CategoryDTO category) {
        Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(()->
                new RuntimeException("Category not found"));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public Category deleteCategory(long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Product> products = productRepository.findByCategory(category);
        if(!products.isEmpty()){
            throw new IllegalStateException("Cannot delete category with associated products");
        }
        categoryRepository.deleteById(id);
        return category;
    }
}
