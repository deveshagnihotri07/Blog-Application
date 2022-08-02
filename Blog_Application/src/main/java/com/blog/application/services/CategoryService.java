package com.blog.application.services;

import com.blog.application.payloads.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    //Create
    public CategoryDto createCategory(CategoryDto categoryDto);

    //Update
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

    //Delete
    public void deleteCategory(Integer categoryId);

    //Get
    public CategoryDto getCategory(Integer categoryId);

    //GetAll
    public List<CategoryDto> getAllCategory();

}
