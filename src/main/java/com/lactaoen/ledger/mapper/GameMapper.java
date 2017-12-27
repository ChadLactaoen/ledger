package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Game;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameMapper {

    private final SqlSession sqlSession;

    public GameMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Game> getAllGames() {
        return sqlSession.selectList("GameMapper.getAllGames");
    }

    public Game getGameById(int id) {
        return sqlSession.selectOne("GameMapper.getGameById", id);
    }
}
