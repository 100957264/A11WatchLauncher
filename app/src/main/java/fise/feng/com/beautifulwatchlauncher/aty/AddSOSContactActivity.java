package fise.feng.com.beautifulwatchlauncher.aty;

import android.content.ContentResolver;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fise.feng.com.beautifulwatchlauncher.R;
import fise.feng.com.beautifulwatchlauncher.constant.ContactConstant;
import fise.feng.com.beautifulwatchlauncher.handler.ContactOperationHandler;


public class AddSOSContactActivity extends BaseActivity implements View.OnClickListener{
    String name;
    String number;
    EditText editName;
    EditText editNumber;
    Button commitButton;
    ContactOperationHandler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.edit_contact);

        initView();
    }
    private void initView() {
        editName =(EditText) findViewById(R.id.edit_contact_name);
        editNumber =(EditText) findViewById(R.id.edit_contact_number);
        commitButton = findViewById(R.id.commit_contact);
        commitButton.setOnClickListener(this);
        mHandler = new ContactOperationHandler(this);
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
                    Toast.makeText(this,"Add successfully..",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(this,"name or number is null",Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
        finish();
    }

}
