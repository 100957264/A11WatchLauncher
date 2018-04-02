package fise.feng.com.beautifulwatchlauncher.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by qingfeng on 2017/12/29.
 */

public class BottomScrollView extends NestedScrollView{

    public BottomScrollView(Context context) {
        super(context);
    }

    public BottomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
