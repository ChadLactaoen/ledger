package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.dashboard.CategoryExpenseMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class DashboardMapper {

    private final SqlSession sqlSession;

    public DashboardMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<CategoryExpenseMapper> getCategoryExpensesByYear(Integer year) {
        return sqlSession.selectList("DashboardMapper.getCategoryExpensesByYear", year);
    }

    public List<Map<String, BigDecimal>> getParentCategorySpendingByPeriodId(Integer periodId) {
        return sqlSession.selectList("DashboardMapper.getParentCategorySpendingByPeriodId", periodId);
    }
}
