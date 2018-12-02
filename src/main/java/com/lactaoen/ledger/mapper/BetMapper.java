package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.GraphCoordinate;
import com.lactaoen.ledger.model.form.BetForm;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BetMapper {

    private final SqlSession sqlSession;

    public BetMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Bet> getAllBets() {
        return sqlSession.selectList("BetMapper.getAllBets");
    }

    public List<Bet> getAllBetsByYear(Integer year) {
        return sqlSession.selectList("BetMapper.getAllBetsByYear", year);
    }

    public List<Bet> getUnresolvedBets() {
        return sqlSession.selectList("BetMapper.getUnresolvedBets");
    }

    public Bet getById(int id) {
        return sqlSession.selectOne("BetMapper.getBetById", id);
    }

    public List<GraphCoordinate<Integer, BigDecimal>> getBetDataPointsPerWeekByYear(Integer year) {
        return sqlSession.selectList("BetMapper.getBetDataPointsPerWeekByYear", year);
    }

    public List<GraphCoordinate<Integer, BigDecimal>> getBetDataPointsPerMonthByYear(Integer year) {
        return sqlSession.selectList("BetMapper.getBetDataPointsPerMonthByYear", year);
    }

    public Integer getCurrentWeekOfYear() {
        return sqlSession.selectOne("BetMapper.getCurrentWeekOfYear");
    }

    public Integer getCurrentMonthOfYear() {
        return sqlSession.selectOne("BetMapper.getCurrentMonthOfYear");
    }

    public List<GraphCoordinate<Integer, BigDecimal>> getBetDataPointsPerWeekByYearAndGame(Integer year, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("name", name);

        return sqlSession.selectList("BetMapper.getBetDataPointsPerWeekByYearAndGame", map);
    }

    public List<GraphCoordinate<Integer, BigDecimal>> getBetDataPointsPerMonthByYearAndGame(Integer year, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("name", name);

        return sqlSession.selectList("BetMapper.getBetDataPointsPerMonthByYearAndGame", map);
    }

    public Integer createBet(BetForm bet) {
        return sqlSession.insert("BetMapper.createBet", bet);
    }

    public Integer updateBet(BetForm bet) {
        return sqlSession.update("BetMapper.updateBet", bet);
    }

    public void deleteBet(int id) {
        sqlSession.delete("BetMapper.deleteBet", id);
    }
}
