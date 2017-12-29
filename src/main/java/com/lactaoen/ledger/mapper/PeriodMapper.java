package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Period;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PeriodMapper {

    private final SqlSession sqlSession;

    public PeriodMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Period> getAllPeriods() {
        return sqlSession.selectList("PeriodMapper.getAllPeriods");
    }

    public Period getPeriodById(int id) {
        return sqlSession.selectOne("PeriodMapper.getPeriodById", id);
    }

    public Integer getLastPeriodId() {
        return sqlSession.selectOne("PeriodMapper.getLastPeriodId");
    }

    public void deletePeriod(int id) {
        sqlSession.delete("PeriodMapper.deletePeriod", id);
    }
}
