package fise.feng.com.beautifulwatchlauncher.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fise.feng.com.beautifulwatchlauncher.Prenster.Dao.SosNumberEntityDaoUtil;
import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.adapter.RecyclerViewContactAdapter;
import fise.feng.com.beautifulwatchlauncher.constant.ContactConstant;
import fise.feng.com.beautifulwatchlauncher.entity.SosNumberEntity;
import fise.feng.com.beautifulwatchlauncher.event.SOSListUpdateEvent;
import fise.feng.com.beautifulwatchlauncher.handler.ContactOperationHandler;

/**
 * Created by qingfeng on 2018/1/3.
 */

public class FiseSOSFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerViewContactAdapter recyclerViewContactAdapter;
    List<SosNumberEntity> mContactInfoList = new ArrayList<>();
    ContactOperationHandler mHandler;
    public static FiseSOSFragment newInstance() {
        Bundle args = new Bundle();
        FiseSOSFragment fragment = new FiseSOSFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new ContactOperationHandler(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sos, container, false);
        mContactInfoList = SosNumberEntityDaoUtil.instance().selectAll();
        mRecyclerView = view.findViewById(R.id.sos_recyclerview);
        recyclerViewContactAdapter = new RecyclerViewContactAdapter(getActivity(), mContactInfoList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(recyclerViewContactAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            Collections.swap(mContactInfoList,viewHolder.getAdapterPosition(),target.getAdapterPosition());
            Log.d("FiseContactFragment","onMove : name =" + mContactInfoList.get(viewHolder.getAdapterPosition()).name);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            String  name = mContactInfoList.get(viewHolder.getAdapterPosition()).name;
            Log.d("FiseContactFragment","name =" + name);
            if(!TextUtils.isEmpty(name)){
                mHandler.sendMessage(getDeleteMessage(name, ContactConstant.CONTACT_DELETE));
            }
            mContactInfoList.remove(viewHolder.getAdapterPosition());
            recyclerViewContactAdapter.notifyDataSetChanged();
        }
    });
    private Message getDeleteMessage(String name, int what){
        Bundle bundle = new Bundle();
        bundle.putString(ContactConstant.CONTACT_NAME,name);
        Message msg = new Message();
        msg.what = what;
        msg.setData(bundle);
        return msg;
    }

}
