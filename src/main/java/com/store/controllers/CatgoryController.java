package com.store.controllers;

import com.store.dtos.CategoryDto;
import com.store.dtos.ProductDto;
import com.store.payloads.ApiResponseMessage;
import com.store.payloads.PageableResponse;
import com.store.services.CategoryService;
import com.store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CatgoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.create(categoryDto), HttpStatus.CREATED);
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(value = "category-id") String id, @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.update(categoryDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable(value = "category-id") String id) {
        categoryService.deleteCategory(id);
        ApiResponseMessage message = ApiResponseMessage.builder().message("category deleted with id" + id).status(HttpStatus.OK).success(true).build();
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pagesize, @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy, @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        System.out.println("running");
        return new ResponseEntity<PageableResponse<CategoryDto>>(categoryService.getAllCategories(pageNumber, pagesize, sortBy, sortDirection), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable(value = "categoryId") String id) {
        return new ResponseEntity<CategoryDto>(categoryService.getSingleCategory(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable(value = "categoryId") String categoryId, @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.createProductWithCategory(productDto, categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateProductWithCategory(@PathVariable(value = "categoryId") String categoryId, @PathVariable(value = "productId") String productId) {
        return new ResponseEntity<>(productService.updateCategoryOfExistingProject(productId, categoryId), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductsOfCategory(@PathVariable(value = "categoryId") String categoryId, @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pagesize, @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy, @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        return new ResponseEntity<>(productService.getAllProductOfCategory(categoryId, pageNumber, pagesize, sortBy, sortDirection), HttpStatus.OK);
    }

}
