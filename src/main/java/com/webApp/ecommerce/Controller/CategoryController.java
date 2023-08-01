package com.webApp.ecommerce.Controller;

import com.webApp.ecommerce.Payloads.CategoryDto;
import com.webApp.ecommerce.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> creatCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {
        CategoryDto categoryDtos = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }
    @GetMapping("/{categoryId}/")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllcategory() {
        List<CategoryDto> categoryDtos = this.categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }
    @DeleteMapping("/{categoryId}/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Integer categoryId) {
        CategoryDto categoryDto = this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
}
