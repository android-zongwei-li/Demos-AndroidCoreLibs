package com.lizw.ui_demos.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

/**
 * 一个可以跟随手指移动的View。
 * <p>
 * 知识点：滑动
 * <p>
 * Q：实现滑动的方式有？
 * 1、onLayout()
 * 2、
 * <p>
 * author: zongwei.li created on: 2022/6/4
 */
public class FingerView extends View {
    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public FingerView(Context context) {
        this(context, null);
    }

    public FingerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FingerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取手指触摸点的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算移动的距离
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                // 方式一、调用 layout 方法来重新放置它的位置
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);
//
//                // 方式二：
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);
//
//                // 方式三：通过 margin 来改变位置
//                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                lp.leftMargin = getLeft() + offsetX;
//                lp.topMargin = getTop() + offsetY;

                // 把scroll想象成是作用到滚动条上，比较好理解。
                // scroll滚动的是view中的内容，而不是view本身。比如ViewGroup滚动的就是其中的子View。
                // 比如写代码的这个页面，滚动条向下滑100，那么其中的内容实际上是上移的。
//                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                // scrollTo是瞬间完成的
                ((View) getParent()).scrollTo(-200, -200);


                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        // 计算滚动距离
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 2000);
    }
}
