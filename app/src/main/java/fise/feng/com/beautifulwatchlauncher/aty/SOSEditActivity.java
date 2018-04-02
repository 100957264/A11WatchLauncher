package fise.feng.com.beautifulwatchlauncher.aty;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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
import fise.feng.com.beautifulwatchlauncher.event.BTStateChangeEvent;
import fise.feng.com.beautifulwatchlauncher.event.SOSListUpdateEvent;
import fise.feng.com.beautifulwatchlauncher.fragment.FiseSOSFragment;
import fise.feng.com.beautifulwatchlauncher.fragment.FiseSOSNumberEditFragment;
import fise.feng.com.beautifulwatchlauncher.handler.ContactOperationHandler;
import fise.feng.com.beautifulwatchlauncher.manager.StaticManager;
import fise.feng.com.beautifulwatchlauncher.socket.BluetoothSppHelper;
import fise.feng.com.beautifulwatchlauncher.util.ContactUtil;
import fise.feng.com.beautifulwatchlauncher.util.LogUtils;

public class SOSEditActivity extends BaseActivity implements View.OnClickListener{

    Button addButtonEdit;
    Button addButtonFromContact;
    ContactOperationHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.sos_edit_activity);
        initView();
        EventBus.getDefault().register(this);
    }
    private void initView(){
        mHandler = new ContactOperationHandler(this);
        addButtonEdit = findViewById(R.id.add_sos_number);
        addButtonEdit.setOnClickListener(this);
        addButtonFromContact = findViewById(R.id.add_sos_from_contact);
        addButtonFromContact.setOnClickListener(this);
        startFragment(FiseSOSFragment.newInstance());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SOSListUpdate(SOSListUpdateEvent event) {
         startFragment(FiseSOSFragment.newInstance());//restart FiseSOSFragment
        addButtonEdit.setVisibility(View.VISIBLE);
        addButtonFromContact.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_sos_number:
//                Intent intent =new Intent(SOSEditActivity.this,AddSOSContactActivity.class);
//                startActivity(intent);
                  startFragment(FiseSOSNumberEditFragment.newInstance());
                  addButtonEdit.setVisibility(View.GONE);
                  addButtonFromContact.setVisibility(View.GONE);
                break;
            case R.id.add_sos_from_contact:
                Intent contactIntent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
//                Intent contactIntent = new Intent(Intent.ACTION_PICK);
//                contactIntent.setType("vnd.android.cursor.dir/phone");
                startActivityForResult(contactIntent,0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("usernumber =" + requestCode + ",username=" + resultCode + "data =" + data);
        if (resultCode == Activity.RESULT_OK) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            Cursor cursor = getContentResolver().query(contactData,null,null,null,null);
            cursor.moveToFirst();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                String usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String username = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                LogUtils.e("usernumber =" + usernumber + ",username=" + username );
                if(usernumber.equals(StaticManager.instance().previousNumber)){
                    return;
                }
                mHandler.handleMessage(getMassage(ContactConstant.CONTACT_INSERT,username,usernumber,false));
                StaticManager.instance().previousNumber = usernumber;
                startFragment(FiseSOSFragment.newInstance());//resart fragment
                addButtonEdit.setVisibility(View.VISIBLE);
                addButtonFromContact.setVisibility(View.VISIBLE);
            }

        }
    }
    public void startFragment(Fragment fragment){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.add_sos_fragment,fragment);
        fragmentTransaction.commit();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
