package com.store.services.implementations;

import com.store.dtos.CategoryDto;
import com.store.entitites.Category;
import com.store.exceptions.ResourceNotFoundException;
import com.store.helper.Helper;
import com.store.payloads.PageableResponse;
import com.store.repositories.CategoryRepo;
import com.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        categoryDto.setCategory_id(UUID.randomUUID().toString());
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category category2 = this.categoryRepo.save(category);
        return this.modelMapper.map(category2, CategoryDto.class);

    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("sorry category is not found with id " + categoryId));
        category.setCoverImage(categoryDto.getCoverImage());
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());

        return this.modelMapper.map(categoryRepo.save(category), CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy,
                                                          String sortDirection) {
        Sort sort = (sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending())
                : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepo.findAll(pageable);
        return Helper.getPageableResponse(page, CategoryDto.class);

    }

    @Override
    public CategoryDto getSingleCategory(String categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("sorry category is not found with id " + categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("sorry category is not found with id " + categoryId));
        categoryRepo.delete(category);

    }

}
