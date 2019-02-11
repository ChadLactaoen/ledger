package com.lactaoen.ledger.model.library;

import java.util.List;
import java.util.Objects;

public class Chapter {

    private int chapterId;
    private int bookId;
    private String name;
    private boolean isRead;
    private boolean isExerciseDone;
    private boolean isExerciseAdded;
    private boolean isTodo;
    private List<Word> words;

    public Chapter() {
    }

    public Chapter(String name) {
        this.name = name;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
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

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isExerciseDone() {
        return isExerciseDone;
    }

    public void setExerciseDone(boolean exerciseDone) {
        isExerciseDone = exerciseDone;
    }

    public boolean isExerciseAdded() {
        return isExerciseAdded;
    }

    public void setExerciseAdded(boolean exerciseAdded) {
        isExerciseAdded = exerciseAdded;
    }

    public boolean isTodo() {
        return isTodo;
    }

    public void setTodo(boolean todo) {
        isTodo = todo;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chapter)) return false;
        Chapter chapter = (Chapter) o;
        return chapterId == chapter.chapterId &&
                bookId == chapter.bookId &&
                isRead == chapter.isRead &&
                isExerciseDone == chapter.isExerciseDone &&
                isExerciseAdded == chapter.isExerciseAdded &&
                isTodo == chapter.isTodo &&
                Objects.equals(name, chapter.name) &&
                Objects.equals(words, chapter.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapterId, bookId, name, isRead, isExerciseDone, isExerciseAdded, isTodo, words);
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId=" + chapterId +
                ", bookId=" + bookId +
                ", name='" + name + '\'' +
                ", isRead=" + isRead +
                ", isExerciseDone=" + isExerciseDone +
                ", isExerciseAdded=" + isExerciseAdded +
                ", isTodo=" + isTodo +
                ", words=" + words +
                '}';
    }
}
