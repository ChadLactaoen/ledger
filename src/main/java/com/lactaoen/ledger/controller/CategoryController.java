package com.lactaoen.ledger.controller;

import com.lactaoen.ledger.model.Category;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable("id") int id) {
        return null;
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
}
