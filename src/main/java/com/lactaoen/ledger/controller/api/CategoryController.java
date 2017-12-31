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
public class CategoryController {

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
        if (categoryMapper.createCategory(category) != 0) {
            model.addFlashAttribute("msgClass", "success");
            model.addFlashAttribute("msg", "The following category was added successfully: " + category.getName());
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "There was an issue adding the category: " + category.getName());
        }

        return new RedirectView("/");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public RedirectView updateCategory(@PathVariable("id") int id, @ModelAttribute CategoryForm category, RedirectAttributes model) {
        category.setCategoryId(id);
        if (categoryMapper.updateCategory(category) != 0) {
            model.addFlashAttribute("msgClass", "success");
            model.addFlashAttribute("msg", "The following category was updated successfully: " + category.getName());
        } else {
            model.addFlashAttribute("msgClass", "danger");
            model.addFlashAttribute("msg", "There was an issue updating the category: " + category.getName());
        }

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
