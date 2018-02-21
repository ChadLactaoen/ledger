package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Bet;
import com.lactaoen.ledger.model.form.BetForm;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

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
