package com.example.saurav.imageconnect.utils;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * Created by saurav on 6/26/2018.
 */

public class ShadowOutline extends ViewOutlineProvider {
    int width;
    int height;
    ShadowOutline(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRect(0, 0, width, height);

    }
}
