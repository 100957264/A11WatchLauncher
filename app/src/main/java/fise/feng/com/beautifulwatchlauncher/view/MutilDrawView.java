package fise.feng.com.beautifulwatchlauncher.view;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by qingfeng on 2017/12/18.
 */

public class MutilDrawView extends ViewGroup{
    private static final int MIN_DRAWER_MARGIN = 0; // dp
    /**
     * Minimum velocity that will be detected as a fling
     */
    private static final int MIN_FLING_VELOCITY = 400; // dips per second

    /**
     * drawer离父容器右边的最小外边距
     */
    private int mMinDrawerMargin;

    private ViewGroup mMenuView;
    private View mContentView;

    private ViewDragHelper mDragHelper;
    /**
     * drawer显示出来的占自身的百分比
     */
    private float mTopMenuOnScreen;

    private boolean canPull = false;

    public MutilDrawView(Context context) {
        this(context, null);
    }

    public MutilDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MutilDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        float density = getResources().getDisplayMetrics().density;
        float minVel = MIN_FLING_VELOCITY * density;  //1200
        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN * density + 0.5f);

        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //捕获该view
                return child == mMenuView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return 0;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int newTop = Math.max(-child.getHeight(), Math.min(top, 0));
                return newTop;
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {

                int childHeight = releasedChild.getHeight();
                //0~1f
                float offset = (childHeight + releasedChild.getTop()) * 1.0f / childHeight;

                mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), yvel > 0 || yvel == 0 && offset > 0.5f ? 0 : -childHeight);
                invalidate();
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                if (isCanPull()) {
                    mDragHelper.captureChildView(mMenuView, pointerId);
                }
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                if (isCanPull()) {
                    mDragHelper.captureChildView(mMenuView, pointerId);
                }
            }

//            @Override
//            public boolean onEdgeLock(int edgeFlags) {
//                return false;
//            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                int childHeight = changedView.getHeight();
                float offset = (float) (childHeight + top) / childHeight;
                mTopMenuOnScreen = offset;
                changedView.setVisibility(offset == 0 ? View.INVISIBLE : View.VISIBLE);
                invalidate();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return mMenuView == child ? child.getHeight() : 0;
            }

        });

        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_TOP | ViewDragHelper.EDGE_BOTTOM);
        mDragHelper.setMinVelocity(minVel);
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);

        ViewGroup leftMenuView = mMenuView;
        MarginLayoutParams lp = (MarginLayoutParams)
                leftMenuView.getLayoutParams();

        final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                mMinDrawerMargin + lp.leftMargin + lp.rightMargin, lp.width);
        final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                lp.topMargin + lp.bottomMargin, lp.height);
        leftMenuView.measure(drawerWidthSpec, drawerHeightSpec);

        View contentView = mContentView;
        lp = (MarginLayoutParams) contentView.getLayoutParams();
        final int contentWidthSpec = MeasureSpec.makeMeasureSpec(
                widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
        final int contentHeightSpec = MeasureSpec.makeMeasureSpec(
                heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
        contentView.measure(contentWidthSpec, contentHeightSpec);

        mMenuView = leftMenuView;
        mContentView = contentView;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View menuView = mMenuView;
        View contentView = mContentView;

        MarginLayoutParams lp = (MarginLayoutParams) contentView.getLayoutParams();
        contentView.layout(lp.leftMargin, lp.topMargin,
                lp.leftMargin + contentView.getMeasuredWidth(),
                lp.topMargin + contentView.getMeasuredHeight());

        lp = (MarginLayoutParams) menuView.getLayoutParams();

        final int menuHeight = menuView.getMeasuredHeight();
        int childTop = -menuHeight + (int) (menuHeight * mTopMenuOnScreen);
//        menuView.layout(childLeft, lp.topMargin, childLeft + menuWidth,
//                lp.topMargin + menuView.getMeasuredHeight());
        menuView.layout(lp.leftMargin, childTop, lp.leftMargin + menuView.getMeasuredWidth(),
                childTop + menuHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean isViewUnder = mDragHelper.isViewUnder(mMenuView, x, y);
        return mDragHelper.shouldInterceptTouchEvent(ev) || (isViewUnder && mMenuView.onInterceptTouchEvent(ev));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        if (isBarOpen()) {//下拉了就让mMenuView处理事件
            boolean consume =  mMenuView.onTouchEvent(event);
        }
        return true;
    }

    /**
     * @return 菜单是否已下拉
     */
    public boolean isBarOpen() {
        return mTopMenuOnScreen > 0;//0是关闭 1是完全打开
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mMenuView = (ViewGroup) getChildAt(1);

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * @return 是否可以下拉总开关
     */
    public boolean isCanPull() {
        return true;
    }

    /**
     * @param canPull 设置当前页面是否是首页
     */
    public void setCanPull(boolean canPull) {
        this.canPull = canPull;
    }

    public void closeDrawer() {
        View menuView = mMenuView;
        mTopMenuOnScreen = 0.f;
//        mDragHelper.smoothSlideViewTo(menuView, menuView.getLeft(), -menuView.getHeight());
        requestLayout();//立即关闭
    }

    public void openDrawer() {
        View menuView = mMenuView;
        mTopMenuOnScreen = 1.0f;
        mDragHelper.smoothSlideViewTo(menuView, menuView.getLeft(), 0);
    }
}
