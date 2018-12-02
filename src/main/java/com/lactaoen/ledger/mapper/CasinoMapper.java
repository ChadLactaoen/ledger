package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Casino;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CasinoMapper {

    private final SqlSession sqlSession;

    public CasinoMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Casino> getAllCasinos() {
        return sqlSession.selectList("CasinoMapper.getAllCasinos");
    }

    public Casino getCasinoById(int id) {
        return sqlSession.selectOne("CasinoMapper.getCasinoById", id);
    }

    public Integer createCasino(Casino casino) {
        return sqlSession.insert("CasinoMapper.createCasino", casino);
    }

    public Integer updateCasino(Casino casino) {
        return sqlSession.update("CasinoMapper.updateCasino", casino);
    }

    public void deleteCasino(int id) {
        sqlSession.delete("CasinoMapper.deleteCasino", id);
    }
}
