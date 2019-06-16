package com.kepependudukan.search.domain;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

public class Result {
    public int start;
    public int end;

    public Result(int start, int end) {

        this.start = start;
        this.end = end;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        return String.format("%d ~ %d", start, end);
    }

}
