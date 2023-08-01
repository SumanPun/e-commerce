package com.webApp.ecommerce.Controller;
import com.webApp.ecommerce.Payloads.AppConstants;
import com.webApp.ecommerce.Payloads.ProductDto;
import com.webApp.ecommerce.Payloads.ProductResponse;
import com.webApp.ecommerce.Services.FileService;
import com.webApp.ecommerce.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    
    @Value("${project.image}")
    private String path;
    
    public ProductController(ProductService productService, FileService fileService) {
        this.productService = productService;
        this.fileService = fileService;
    }

    @PostMapping("/category/{categoryId}/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto,@PathVariable Integer categoryId) {
        ProductDto saveProduct = this.productService.createProduct(productDto,categoryId);
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    
    @PostMapping("/image/upload/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> uploadImage(@RequestParam MultipartFile photo, @PathVariable Integer productId)
    throws IOException{
    	ProductDto productDto = this.productService.getProductById(productId);
    	String fileName = this.fileService.uploadFile(path, photo);
        productDto.setImageName(fileName);
        ProductDto updateProduct = this.productService.updateProduct(productDto,productId);
        return new ResponseEntity<>(updateProduct,HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> findByCategory(@PathVariable int categoryId) {
        List<ProductDto> productDtos = this.productService.findByCategory(categoryId);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
    @DeleteMapping("/{productId}/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable int productId) {
        ProductDto productDto = this.productService.deleteProduct(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<ProductDto>> searchProduct(@PathVariable (name = "keywords") String keywords) {
        List<ProductDto> list = this.productService.searchProducts("%" + keywords + "%");
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PutMapping("/{productId}/discount")
    public ResponseEntity<ProductDto> productAfterDiscount(@PathVariable int productId, @RequestParam double discount) {
        ProductDto productDto = this.productService.updateProductDiscount(productId,discount);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }


    @GetMapping("/productPromo")
    public ResponseEntity<Object> applyPromoOnProduct(@RequestParam String promoCode) {
        Object productDto = this.productService.applyPromoOnProduct(promoCode);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

}
