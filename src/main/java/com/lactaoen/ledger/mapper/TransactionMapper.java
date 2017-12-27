package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Transaction;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper {

    private final SqlSession sqlSession;

    public TransactionMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Transaction> getAllTransactions() {
        return sqlSession.selectList("TransactionMapper.getAllTransactions");
    }

    public Transaction getTransactionById(int id) {
        return sqlSession.selectOne("TransactionMapper.getTransactionById", id);
    }
}
