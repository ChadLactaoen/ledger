package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.Transaction;
import com.lactaoen.ledger.model.form.TransactionForm;
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

    public Integer createTransaction(TransactionForm transaction) {
        return sqlSession.insert("TransactionMapper.createTransaction", transaction);
    }

    public Integer updateTransaction(TransactionForm transaction) {
        return sqlSession.update("TransactionMapper.updateTransaction", transaction);
    }

    public void deleteTransaction(int id) {
        sqlSession.delete("TransactionMapper.deleteTransaction", id);
    }
}
