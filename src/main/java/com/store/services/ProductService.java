package com.store.services;

import com.store.dtos.ProductDto;
import com.store.payloads.PageableResponse;


public interface ProductService {
    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto, String productId);

    void delete(String productId);

    ProductDto getSingleProduct(String productId);

    PageableResponse<ProductDto> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PageableResponse<ProductDto> getAllLiveProducts(int pageNumber, int pageSize, String sortBy, String sortDirection);

    PageableResponse<ProductDto> searchByTitle(String title, int pageNumber, int pageSize, String sortBy,
                                               String sortDirection);

    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);

    ProductDto updateCategoryOfExistingProject(String productId, String categoryId);

    PageableResponse<ProductDto> getAllProductOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection);


}
