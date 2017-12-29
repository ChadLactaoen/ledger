package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Category;
import com.lactaoen.ledger.model.form.CategoryForm;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {

    private final SqlSession sqlSession;

    public CategoryMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Category> getAllCategories() {
        return sqlSession.selectList("CategoryMapper.getAllCategories");
    }

    public List<Category> getAllChildCategories() {
        return sqlSession.selectList("CategoryMapper.getAllChildCategories");
    }

    public List<Category> getAllParentCategories() {
        return sqlSession.selectList("CategoryMapper.getAllParentCategories");
    }

    public Category getCategoryById(int id) {
        return sqlSession.selectOne("CategoryMapper.getCategoryById", id);
    }

    public Integer createCategory(CategoryForm category) {
        return sqlSession.insert("CategoryMapper.createCategory", category);
    }

    public void deleteCategory(int id) {
        sqlSession.delete("CategoryMapper.deleteCategory", id);
    }
}
