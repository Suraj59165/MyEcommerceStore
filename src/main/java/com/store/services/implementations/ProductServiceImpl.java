package com.store.services.implementations;

import com.store.dtos.ProductDto;
import com.store.entitites.Category;
import com.store.entitites.Product;
import com.store.exceptions.ResourceNotFoundException;
import com.store.helper.Helper;
import com.store.payloads.PageableResponse;
import com.store.repositories.CategoryRepo;
import com.store.repositories.ProductRepo;
import com.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        productDto.setId(UUID.randomUUID().toString());
        Product product = this.modelMapper.map(productDto, Product.class);
        Product product2 = this.productRepo.save(product);
        return this.modelMapper.map(product2, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id " + productId));
        product.setTitle(productDto.getTitle());
        product.setAddedDate(productDto.getAddedDate());
        product.setDescription(productDto.getDescription());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setPrice(productDto.getPrice());
        product.setProductImageName(productDto.getProductImageName());
        return this.modelMapper.map(productRepo.save(product), ProductDto.class);

    }

    @Override
    public void delete(String productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id " + productId));
        productRepo.delete(product);

    }

    @Override
    public ProductDto getSingleProduct(String productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found with id " + productId));
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy,
                                                       String sortDirection) {
        Sort sort = (sortBy.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> page = productRepo.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy,
                                                           String sortDirection) {
        Sort sort = (sortBy.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepo.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String title, int pageNumber, int pageSize, String sortBy,
                                                      String sortDirection) {
        Sort sort = (sortBy.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepo.findByTitleContaining(title, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with given id " + categoryId));
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(UUID.randomUUID().toString());
        product.setAddedDate(LocalDateTime.now());
        product.setCategory(category);
        return modelMapper.map(productRepo.save(product), ProductDto.class);
    }

    @Override
    public ProductDto updateCategoryOfExistingProject(String productId, String categoryId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product  not found with given id " + productId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category  not found with given id " + categoryId));
        product.setCategory(category);
        return modelMapper.map(productRepo.save(product), ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getAllProductOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category  not found with given id " + categoryId));
        Sort sort = (sortBy.equalsIgnoreCase("desc") ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending()));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepo.findByCategory(category, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

}
