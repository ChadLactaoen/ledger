package com.lactaoen.ledger.controller.api;

import com.lactaoen.ledger.mapper.CategoryMapper;
import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.form.CategoryForm;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractApiController {

    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @GetMapping("/child")
    public List<Category> getAllChildCategories() {
        return categoryMapper.getAllChildCategories();
    }

    @GetMapping("/parent")
    public List<Category> getAllParentCategories() {
        return categoryMapper.getAllParentCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") int id) {
        return categoryMapper.getCategoryById(id);
    }

    @PostMapping
    public RedirectView createCategory(@ModelAttribute("category") CategoryForm category, RedirectAttributes model) {
        generateFlashAttributes(model, categoryMapper.createCategory(category), "category", PostType.ADD);
        return new RedirectView("/form/category");
    }

    @PostMapping("/{id}")
    public RedirectView updateCategory(@PathVariable("id") int id, @ModelAttribute CategoryForm category, RedirectAttributes model) {
        category.setCategoryId(id);
        generateFlashAttributes(model, categoryMapper.updateCategory(category), "category", PostType.UPDATE);
        return new RedirectView("/");
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") int id) {
        categoryMapper.deleteCategory(id);
    }
}
