package fise.feng.com.beautifulwatchlauncher.handler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import fise.feng.com.beautifulwatchlauncher.constant.ContactConstant;
import fise.feng.com.beautifulwatchlauncher.event.SOSListUpdateEvent;
import fise.feng.com.beautifulwatchlauncher.util.ContactUtil;

/**
 * Created by qingfeng on 2018/1/11.
 */

public class ContactOperationHandler extends Handler{

    Context mContext;
    public ContactOperationHandler(Context context){
        mContext = context;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case ContactConstant.CONTACT_INSERT:
                Bundle bundle = msg.getData();
                String name = bundle.getString(ContactConstant.CONTACT_NAME);
                String number = bundle.getString(ContactConstant.CONTACT_NUMBER);
                boolean isNeedUpdateToContact = bundle.getBoolean(ContactConstant.IS_NEED_UPDATE);
                ContactUtil.addContact(name,number,mContext,isNeedUpdateToContact);
                SOSListUpdateEvent sosListUpdateEvent = new SOSListUpdateEvent(number,name);
                EventBus.getDefault().post(sosListUpdateEvent);
                break;
            case ContactConstant.CONTACT_UPDATE:
                break;
            case ContactConstant.CONTACT_DELETE:
                Bundle deletebundle = msg.getData();
                String deletename = deletebundle.getString(ContactConstant.CONTACT_NAME);
                Log.d("ContactOperationHandler","start delete deletename=" + deletename);
                ContactUtil.deleteContact(deletename,mContext);
                break;
        }
    }
}
