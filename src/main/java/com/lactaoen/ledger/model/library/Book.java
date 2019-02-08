package com.lactaoen.ledger.model.library;

import java.util.List;
import java.util.Objects;

public class Book {

    private int bookId;
    private String name;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return bookId == book.bookId &&
                Objects.equals(name, book.name) &&
                Objects.equals(chapters, book.chapters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, name, chapters);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", chapters=" + chapters +
                '}';
    }
}
