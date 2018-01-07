package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Game;
import com.lactaoen.ledger.model.form.GameForm;
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

    public List<Game> getPermissibleGames() {
        return sqlSession.selectList("GameMapper.getPermissibleGames");
    }

    public Boolean isSportsBet(int id) {
        return sqlSession.selectOne("GameMapper.isSportsBet", id);
    }

    public Game getGameById(int id) {
        return sqlSession.selectOne("GameMapper.getGameById", id);
    }

    public Integer createGame(GameForm game) {
        return sqlSession.insert("GameMapper.createGame", game);
    }

    public Integer updateGame(GameForm game) {
        return sqlSession.update("GameMapper.updateGame", game);
    }

    public void deleteGame(int id) {
        sqlSession.delete("GameMapper.deleteGame", id);
    }
}
