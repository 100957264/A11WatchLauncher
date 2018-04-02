package fise.feng.com.beautifulwatchlauncher.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.aty.ClockSelectActivity;
import fise.feng.com.beautifulwatchlauncher.constant.ActivityConstant;
import fise.feng.com.beautifulwatchlauncher.holder.PageHolder;
import fise.feng.com.beautifulwatchlauncher.info.ImgPreviewInfo;
import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import fise.feng.com.beautifulwatchlauncher.view.ViewManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class ClockPageFragment extends Fragment implements View.OnClickListener{

    private static final String KEY_DATA = "page_holder";
    private PageHolder holder;
    ImageView iconClock;
    private int mLeft;
    private int mTop;
    private float mScaleX;
    private float mScaleY;
    private Drawable mBackground;
    public static ClockPageFragment newInstance(PageHolder holder) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATA, holder);
        ClockPageFragment fragment = new ClockPageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            holder = (PageHolder) bundle.getSerializable(KEY_DATA);
        }
        EventBus.getDefault().register(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_select_clock,container,false);
        iconClock = (ImageView)view.findViewById(R.id.icon_clock);
        iconClock.setOnClickListener(this);
        initData(getArguments());
        return view;
    }
    protected void initData(Bundle arguments) {
        PageHolder holder = (PageHolder) arguments.getSerializable(KEY_DATA);
        this.holder = holder;
        if (null != holder) {
          int iconId = holder.getIconId();
            iconClock.setBackgroundResource(ViewManager.clocks[iconId]);
        } else {
            LogUtils.e("fengqing ClockPageFragment arguments = null");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon_clock:
                Intent intent = new Intent();
                intent.putExtra(ActivityConstant.RESULT_STRING,this.holder.getIconId());
                getActivity().setResult(ActivityConstant.RESULT_CODE,intent);
                LogUtils.d("fengqing onClick id =" + this.holder.getIconId());
                ViewManager.currentClock = this.holder.getIconId();
                PreferControler.instance().setClockIndex(this.holder.getIconId());
                LogUtils.d("fengqing onClick currentClock =" + ViewManager.currentClock);
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                break;
        }
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onDataSynEvent(final ImgPreviewInfo fullImageInfo) {
        final int left = fullImageInfo.getLocationX();
        final int top = fullImageInfo.getLocationY();
        final int width = fullImageInfo.getWidth();
        final int height = fullImageInfo.getHeight();
        mBackground = fullImageInfo.getImageBg();
//        fullLay.setBackground(mBackground);
        iconClock.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                iconClock.getViewTreeObserver().removeOnPreDrawListener(this);
                int location[] = new int[2];
                iconClock.getLocationOnScreen(location);
                mLeft = left - location[0];
                mTop = top - location[1];
                mScaleX = width * 1.0f / iconClock.getWidth();
                mScaleY = height * 1.0f / iconClock.getHeight();
                activityEnterAnim();
                return true;
            }
        });
        Glide.with(this).load(fullImageInfo.getResourceId()).into(iconClock);
    }


    private void activityEnterAnim() {
        iconClock.setPivotX(0);
        iconClock.setPivotY(0);
        iconClock.setScaleX(mScaleX);
        iconClock.setScaleY(mScaleY);
        iconClock.setTranslationX(mLeft);
        iconClock.setTranslationY(mTop);
        iconClock.animate().scaleX(1).scaleY(1).translationX(0).translationY(0).
                setDuration(250).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackground, "alpha", 0, 255);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(250);
        objectAnimator.start();
    }
    private void activityExitAnim(Runnable runnable) {
        iconClock.setPivotX(0);
        iconClock.setPivotY(0);
        iconClock.animate().scaleX(mScaleX).scaleY(mScaleY).translationX(mLeft).translationY(mTop).
                withEndAction(runnable).
                setDuration(250).setInterpolator(new AccelerateDecelerateInterpolator()).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mBackground, "alpha", 255, 0);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.setDuration(250);
        objectAnimator.start();
    }

    public static void previewImage(Activity act, View view, int resourceId, Drawable bg) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        ImgPreviewInfo fullImageInfo = new ImgPreviewInfo();
        fullImageInfo.setLocationX(location[0]);
        fullImageInfo.setLocationY(location[1]);
        fullImageInfo.setWidth(view.getWidth());
        fullImageInfo.setHeight(view.getHeight());
        fullImageInfo.setResourceId(resourceId);
        fullImageInfo.setImageBg(bg);
        EventBus.getDefault().postSticky(fullImageInfo);
        act.startActivity(new Intent(act, ClockSelectActivity.class));
        act.overridePendingTransition(0, 0);
    }
}
