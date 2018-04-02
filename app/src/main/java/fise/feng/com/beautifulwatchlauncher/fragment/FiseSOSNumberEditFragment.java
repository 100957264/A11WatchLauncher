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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
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

public class FiseSOSNumberEditFragment extends Fragment implements View.OnClickListener{
    String name;
    String number;
    EditText editName;
    EditText editNumber;
    Button commitButton;
    ContactOperationHandler mHandler;
    public static FiseSOSNumberEditFragment newInstance() {
        Bundle args = new Bundle();
        FiseSOSNumberEditFragment fragment = new FiseSOSNumberEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_contact, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        editName =(EditText) view.findViewById(R.id.edit_contact_name);
        editNumber =(EditText) view.findViewById(R.id.edit_contact_number);
        commitButton = view.findViewById(R.id.commit_contact);
        commitButton.setOnClickListener(this);
        mHandler = new ContactOperationHandler(getActivity());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit_contact:
                String name = editName.getText().toString().trim();
                String number = editNumber.getText().toString().trim();
                Log.d("EditContactActivity","name = " + name + ", number=" +number);
                if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(number)){
                    mHandler.sendMessage(getMassage(ContactConstant.CONTACT_INSERT,name,number,true));
                    editName.getText().clear();
                    editNumber.getText().clear();
                    Toast.makeText(getActivity(),"Add successfully..",Toast.LENGTH_LONG).show();
                    SosNumberEntity sosNumberEntity = new SosNumberEntity();
                    sosNumberEntity.setNumber(number);
                    sosNumberEntity.setName(name);
                    EventBus.getDefault().post(sosNumberEntity);
                }else {
                    Toast.makeText(getActivity(),"name or number is null",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private Message getMassage(int what ,String name,String number,boolean isNeedUpdateToContact){
        Bundle bundle = new Bundle();
        bundle.putString(ContactConstant.CONTACT_NAME,name);
        bundle.putString(ContactConstant.CONTACT_NUMBER,number);
        bundle.putBoolean(ContactConstant.IS_NEED_UPDATE,isNeedUpdateToContact);
        Message msg = new Message();
        msg.what = what;
        msg.setData(bundle);
        return msg;
    }
}
