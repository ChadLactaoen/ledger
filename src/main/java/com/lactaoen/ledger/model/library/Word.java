package com.lactaoen.ledger.model.library;

import java.sql.Date;
import java.util.Objects;

public class Word {

    private int wordId;
    private int chapterId;
    private String value;
    private boolean isComplete;
    private Date dateAdded;

    public Word() {
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return wordId == word.wordId &&
                chapterId == word.chapterId &&
                isComplete == word.isComplete &&
                Objects.equals(value, word.value) &&
                Objects.equals(dateAdded, word.dateAdded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordId, chapterId, value, isComplete, dateAdded);
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", chapterId=" + chapterId +
                ", value='" + value + '\'' +
                ", isComplete=" + isComplete +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
