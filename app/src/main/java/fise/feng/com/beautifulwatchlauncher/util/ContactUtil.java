package fise.feng.com.beautifulwatchlauncher.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

import java.util.List;

import fise.feng.com.beautifulwatchlauncher.Prenster.Dao.SosNumberEntityDaoUtil;
import fise.feng.com.beautifulwatchlauncher.entity.SosNumberEntity;

/**
 * Created by qingfeng on 2018/1/11.
 */

public class ContactUtil {

    public static void addContact(String name, String number, Context context,boolean isNeedUpdateToContact){
        //insert contact name
        LogUtils.e("isNeedUpdateToContact =" + isNeedUpdateToContact);
        SosNumberEntity sosNumberEntity = new SosNumberEntity();
        sosNumberEntity.setName(name);
        sosNumberEntity.setNumber(number);
        SosNumberEntityDaoUtil.instance().insert(sosNumberEntity);
    }
    public static void deleteContact(String name,Context context){
       List<SosNumberEntity> list = SosNumberEntityDaoUtil.instance().selectWhere(name);
       if(list != null){
           for (SosNumberEntity sosNumberEntity : list){
               SosNumberEntityDaoUtil.instance().deleteWhere(sosNumberEntity.getId());
           }
       }
    }
}
