package com.example.cabway.Utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class RecyclerViewUtils {
    public static void setHeightItemWise(Context context, RecyclerView recyclerView, int layoutId, int size) {
        LayoutInflater factory = LayoutInflater.from(context);
        View myView = factory.inflate(layoutId, null);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = (getViewHeight(myView) * size) + 100;
        recyclerView.setLayoutParams(params);
        recyclerView.setNestedScrollingEnabled(false);
    }


    private static int getViewHeight(View view) {
        WindowManager wm =
                (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = Objects.requireNonNull(wm).getDefaultDisplay();

        int deviceWidth;

        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight(); //        view.getMeasuredWidth();
    }
}
