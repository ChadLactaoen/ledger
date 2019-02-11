package com.lactaoen.ledger.model.library;

import java.util.List;
import java.util.Objects;

public class Book {

    private int bookId;
    private String name;
    private String status;
    private List<Chapter> chapters;

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public long getUnfinishedWordCount() {
        return chapters.stream().flatMap(chapter -> chapter.getWords().stream()).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return bookId == book.bookId &&
                Objects.equals(name, book.name) &&
                Objects.equals(status, book.status) &&
                Objects.equals(chapters, book.chapters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, status, chapters);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", chapters=" + chapters +
                '}';
    }
}
