package fise.feng.com.beautifulwatchlauncher.Prenster.Dao;


import android.database.sqlite.SQLiteException;

import fise.feng.com.beautifulwatchlauncher.dao.NotificationEntityDao;
import fise.feng.com.beautifulwatchlauncher.db.DBManager;
import fise.feng.com.beautifulwatchlauncher.db.GreenDaoChargeRecordImpl;
import fise.feng.com.beautifulwatchlauncher.entity.NotificationEntity;

import java.util.List;

/**
 * Created by qingfeng on 2018/1/13.
 */

public class NotificationEntityDaoUtil implements GreenDaoChargeRecordImpl<NotificationEntity> {
    public NotificationEntityDaoUtil() {
    }
    private static class SingletonHolder {
        private static final NotificationEntityDaoUtil INSTANCE = new NotificationEntityDaoUtil();

    }
    public static NotificationEntityDaoUtil instance() {
        return SingletonHolder.INSTANCE;
    }

    private NotificationEntityDao mNotificationEntityDao;
    private NotificationEntityDao getNotificationEntityDao(){
        if(mNotificationEntityDao == null){
            mNotificationEntityDao = DBManager.instance().getDaoSession().getNotificationEntityDao();
        }
        return mNotificationEntityDao;
    }

    @Override
    public long insert(NotificationEntity data) {
        long add = getNotificationEntityDao().insertOrReplace(data);
        return add;
    }

    @Override
    public boolean update(NotificationEntity data) {
        if(data == null){
            return  false;
        }
        long insertUsersID = -1;
        NotificationEntityDao dao =    getNotificationEntityDao();
        long time = data.getTime();
        NotificationEntity userQuery=null;
        try {
            userQuery = dao.queryBuilder().where(NotificationEntityDao.Properties.Time.eq(time)).build().unique();
        } catch (SQLiteException e) {
        }
        if (null != userQuery) {//覆盖更新
            data.setId(userQuery.getId());
        } else {
            data.setId(System.currentTimeMillis());
        }
        insertUsersID = dao.insertOrReplace(data);
        boolean isSuccess = insertUsersID >= 0;
        return isSuccess;
    }

    @Override
    public void deleteAll() {
       getNotificationEntityDao().deleteAll();
    }

    @Override
    public void deleteWhere(long id) {
       getNotificationEntityDao().deleteByKey(id);
    }

    @Override
    public List<NotificationEntity> selectAll() {
        List<NotificationEntity> list = getNotificationEntityDao().loadAll();
        return list != null && list.size() > 0 ? list:null;
    }


    @Override
    public List<NotificationEntity> selectWhere(NotificationEntity data) {
        String type = data.getType();
        List<NotificationEntity> list =  getNotificationEntityDao().queryBuilder().where(NotificationEntityDao.Properties.Type.like(type)).build().list();
        return null != list && list.size() > 0 ? list : null;
    }

    @Override
    public List<NotificationEntity> selectWhrer(long id) {
        return null;
    }

    @Override
    public List<NotificationEntity> selectWhere(String type) {
        List<NotificationEntity> list =  getNotificationEntityDao().queryBuilder().where(NotificationEntityDao.Properties.Type.like(type)).build().list();
        return null != list && list.size() > 0 ? list : null;
    }
}
