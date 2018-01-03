package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Allocation;
import com.lactaoen.ledger.model.form.AllocationForm;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AllocationMapper {

    private final SqlSession sqlSession;

    public AllocationMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Allocation> getAllocationsByPeriodId(int id) {
        return sqlSession.selectList("AllocationMapper.getAllocationsByPeriodId", id);
    }

    public Allocation getAllocationByDateAndCategoryId(Date date, int categoryId) {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("categoryId", categoryId);

        return sqlSession.selectOne("AllocationMapper.getAllocationByDateAndCategoryId", map);
    }

    public Integer createAllocation(AllocationForm allocation) {
        return sqlSession.insert("AllocationMapper.createAllocation", allocation);
    }

    public Integer updateAllocation(AllocationForm allocation) {
        return sqlSession.update("AllocationMapper.updateAllocation", allocation);
    }

    public void deleteAllocation(Map<String, Integer> map) {
        sqlSession.delete("AllocationMapper.deleteAllocation", map);
    }
}
