package com.numetriclabz.fbfriendslist.widget;

import android.content.Context;
import android.graphics.Color;
import android.widget.Checkable;
import android.widget.FrameLayout;

public class CheckableLayout extends FrameLayout implements Checkable {
    private boolean mChecked;

    public CheckableLayout(Context context) {
        super(context);
    }


    public void setChecked(boolean checked) {
        mChecked = checked;

        setBackgroundColor(checked ? Color.parseColor("#64B5F6") : Color.parseColor("#FFFFFF"));
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void toggle() {
        setChecked(!mChecked);
    }

}