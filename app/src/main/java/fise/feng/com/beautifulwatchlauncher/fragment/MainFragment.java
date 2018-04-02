package fise.feng.com.beautifulwatchlauncher.fragment;

import android.content.Intent;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.aty.BeautifulWatchLauncherActivity;
import fise.feng.com.beautifulwatchlauncher.aty.ClockSkinChooseActivity;
import fise.feng.com.beautifulwatchlauncher.clock.model.ClockSkin;
import fise.feng.com.beautifulwatchlauncher.clock.model.ClockSkinParse;
import fise.feng.com.beautifulwatchlauncher.clock.view.ClockView;
import fise.feng.com.beautifulwatchlauncher.event.ClockSkinPositionEvent;
import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class MainFragment extends Fragment implements View.OnLongClickListener{
    View view;
    public static final int REQUEST_CODE_CHOOSE_CLOCK = 1;
    public static final String RESULT_EXTRA_CLOCK_INDEX = "clock_index";

    public static final String ARG_CLOCK_SCALE = "clock_scale";
    public static final String ARG_CLOCK_TYPE = "clock_type";
    public static final int CLOCK_SCALE_DEFAULT = 240;
    public static final int INVALUED_INDEX = -1;
    private ClockSkinLoader mClockSkinLoader;
    private ClockSkinParse mClockSkinParse;
    private ClockView mClockView;

    private int mClockIndex = INVALUED_INDEX;
    BeautifulWatchLauncherActivity beautifulWatchLauncherActivity;
    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClockSkinParse = new ClockSkinParse();
        mClockSkinLoader = new ClockSkinLoader();
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.clock_main:
               // ClockPageFragment.previewImage(getActivity(),v,ViewManager.clocks[ViewManager.currentClock],view.getBackground());
                gotoChangeClock();
                break;
        }
        return false;
    }
    private void gotoChangeClock() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getActivity(), ClockSkinChooseActivity.class);
        startActivity(intent);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_main_page_test,container,false);
        mClockView = (ClockView) view.findViewById(R.id.clock_main);
       LogUtils.d("onCreateView clockView =" + mClockView);
        mClockView.setOnLongClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            LogUtils.d("colck index =" + PreferControler.instance().getClockIndex());
            startGetClockSkin(PreferControler.instance().getClockIndex());

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("onResume...mClockView=" + mClockView);
        Log.d("fengqing","onResume...mClockView=" + mClockView);
        mClockView.startDraw();
//        ensureClock();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ClockSkinPositionEvent(ClockSkinPositionEvent event) {
        LogUtils.d("ClockSkinPositionEvent:  =" + event.position);
        startGetClockSkin(event.position);
    }

//    private void ensureClock(){
//        final int index = SharedPreferencesUtils.getIntParam(getActivity(),"BT_CLOCK",default_clock);
//        LogUtils.d("ensureClock" + "--index=" + index + "--mClockIndex=" + mClockIndex);
//        if(index != mClockIndex){
//            startGetClockSkin(index);
//            mClockIndex = index;
//        }
//    }



    private class ClockSkinLoader extends AsyncTask<Integer, Void, ClockSkin> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected ClockSkin doInBackground(Integer... params) {
            // TODO Auto-generated method stub
            ClockSkinParse parser = new ClockSkinParse(params[0], params[0]);
            ClockSkin clockSkin = null;
            try{
                clockSkin = parser.getChildSkinByPosition(getActivity(), params[1]);
                LogUtils.e("doInBackground clockSkin " + clockSkin);
                if(!isCancelled()){
                    mClockIndex = params[1];
                }
            }catch (Exception e){
                LogUtils.e("ClockSkinLoader--e=" + e.toString());
                if(!isCancelled()){
                    //mClockIndex = 0;
                    //clockSkin = parser.getChildSkinByPosition(getActivity(), 0);
                    return null;
                }
            }
            return clockSkin;
        }

        @Override
        protected void onPostExecute(ClockSkin result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            LogUtils.e("isCancelled " +isCancelled());
            LogUtils.e("ClockSkin result " +result);
            if(!isCancelled()){
                setClockSkin(result);
            }
        }
    }
    private void setClockSkin(ClockSkin clockSkin){
        if(mClockView == null){
            return;
        }

        if(clockSkin == null){
            LogUtils.d("clockSkin == null");
            startGetClockSkin(0);
        }else{
            LogUtils.d( "clockSkin =" + clockSkin.toString());
            mClockView.setClockSkin(clockSkin);
        }
    }
    private void startGetClockSkin(int position){
        LogUtils.i("startGetClockSkin--position=" + position);
        if(mClockSkinLoader != null){
            final ClockSkinLoader oldTask = mClockSkinLoader;
            oldTask.cancel(true);
            mClockSkinLoader = null;
        }
        mClockSkinLoader = new ClockSkinLoader();
        LogUtils.i("startGetClockSkin--position22=" + position);
        mClockSkinLoader.execute(CLOCK_SCALE_DEFAULT, position);

    }
    private void stopGetClockSkin(){

        if(mClockSkinLoader != null){
            final ClockSkinLoader oldTask = mClockSkinLoader;
            oldTask.cancel(true);
            mClockSkinLoader = null;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopGetClockSkin();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
