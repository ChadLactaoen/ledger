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

    public List<Allocation> getAllAllocations() {
        return sqlSession.selectList("AllocationMapper.getAllAllocations");
    }

    public Allocation getAllocationById(int id) {
        return sqlSession.selectOne("AllocationMapper.getAllocationById", id);
    }
}
