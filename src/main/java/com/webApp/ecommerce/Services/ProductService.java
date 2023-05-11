package com.webApp.ecommerce.Services;

import com.webApp.ecommerce.Exceptions.ResourceNotFoundException;
import com.webApp.ecommerce.Model.Category;
import com.webApp.ecommerce.Model.Product;
import com.webApp.ecommerce.Model.User;
import com.webApp.ecommerce.Payloads.CategoryDto;
import com.webApp.ecommerce.Payloads.ProductDto;
import com.webApp.ecommerce.Payloads.ProductResponse;
import com.webApp.ecommerce.Payloads.UserDto;
import com.webApp.ecommerce.Repositories.CategoryRepository;
import com.webApp.ecommerce.Repositories.ProductRepository;
import com.webApp.ecommerce.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.productRepository=productRepository;
        this.modelMapper=modelMapper;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Product dtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductDescription(productDto.getProductDescription());
        product.setProductPrice(productDto.getProductPrice());
        product.setLive(productDto.getLive());
        product.setStock(productDto.getStock());
        product.setQuantity(productDto.getQuantity());
        product.setImageName(productDto.getImageName());

        Category category = new Category();
        category.setCategoryId(product.getCategory().getCategoryId());
        category.setTitle(product.getCategory().getTitle()); 
        product.setCategory(category);
        return product;
        //return this.modelMapper.map(productDto,Product.class);
    }

    public ProductDto productToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setQuantity(product.getQuantity());
        productDto.setLive(product.getLive());
        productDto.setStock(product.getStock());
        productDto.setProductDescription(product.getProductDescription());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setImageName(product.getImageName());
        productDto.setProductDescription(product.getProductDescription());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(product.getCategory().getCategoryId());
        categoryDto.setTitle(product.getCategory().getTitle());
        productDto.setCategoryDto(categoryDto);

        UserDto userDto = new UserDto();
        userDto.setFirstName(product.getUser().getFirstName());
        userDto.setLastName(product.getUser().getLastName());
        userDto.setEmail(product.getUser().getEmail());
        userDto.setPassWord(product.getUser().getPassWord());
        userDto.setAddress(product.getUser().getAddress());
        userDto.setGender(product.getUser().getGender());
        userDto.setUserId(product.getUser().getUserId());
        userDto.setPhoneNo(product.getUser().getPhoneNo());
        userDto.setAddedDate(product.getUser().getAddedDate());
        userDto.setActive(product.getUser().getActive());
        productDto.setUserDto(userDto); 


        return productDto;

       // return this.modelMapper.map(product,ProductDto.class);
    }

    public UserRepository getUserRepository() {
		return userRepository;
	}

	public ProductDto createProduct(ProductDto productDto, Integer userId, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","userId",userId));
        Product product = this.dtoToProduct(productDto);
       //System.out.println("category "+ category.getCategoryId());
        product.setCategory(category);
        product.setUser(user);
        System.out.println("user " + product.getCategory().getTitle());
        Product saveProduct = this.productRepository.save(product);
        return this.modelMapper.map(saveProduct, ProductDto.class);
       // return productToDto(saveProduct);
    }

    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setStock(productDto.getStock());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.getLive());
        product.setImageName(productDto.getImageName());
        product.setProductDescription(productDto.getProductDescription());

        Product product1 = this.productRepository.save(product);
        return this.productToDto(product1);
    }

    public ProductDto getProductById(Integer productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        return productToDto(product);
    }

    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.trim().equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        List<Product> pageProduct = page.getContent();
        List<ProductDto> productList = pageProduct.stream().map(this::productToDto).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productList);
        productResponse.setPageNumber(page.getNumber());
        productResponse.setPageSize(page.getSize());
        productResponse.setTotalPage(page.getTotalPages());
        productResponse.setLastPage(page.isLast());
       // List<Product> productList = this.productRepository.findAll();
        //return productList.stream().map(this::productToDto).collect(Collectors.toList());
        return productResponse;
    }

    public List<ProductDto> findByCategory(Integer categoryId) {
       Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryID",categoryId));
       List<Product> productList = this.productRepository.findByCategory(category);
       List<ProductDto> productDos = productList.stream().map(this::productToDto).collect(Collectors.toList());
       return productDos;
    }

    public List<ProductDto> findByUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        List<Product> productList = this.productRepository.findByUser(user);
        List<ProductDto> productDos = productList.stream().map(this::productToDto).collect(Collectors.toList());
        return  productDos;
    }

    public ProductDto deleteProduct(Integer productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productID",productId));
        ProductDto productDto = this.productToDto(product);
        this.productRepository.delete(product);
        return productDto;
    }
}
