package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.GraphCoordinate;
import com.lactaoen.ledger.model.form.BetForm;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
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

    public List<GraphCoordinate<Integer, BigDecimal>> getBetDataPointsByYear(Integer year) {
        return sqlSession.selectList("BetMapper.getBetDataPointsByYear", year);
    }

    public Integer getCurrentWeekOfYear() {
        return sqlSession.selectOne("BetMapper.getCurrentWeekOfYear");
    }

    public List<GraphCoordinate<Integer, BigDecimal>> getBetDataPointsByYearAndGame(Integer year, String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("year", year);
        map.put("name", name);

        return sqlSession.selectList("BetMapper.getBetDataPointsByYearAndGame", map);
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
