package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Bet;
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

    public Bet getById(int id) {
        return sqlSession.selectOne("BetMapper.getBetById", id);
    }
}
