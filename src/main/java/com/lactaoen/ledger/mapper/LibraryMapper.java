package com.lactaoen.ledger.mapper;

import com.lactaoen.ledger.model.form.ChapterUpdateForm;
import com.lactaoen.ledger.model.library.Book;
import com.lactaoen.ledger.model.library.Chapter;
import com.lactaoen.ledger.model.library.UnfinishedWord;
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

    public String getChapterNameById(int chapterId) {
        return sqlSession.selectOne("LibraryMapper.getChapterNameById", chapterId);
    }

    public List<Book> getAllBooks() {
        return sqlSession.selectList("LibraryMapper.getAllBooks");
    }

    public List<Chapter> getTodoChapters() {
        return sqlSession.selectList("LibraryMapper.getTodoChapters");
    }

    public List<UnfinishedWord> getAllUnfinishedWords() {
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

    public int updateChapter(ChapterUpdateForm chapterUpdateForm) {
        return sqlSession.update("LibraryMapper.updateChapter", chapterUpdateForm);
    }

    public int updateWord(int wordId) {
        return sqlSession.update("LibraryMapper.updateWord", wordId);
    }
}
