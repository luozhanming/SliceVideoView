package com.example.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class SliceVideoView extends ViewGroup implements View.OnDragListener {

    private static final String TAG = SliceVideoView.class.getSimpleName();
    public static final int DRAG_EVENT = 2001;

    //分片数量
    private int sliceCount = 0;
    //分片矩形，用于记录位置
    private List<RectF> sliceRectFs;


    public SliceVideoView(Context context) {
        this(context, null);
    }

    public SliceVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliceVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.parseColor("#879321"));
        init();
        setOnDragListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 1080 / 1920;
        setMeasuredDimension(width, height);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            childAt.measure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            RectF rectF = sliceRectFs.get(i);
            child.layout((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        }
    }


    /**
     * 设置分片数目
     *
     * @param count 分片数量
     */
    public void changeSliceCount(int count) {
        if (count == sliceCount)
            return;
        sliceCount = count;
        sliceRectFs.clear();
        sliceRectFs = generatorRects(count);
        removeAllViews();
        for (int i = 0; i < count; i++) {
            RectF rectF = sliceRectFs.get(i);
            int width = (int) (rectF.right - rectF.left);
            int height = (int) (rectF.bottom - rectF.top);
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(width, height));
            frameLayout.setBackgroundColor(Color.argb(255, (i + 1) * (i + 1) * 8, (i + 1) * (i + 1) * 8, (i + 1) * (i + 1) * 8));
            addView(frameLayout);
        }
        requestLayout();
    }

    private List<RectF> generatorRects(int count) {
        List<RectF> rects = new ArrayList<>(count);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        if (count == 1) {
            RectF rectf = new RectF(0, 0, width, height);
            rects.add(rectf);
        } else if (count == 2) {
            int eachWidth = width / 2;
            int eachHeight = eachWidth * 1080 / 1920;
            int top = height / 2 - eachHeight / 2;
            for (int i = 0; i < 2; i++) {
                RectF rectF = new RectF(i * eachWidth, top, (i + 1) * eachWidth, top + eachHeight);
                rects.add(rectF);
            }
        } else if (count == 3) {
            int eachWidth = width / 2;
            int eachHeight = eachWidth * 1080 / 1920;
            for (int i = 0; i < 3; i++) {
                if (i == 0) {
                    int top = height / 2 - eachHeight / 2;
                    RectF rectF = new RectF(0, top, eachWidth, top + eachHeight);
                    rects.add(rectF);
                } else if (i == 1) {
                    int top = height / 2 - eachHeight;
                    RectF rectF = new RectF(eachWidth, top, eachWidth * 2, top + eachHeight);
                    rects.add(rectF);
                } else if (i == 2) {
                    int top = height / 2;
                    RectF rectF = new RectF(eachWidth, top, eachWidth * 2, top + eachHeight);
                    rects.add(rectF);
                }
            }
        } else if (count == 4) {
            int eachWidth = width / 2;
            int eachHeight = eachWidth * 1080 / 1920;
            for (int i = 0; i < 2; i++) {
                RectF rectF = new RectF(i * eachWidth, 0, (i + 1) * eachWidth, eachHeight);
                rects.add(rectF);
            }
            for (int i = 0; i < 2; i++) {
                RectF rectF = new RectF(i * eachWidth, eachHeight, (i + 1) * eachWidth, eachHeight * 2);
                rects.add(rectF);
            }
        } else if (count == 6) {
            int sEachHeight = height / 3;
            int sEachWidth = width / 3;
            int lEachWidth = width - sEachWidth;
            int lEachHeight = lEachWidth * 1080 / 1920;

            RectF rectF1 = new RectF(0, 0, lEachWidth, lEachHeight);
            rects.add(rectF1);

            RectF rectF2 = new RectF(lEachWidth, 0, lEachWidth + sEachWidth, sEachHeight);
            rects.add(rectF2);

            RectF rectF3 = new RectF(lEachWidth, sEachHeight, lEachWidth + sEachWidth, sEachHeight * 2);
            rects.add(rectF3);

            RectF rectF4 = new RectF(lEachWidth, sEachHeight * 2, lEachWidth + sEachWidth, sEachHeight * 3);
            rects.add(rectF4);

            RectF rectF5 = new RectF(sEachWidth, sEachHeight * 2, sEachWidth * 2, sEachHeight * 3);
            rects.add(rectF5);

            RectF rectF6 = new RectF(0, sEachHeight * 2, sEachWidth, sEachHeight * 3);
            rects.add(rectF6);
        } else if (count == 8) {
            int sEachHeight = height / 4;
            int sEachWidth = width / 4;
            int lEachWidth = sEachWidth * 3;
            int lEachHeight = sEachHeight * 3;
            RectF rectF1 = new RectF(0, 0, lEachWidth, lEachHeight);
            rects.add(rectF1);
            RectF rectF2 = new RectF(lEachWidth, 0, lEachWidth + sEachWidth, sEachHeight);
            rects.add(rectF2);
            RectF rectF3 = new RectF(lEachWidth, sEachHeight, lEachWidth + sEachWidth, lEachHeight * 2);
            rects.add(rectF3);
            RectF rectF4 = new RectF(lEachWidth, sEachHeight * 2, lEachWidth + sEachWidth, lEachHeight * 3);
            rects.add(rectF4);
            RectF rectF5 = new RectF(lEachWidth, sEachHeight * 3, lEachWidth + sEachWidth, lEachHeight * 4);
            rects.add(rectF5);
            RectF rectF6 = new RectF(lEachWidth - sEachWidth, sEachHeight * 3, lEachWidth, lEachHeight * 4);
            rects.add(rectF6);
            RectF rectF7 = new RectF(lEachWidth - sEachWidth * 2, sEachHeight * 3, lEachWidth - sEachWidth, lEachHeight * 4);
            rects.add(rectF7);
            RectF rectF8 = new RectF(lEachWidth - sEachWidth * 3, sEachHeight * 3, lEachWidth - sEachWidth * 2, lEachHeight * 4);
            rects.add(rectF8);
        } else {
            throw new IllegalArgumentException("No such support slice count.");
        }
        return rects;
    }

    private void init() {
        sliceRectFs = new ArrayList<>();
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }
}
