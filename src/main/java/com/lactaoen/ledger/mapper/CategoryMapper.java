package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Category;
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

    public Category getCategoryById(int id) {
        return sqlSession.selectOne("CategoryMapper.getCategoryById", id);
    }
}
