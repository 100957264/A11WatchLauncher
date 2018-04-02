package fise.feng.com.beautifulwatchlauncher.aty;


import android.app.Activity;
import android.content.Context;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.ClockSkinChooseAdapter;
import fise.feng.com.beautifulwatchlauncher.clock.util.ClockSkinUtil;
import fise.feng.com.beautifulwatchlauncher.clock.view.PagerRecylerView;
import fise.feng.com.beautifulwatchlauncher.event.ClockSkinPositionEvent;
import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

public class ClockSkinChooseActivity extends Activity implements ClockSkinChooseAdapter.IClockSkinChooseClick {
	private static final String TAG = ClockSkinChooseActivity.class.getSimpleName();
	private PagerRecylerView clockChooseRecyclerView;
	private ClockSkinChooseAdapter clockSkinChooseAdapter;
	private TextView mEmptyText;
	private ClockSkinTask mClockSkinTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		clockSkinChooseAdapter = new ClockSkinChooseAdapter(this);
		setContentView(R.layout.activity_clock_skin_choose);
		initView();
		initData();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(mClockSkinTask != null){
			mClockSkinTask.cancel(true);
		}
		super.onDestroy();
	}

	private void initView() {
		// TODO Auto-generated method stub
		clockChooseRecyclerView = (PagerRecylerView) findViewById(R.id.clock_skin_choose_recyleview);
		clockChooseRecyclerView.initLayoutManager(LinearLayoutManager.HORIZONTAL, false);
		clockChooseRecyclerView.setFlingVelocity(2);
		mEmptyText = (TextView) findViewById(R.id.ckock_skin_choose_empty);
	}

	private void initData() {
		// TODO Auto-generated method stub
		clockSkinChooseAdapter.setOnItemClickListen(this);
		clockChooseRecyclerView.setAdapter(clockSkinChooseAdapter);
		mClockSkinTask = new ClockSkinTask();
		mClockSkinTask.execute(this);
	}
	
	private void setClockSkinFiles(String[] files){
		if(files == null || files.length == 0){
			if(mEmptyText != null){
				mEmptyText.setText(getString(R.string.clcock_skin_loader_error));
			}
			return;
		}
		if(clockSkinChooseAdapter != null){
			clockSkinChooseAdapter.setClockSkinFiles(files);
		}
		if(clockChooseRecyclerView != null){
			clockChooseRecyclerView.setInitPosition(PreferControler.instance().getClockIndex());
		}
	}
	
	private class ClockSkinTask extends AsyncTask<Context, Void, String[]>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String[] doInBackground(Context... params) {
			// TODO Auto-generated method stub
			return ClockSkinUtil.getAllClockSkins(params[0]);
		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(!isCancelled()){
				setClockSkinFiles(result);
			}
		}

	}

	@Override
	public void onItemClick(View view, int position) {
		// TODO Auto-generated method stub
		ClockSkinPositionEvent clockSkinPositionEvent = new ClockSkinPositionEvent(position);
		LogUtils.d("onItemClick position =" + position);
		PreferControler.instance().setClockIndex(position);
		EventBus.getDefault().postSticky(clockSkinPositionEvent);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
			}
		}, 700);
	}
}
