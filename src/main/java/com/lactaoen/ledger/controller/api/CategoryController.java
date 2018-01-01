package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.CategoryMapper;
import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.form.CategoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractApiController {

    private CategoryMapper categoryMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @RequestMapping(value = "/child", method = RequestMethod.GET)
    public List<Category> getAllChildCategories() {
        return categoryMapper.getAllChildCategories();
    }

    @RequestMapping(value = "/parent", method = RequestMethod.GET)
    public List<Category> getAllParentCategories() {
        return categoryMapper.getAllParentCategories();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable("id") int id) {
        return categoryMapper.getCategoryById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public RedirectView createCategory(@ModelAttribute("category") CategoryForm category, RedirectAttributes model) {
        generateFlashAttributes(model, categoryMapper.createCategory(category), "category", PostType.ADD);
        return new RedirectView("/form/category");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateCategory(@PathVariable("id") int id, @ModelAttribute CategoryForm category, RedirectAttributes model) {
        category.setCategoryId(id);
        generateFlashAttributes(model, categoryMapper.updateCategory(category), "category", PostType.UPDATE);
        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCategory(@PathVariable("id") int id) {
        categoryMapper.deleteCategory(id);
    }

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }
}
