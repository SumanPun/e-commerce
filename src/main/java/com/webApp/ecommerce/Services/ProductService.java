package com.webApp.ecommerce.Services;
import com.webApp.ecommerce.Exceptions.ResourceNotFoundException;
import com.webApp.ecommerce.Model.Category;
import com.webApp.ecommerce.Model.Product;
import com.webApp.ecommerce.Model.PromoCode;
import com.webApp.ecommerce.Payloads.ApiResponse;
import com.webApp.ecommerce.Payloads.CategoryDto;
import com.webApp.ecommerce.Payloads.ProductDto;
import com.webApp.ecommerce.Payloads.ProductResponse;
import com.webApp.ecommerce.Repositories.CategoryRepository;
import com.webApp.ecommerce.Repositories.ProductRepository;
import com.webApp.ecommerce.Repositories.PromoCodeRepository;
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
    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeService promoCodeService;

    public ProductService(ProductRepository productRepository, PromoCodeService promoCodeService ,ModelMapper modelMapper, CategoryRepository categoryRepository, PromoCodeRepository promoCodeRepository) {
        this.productRepository=productRepository;
        this.modelMapper=modelMapper;
        this.categoryRepository = categoryRepository;
        this.promoCodeRepository = promoCodeRepository;
        this.promoCodeService = promoCodeService;
    }

    public Product dtoToProduct(ProductDto productDto) {
        return this.modelMapper.map(productDto,Product.class);
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

        return productDto;
    }

	public ProductDto createProduct(ProductDto productDto, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));
        Product product = this.dtoToProduct(productDto);
        product.setCategory(category);
        Product saveProduct = this.productRepository.save(product);
        return productToDto(saveProduct);
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
        return productResponse;
    }

    public List<ProductDto> findByCategory(Integer categoryId) {
       Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category","categoryID",categoryId));
       List<Product> productList = this.productRepository.findByCategory(category);
       List<ProductDto> productCat = productList.stream().map(this::productToDto).collect(Collectors.toList());
       return productCat;
    }

    public ProductDto updateProductDiscount(Integer id, double discount) {
        Product product = this.productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("produt","productId", id));
        ProductDto productDto = this.productToDto(product);
        productDto.setProductPrice(product.getProductPrice() - discount/100 * product.getProductPrice());
        return productDto;
    }

    public ProductDto deleteProduct(Integer productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productID",productId));
        ProductDto productDto = this.productToDto(product);
        this.productRepository.delete(product);
        return productDto;
    }

    public List<ProductDto> searchProducts(String keywords) {
        List<Product> lists = this.productRepository.searchByKeywords(keywords);
        List<ProductDto> productLists = lists.stream().map((product) -> this.modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
        return productLists;
    }

    public Object applyPromoOnProduct(String promocode) {
        ApiResponse apiResponse = new ApiResponse();
        PromoCode code = this.promoCodeRepository.findByCode(promocode).orElseThrow(()-> new ResourceNotFoundException("Promocode","code: "+ promocode,0));
        if(promocode.equalsIgnoreCase(promocode)) {
                boolean valid = this.promoCodeService.promoCodeValid(promocode);
                if(!valid) {
                    apiResponse.setMessage("Invalid PromoCode");
                    apiResponse.setSuccess(false);
                    return apiResponse;
                }
                int productId = code.getProductId();
                double produtDiscount = code.getDiscountPercentage();
                ProductDto product = updateProductDiscount(productId,produtDiscount);
                return product;
        }
        apiResponse.setMessage("PromoCode doesNot match with exiting database");
        apiResponse.setSuccess(false);
        return apiResponse;
    }
}
