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
}
