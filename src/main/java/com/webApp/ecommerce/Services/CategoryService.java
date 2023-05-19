package com.webApp.ecommerce.Services;

import com.webApp.ecommerce.Exceptions.ResourceNotFoundException;
import com.webApp.ecommerce.Model.Category;
import com.webApp.ecommerce.Payloads.CategoryDto;
import com.webApp.ecommerce.Repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public Category dtoToCategory(CategoryDto categoryDto) {
        return this.modelMapper.map(categoryDto,Category.class);
    }

    public CategoryDto categoryToDTO(Category category) {
        return this.modelMapper.map(category,CategoryDto.class);
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.dtoToCategory(categoryDto);
        Category saveCategory = this.categoryRepository.save(category);
        return this.categoryToDTO(saveCategory);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        category.setTitle(categoryDto.getTitle());
        Category newCategory = this.categoryRepository.save(category);
        return this.categoryToDTO(newCategory);
    }

    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        return this.categoryToDTO(category);
    }

    public List<CategoryDto> getAllCategory() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map(this::categoryToDTO).collect(Collectors.toList());
    }

    public CategoryDto deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        CategoryDto showCategory = this.categoryToDTO(category);
        this.categoryRepository.delete(category);
        return showCategory;
    }

}
