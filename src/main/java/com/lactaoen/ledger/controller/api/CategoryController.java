package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.CategoryMapper;
import com.lactaoen.ledger.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryMapper categoryMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable("id") int id) {
        return categoryMapper.getCategoryById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public int createCategory(@RequestBody Category category) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public int updateCategory(@PathVariable("id") int id, @RequestBody Category category) {
        return 1;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable("id") int id) {

    }

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
}
