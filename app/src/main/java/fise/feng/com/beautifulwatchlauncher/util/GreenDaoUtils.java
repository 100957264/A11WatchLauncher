package fise.feng.com.beautifulwatchlauncher.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import fise.feng.com.beautifulwatchlauncher.constant.DBConstant;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangqie on 2016/3/26.
 */

//创建数据库生成路径
public class GreenDaoUtils extends ContextWrapper {


    private Context mContext;

    public GreenDaoUtils(Context base) {
        super(base);
        this.mContext = base;
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param dbName
     */
    @Override
    public File getDatabasePath(String dbName) {
        File dbDir = new File(DBConstant.DBPATH);
        if(!dbDir.exists()){
            dbDir.mkdirs();
        }
        File dbFile = new File(DBConstant.DBPATH + DBConstant.DBNAME);
        LogUtils.e("dbFile =" + dbFile.toString());
        boolean isSuccessful = false;
        if(!dbFile.exists()){
            try {
                isSuccessful = dbFile.createNewFile();
                LogUtils.d("isSuccessful =" + isSuccessful);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dbFile;
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see ContextWrapper#openOrCreateDatabase(String, int,
     * SQLiteDatabase.CursorFactory,
     * DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }


}
