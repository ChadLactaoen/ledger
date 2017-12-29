package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Allocation;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllocationMapper {

    private final SqlSession sqlSession;

    public AllocationMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Allocation> getAllocationsByPeriodId(int id) {
        return sqlSession.selectList("AllocationMapper.getAllocationsByPeriodId", id);
    }

    public void deleteAllocation(int id) {
        sqlSession.delete("AllocationMapper.deleteAllocation", id);
    }
}
