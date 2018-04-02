package fise.feng.com.beautifulwatchlauncher.util;


import android.content.Context;

import org.greenrobot.greendao.database.Database;

import fise.feng.com.beautifulwatchlauncher.dao.DaoMaster;
import fise.feng.com.beautifulwatchlauncher.dao.DaoSession;
import fise.feng.com.beautifulwatchlauncher.dao.NotificationEntityDao;
import fise.feng.com.beautifulwatchlauncher.dao.SosNumberEntityDao;

/**
 * Created by zhangqie on 2016/3/26.
 */

public class DaoHelper extends DaoMaster.OpenHelper {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    private static String DBNAME = "";

    public DaoHelper(Context context, String name) {
        super(context, name, null);
        DaoHelper.DBNAME = name;
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
//        LogUtils.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion < newVersion) {
//            LogUtils.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
            MigrationHelper.migrate(db, NotificationEntityDao.class, SosNumberEntityDao.class);
            //更改过的实体类(新增的不用加)   更新UserDao文件 可以添加多个  XXDao.class 文件
//             MigrationHelper.getInstance().migrate(db, UserDao.class,XXDao.class);
        }
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    DBNAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
