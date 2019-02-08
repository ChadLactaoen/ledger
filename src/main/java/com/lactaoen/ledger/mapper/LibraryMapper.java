package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.library.Book;
import com.lactaoen.ledger.model.library.Chapter;
import com.lactaoen.ledger.model.library.Word;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibraryMapper {

    private final SqlSession sqlSession;

    public LibraryMapper(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Book> getAllBooks() {
        return sqlSession.selectList("LibraryMapper.getAllBooks");
    }

    public List<Chapter> getTodoChapters() {
        return sqlSession.selectList("LibraryMapper.getTodoChapters");
    }

    public List<Word> getAllUnfinishedWords() {
        return sqlSession.selectList("LibraryMapper.getAllUnfinishedWords");
    }

    public int createBook(Book book) {
        return sqlSession.insert("LibraryMapper.createBook", book);
    }

    public int createChapter(Chapter chapter) {
        return sqlSession.insert("LibraryMapper.createChapter", chapter);
    }

    public int createWord(Word word) {
        return sqlSession.insert("LibraryMapper.createWord", word);
    }

    public int updateWord(Word word) {
        return sqlSession.update("LibraryMapper.updateWord", word);
    }
}
