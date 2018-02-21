package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.dashboard.CategoryExpenseMapper;
import com.lactaoen.ledger.model.dashboard.GameGamblingMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
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

    public List<Map<String, BigDecimal>> getParentCategorySpendingByYear(Integer year) {
        return sqlSession.selectList("DashboardMapper.getParentCategorySpendingByYear", year);
    }

    public List<Map<String, BigDecimal>> getParentCategorySpendingByPeriodId(Integer periodId) {
        return sqlSession.selectList("DashboardMapper.getParentCategorySpendingByPeriodId", periodId);
    }

    public List<GameGamblingMapper> getGameGamblingByYear(Integer year) {
        return sqlSession.selectList("DashboardMapper.getGameGamblingByYear", year);
    }

    public List<GameGamblingMapper> getGameGamblingByYearAndParentName(Integer year, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("name", name);

        return sqlSession.selectList("DashboardMapper.getGameGamblingByYearAndParentName", map);
    }

    public List<GameGamblingMapper> getCasinoGamblingByYearAndParentName(Integer year, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("name", name);

        return sqlSession.selectList("DashboardMapper.getCasinoGamblingByYearAndParentName", map);
    }

    public List<Bet> getBetsByYear(Integer year) {
        return sqlSession.selectList("DashboardMapper.getBetsByYear", year);
    }
}
