package com.lactaoen.ledger.model.form;

public class ChapterUpdateForm {

    private int chapterId;
    private String field;
    private boolean value;

    public ChapterUpdateForm() {
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
