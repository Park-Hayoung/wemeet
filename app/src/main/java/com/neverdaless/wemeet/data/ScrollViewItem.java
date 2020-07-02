package com.neverdaless.wemeet.data;

public class ScrollViewItem {
    int value;
    boolean selected;

    public ScrollViewItem(int value, boolean selected) {
        this.value = value;
        this.selected = selected;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
