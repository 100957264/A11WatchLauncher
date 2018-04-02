package fise.feng.com.beautifulwatchlauncher.db;

import java.util.List;

/**
 * Created by zhangqie on 2016/3/26.
 */
public interface GreenDaoChargeRecordImpl<T> {

    long insert(T data);

    boolean update(T data);

    void deleteAll();

    void deleteWhere(long id);

    List<T> selectAll();

    List<T> selectWhere(T data);

    List<T> selectWhrer(long id);

    List<T> selectWhere(String type);
}
