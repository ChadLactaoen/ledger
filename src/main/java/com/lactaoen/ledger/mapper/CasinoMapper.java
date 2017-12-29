package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Casino;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CasinoMapper {

    private final SqlSession sqlSession;

    public CasinoMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Casino> getAllCasinos() {
        return sqlSession.selectList("CasinoMapper.getAllCasinos");
    }

    public Casino selectCasinoById(int id) {
        return sqlSession.selectOne("CasinoMapper.getCasinoById", id);
    }

    public Integer createCasino(Casino casino) {
        return sqlSession.insert("CasinoMapper.createCasino", casino);
    }

    public void deleteCasino(int id) {
        sqlSession.delete("CasinoMapper.deleteCasino", id);
    }
}
