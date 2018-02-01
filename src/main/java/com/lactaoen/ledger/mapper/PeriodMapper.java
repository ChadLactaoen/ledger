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

    public List<Period> getAllPeriodsLight() {
        return sqlSession.selectList("PeriodMapper.getAllPeriodsLight");
    }

    public Period getPeriodById(int id) {
        return sqlSession.selectOne("PeriodMapper.getPeriodById", id);
    }

    public Period getCurrentPeriod() {
        return sqlSession.selectOne("PeriodMapper.getCurrentPeriod");
    }

    public Integer getLastPeriodId() {
        return sqlSession.selectOne("PeriodMapper.getLastPeriodId");
    }

    public Integer createPeriod(Period period) {
        return sqlSession.insert("PeriodMapper.createPeriod", period);
    }

    public Integer updatePeriod(Period period) {
        return sqlSession.update("PeriodMapper.updatePeriod", period);
    }

    public void deletePeriod(int id) {
        sqlSession.delete("PeriodMapper.deletePeriod", id);
    }
}
