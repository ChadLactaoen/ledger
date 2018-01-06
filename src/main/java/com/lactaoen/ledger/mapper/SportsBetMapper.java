package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.BetType;
import com.lactaoen.ledger.model.GameType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SportsBetMapper {

    private final SqlSession sqlSession;

    public SportsBetMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<BetType> getAllBetTypes() {
        return sqlSession.selectList("SportsBetMapper.getAllBetTypes");
    }

    public List<GameType> getAllGameTypes() {
        return sqlSession.selectList("SportsBetMapper.getAllGameTypes");
    }

}
