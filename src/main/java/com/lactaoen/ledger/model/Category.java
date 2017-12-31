package com.lactaoen.ledger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lactaoen.ledger.model.form.CategoryForm;

public class Category {

    private Integer categoryId;
    private String name;
    private Category parentCategory;
    private String color;

    public Category() {
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @JsonIgnore
    public CategoryForm toCategoryForm() {
        CategoryForm form = new CategoryForm();
        form.setCategoryId(categoryId);
        form.setName(name);
        form.setColor(color);
        if (parentCategory != null) {
            form.setParentId(parentCategory.getCategoryId());
        }
        return form;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", parentCategory=" + parentCategory +
                ", color='" + color + '\'' +
                '}';
    }
}
