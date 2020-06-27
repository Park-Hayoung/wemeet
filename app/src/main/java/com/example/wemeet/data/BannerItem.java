package com.example.wemeet.data;

public class BannerItem {
    private int matchCount;
    private int resId;

    public BannerItem(int matchCount, int resId) {
        this.matchCount = matchCount;
        this.resId = resId;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
