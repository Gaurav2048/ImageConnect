package com.example.saurav.imageconnect.utils;

import android.view.View;

/**
 * Created by saurav on 6/26/2018.
 */

public class wedgitUtil {
    public int getHeightOfView(View contentview) {
        contentview.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //contentview.getMeasuredWidth();
        return contentview.getMeasuredHeight();
    }
}
