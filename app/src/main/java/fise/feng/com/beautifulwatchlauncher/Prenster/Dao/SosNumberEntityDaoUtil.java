package fise.feng.com.beautifulwatchlauncher.Prenster.Dao;



import android.database.sqlite.SQLiteException;

import java.util.List;

import fise.feng.com.beautifulwatchlauncher.dao.NotificationEntityDao;
import fise.feng.com.beautifulwatchlauncher.dao.SosNumberEntityDao;
import fise.feng.com.beautifulwatchlauncher.db.DBManager;
import fise.feng.com.beautifulwatchlauncher.db.GreenDaoChargeRecordImpl;
import fise.feng.com.beautifulwatchlauncher.entity.NotificationEntity;
import fise.feng.com.beautifulwatchlauncher.entity.SosNumberEntity;

/**
 * Created by qingfeng on 2018/1/13.
 */

public class SosNumberEntityDaoUtil implements GreenDaoChargeRecordImpl<SosNumberEntity> {

    public SosNumberEntityDaoUtil() {
    }
    private static class SingletonHolder {
        private static final SosNumberEntityDaoUtil INSTANCE = new SosNumberEntityDaoUtil();

    }
    public static SosNumberEntityDaoUtil instance() {
        return SosNumberEntityDaoUtil.SingletonHolder.INSTANCE;
    }

    private SosNumberEntityDao sosNumberEntityDao;
    private SosNumberEntityDao getSosNumberEntityDao(){
        if(sosNumberEntityDao == null){
            sosNumberEntityDao = DBManager.instance().getDaoSession().getSosNumberEntityDao();
        }
        return sosNumberEntityDao;
    }

    @Override
    public long insert(SosNumberEntity data) {
        long add = getSosNumberEntityDao().insertOrReplace(data);
        return add;
    }

    @Override
    public boolean update(SosNumberEntity data) {
        if(data == null){
            return  false;
        }
        long insertUsersID = -1;
        SosNumberEntityDao dao = getSosNumberEntityDao();
        String number = data.getNumber();
        SosNumberEntity userQuery=null;
        try {
            userQuery = dao.queryBuilder().where(SosNumberEntityDao.Properties.Number.eq(number)).build().unique();
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
        getSosNumberEntityDao().deleteAll();
    }

    @Override
    public void deleteWhere(long id) {
        getSosNumberEntityDao().deleteByKey(id);
    }

    @Override
    public List<SosNumberEntity> selectAll() {
        List<SosNumberEntity> list = getSosNumberEntityDao().loadAll();
        return list != null && list.size() > 0 ? list:null;
    }


    @Override
    public List<SosNumberEntity> selectWhere(SosNumberEntity data) {
        String number = data.getNumber();
        List<SosNumberEntity> list =  getSosNumberEntityDao().queryBuilder().where(SosNumberEntityDao.Properties.Number.like(number)).build().list();
        return null != list && list.size() > 0 ? list : null;
    }

    @Override
    public List<SosNumberEntity> selectWhrer(long id) {
        return null;
    }

    @Override
    public List<SosNumberEntity> selectWhere(String type) {
        List<SosNumberEntity> list =getSosNumberEntityDao().queryBuilder().where(SosNumberEntityDao.Properties.Name.like(type)).build().list();
        return null != list && list.size() > 0 ? list : null;
    }
}
