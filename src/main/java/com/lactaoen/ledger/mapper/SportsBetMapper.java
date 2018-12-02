package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.BetType;
import com.lactaoen.ledger.model.GameType;
import com.lactaoen.ledger.model.SportsBet;
import com.lactaoen.ledger.model.form.BetForm;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

    public SportsBet getSportsBetByBetId(int id) {
        return sqlSession.selectOne("SportsBetMapper.getSportsBetByBetId", id);
    }

    public Integer createSportsBet(BetForm form) {
        return sqlSession.insert("SportsBetMapper.createSportsBet", form);
    }

    public Integer updateSportsBet(BetForm form) {
        return sqlSession.update("SportsBetMapper.updateSportsBet", form);
    }

    public void deleteSportsBet(int id) {
        sqlSession.delete("SportsBetMapper.deleteSportsBet", id);
    }

}
