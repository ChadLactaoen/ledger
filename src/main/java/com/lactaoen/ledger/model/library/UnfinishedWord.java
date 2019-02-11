package com.lactaoen.ledger.model.library;

import java.util.Objects;

public class UnfinishedWord extends Word {

    private String chapterName;
    private String bookName;

    public UnfinishedWord() {
        super();
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnfinishedWord)) return false;
        if (!super.equals(o)) return false;
        UnfinishedWord that = (UnfinishedWord) o;
        return Objects.equals(chapterName, that.chapterName) &&
                Objects.equals(bookName, that.bookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), chapterName, bookName);
    }

    @Override
    public String toString() {
        return "UnfinishedWord{" +
                "chapterName='" + chapterName + '\'' +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
