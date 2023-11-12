package com.store.services;

import com.store.dtos.CategoryDto;
import com.store.payloads.PageableResponse;

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto, String categoryId);

    PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDirection);

    CategoryDto getSingleCategory(String categoryId);

    void deleteCategory(String categoryId);

}
