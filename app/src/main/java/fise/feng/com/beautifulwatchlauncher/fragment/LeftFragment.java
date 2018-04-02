package fise.feng.com.beautifulwatchlauncher.fragment;

import fise.feng.com.beautifulwatchlauncher.Prenster.Dao.NotificationEntityDaoUtil;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.MessageAdapter;
import fise.feng.com.beautifulwatchlauncher.entity.NotificationEntity;
import fise.feng.com.beautifulwatchlauncher.event.NotificationComingEvent;
import fise.feng.com.beautifulwatchlauncher.manager.PreferControler;
import fise.feng.com.beautifulwatchlauncher.service.BluetoothManagerService;
import fise.feng.com.beautifulwatchlauncher.thread.HeartBeatThread;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qingfeng on 2017/12/14.
 */

public class LeftFragment extends Fragment implements View.OnClickListener{
    MessageAdapter messageAdapter;
    RecyclerView mRecyclerView;
    List<NotificationEntity> list = new ArrayList<>();
    List<NotificationEntity>  mmsList = new ArrayList<>();
    List<NotificationEntity>  notificationEntityList = new ArrayList<>();

    Button notificationButton;
    Button mmsButton;
    boolean isShowMmsList = false;

    ImageView notiPoint;
    ImageView mmsPoint;

    MmsNotificationHandler mMmsNotificationHandler;
    public static LeftFragment newInstance() {
        Bundle args = new Bundle();
        LeftFragment fragment = new LeftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mNotificationList = NotificationEntityDaoUtil.instance().selectAll();
        EventBus.getDefault().register(this);
        mMmsNotificationHandler = new MmsNotificationHandler();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_left_page,container,false);
        initButtonView(view);
        initView(view);
        return view;
    }
    private void initButtonView(View view){
        mmsButton = view.findViewById(R.id.mms_button);
        mmsButton.setOnClickListener(this);
        notificationButton = view.findViewById(R.id.notification_button);
        notificationButton.setOnClickListener(this);
        mmsPoint = view.findViewById(R.id.mms_point);
        notiPoint = view.findViewById(R.id.noti_point);
    }
    private void initView(View view){
        mRecyclerView = view.findViewById(R.id.message_list);
        messageAdapter = new MessageAdapter(getActivity(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(messageAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMmsNotificationHandler.sendEmptyMessage(MmsNotificationHandler.MSG_QUERY_DATA_BASE);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d("onResume......");
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        if(!PreferControler.instance().getKeyConnectState().equals("CONNEDTIONED")){
//            HeartBeatThread.instance().onErrorConnected();
            LogUtils.d("state == DISCONNECTION service had been killed,restart service....");
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void notificationComing(NotificationComingEvent event) {
        NotificationEntityDaoUtil.instance().update(event.notificationEntity);
        LogUtils.d("notificationComing......");
        if(event.notificationEntity.getType().equals("N")){
            notificationEntityList.add(event.notificationEntity);
        }else if(event.notificationEntity.getType().equals("S")){
            mmsList.add(event.notificationEntity);
        }
        if(isShowMmsList){
            if(mmsList != null) {
                list.addAll(mmsList);
                LogUtils.d("list ...mmsList=" + mmsList.size());
            }
            messageAdapter.notifyDataSetChanged();
        }else {
            if(notificationEntityList != null) {
                list.addAll(notificationEntityList);
                LogUtils.d("list ...notificationEntityList=" + notificationEntityList.size());
            }
            messageAdapter.notifyDataSetChanged();
        }
    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            Collections.swap(list,viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            list.remove(viewHolder.getAdapterPosition());
            messageAdapter.notifyDataSetChanged();
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mms_button:
                 if(!isShowMmsList){
                     isShowMmsList = true;
                     mmsPoint.setVisibility(View.VISIBLE);
                     notiPoint.setVisibility(View.GONE);
                     mMmsNotificationHandler.sendEmptyMessage(MmsNotificationHandler.MSG_QUERY_DATA_BASE);
                     LogUtils.d("onClick..mms button");
                 }
                break;
            case R.id.notification_button:
                if(isShowMmsList) {
                    isShowMmsList =false;
                    mmsPoint.setVisibility(View.GONE);
                    notiPoint.setVisibility(View.VISIBLE);
                    mMmsNotificationHandler.sendEmptyMessage(MmsNotificationHandler.MSG_QUERY_DATA_BASE);
                    LogUtils.d("onClick..noti button");
                }
                break;
        }
    }
    class MmsNotificationHandler extends Handler{
        public static  final  int MSG_QUERY_DATA_BASE = 100;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_QUERY_DATA_BASE:
                    if(isShowMmsList){
                         list.clear();
                         mmsList =  NotificationEntityDaoUtil.instance().selectWhere("S");
                         if(mmsList != null && mmsList.size() >0)
                         list.addAll(mmsList);
                         messageAdapter.notifyDataSetChanged();
                    }else {
                        list.clear();
                        notificationEntityList =  NotificationEntityDaoUtil.instance().selectWhere("N");
                        if(notificationEntityList != null && notificationEntityList.size() >0)
                        list.addAll(notificationEntityList);
                        messageAdapter.notifyDataSetChanged();
                        LogUtils.d("list =" + list.size());
                    }
                    break;
            }
        }
    }
}
