package com.webApp.ecommerce.Controller;
import com.webApp.ecommerce.Payloads.AppConstants;
import com.webApp.ecommerce.Payloads.ProductDto;
import com.webApp.ecommerce.Payloads.ProductResponse;
import com.webApp.ecommerce.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductController {

    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto, @PathVariable Integer userId,@PathVariable Integer categoryId) {
        ProductDto saveProduct = this.productService.createProduct(productDto,userId,categoryId);
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}/")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable Integer productId) {
        ProductDto updatedProduct = this.productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    @GetMapping("/{productId}/")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer productId) {
        ProductDto productDto = this.productService.getProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ProductResponse> getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER_STRING,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.PAGE_DIR_STRING,required = false) String sortDir
    ) {
        ProductResponse productResponse = this.productService.getAllProduct(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> findByCategory(@PathVariable int categoryId) {
        List<ProductDto> productDtos = this.productService.findByCategory(categoryId);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductDto>> findByUser(@PathVariable int userId) {
        List<ProductDto> productDtos = this.productService.findByUser(userId);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }

    @DeleteMapping("/{productId}/")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable int productId) {
        ProductDto productDto = this.productService.deleteProduct(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

}
