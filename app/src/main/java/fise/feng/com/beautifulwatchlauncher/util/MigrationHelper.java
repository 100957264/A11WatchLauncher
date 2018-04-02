package fise.feng.com.beautifulwatchlauncher.util;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * please call {@link #migrate(SQLiteDatabase, Class[])} or {@link #migrate(Database, Class[])}
 */
public final class MigrationHelper {

    public static boolean DEBUG = false;
    private static String TAG = "MigrationHelper";
    private static final String SQLITE_MASTER = "sqlite_master";
    private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

    public static void migrate(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        printLog("【The Old Database Version】" + db.getVersion());
        Database database = new StandardDatabase(db);
        migrate(database, daoClasses);
    }

    private static boolean checkDaoClassesNull(Class<? extends AbstractDao<?, ?>>... daoClasses) {
        return null == daoClasses || daoClasses.length <= 0;
    }

    public static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        printLog("【migrate database】start");
        boolean daoClassesNull = checkDaoClassesNull(daoClasses);
        printLog("【checkDaoClassesNull】" + daoClassesNull);
        if (daoClassesNull) {
            return;
        }
        DaoConfig daoConfig;
        String tableName;
        for (int i = 0; i < daoClasses.length; i++) {
            daoConfig = new DaoConfig(database, daoClasses[i]);
            tableName = daoConfig.tablename;
            printLog("【Check isTableExists】start");
            boolean isTableExists = isTableExists(database, false, tableName);
            printLog("【Check isTableExists】complete");
            if (!isTableExists) {
                printLog(tableName + "【 is New Table】");
                continue;
            }
            printLog("【Check isShouldNotUpdate】start");
            boolean isShouldNotUpdate = isShouldNotUpdate(database, daoConfig, tableName);
            printLog("【Check isShouldNotUpdate】complete");
            if (isShouldNotUpdate) {//表列一样就过滤不用建临时表
                continue;
            }
            printLog("【Generate temp table】start");
            generateSingleTempTables(database, daoConfig);
            printLog("【Generate temp table】complete");

            printLog("【drop Single Table】" + tableName + " start");
            dropSingleTable(database, true, daoClasses[i]);
            printLog("【drop Single Table】" + tableName + "complete");

            printLog("【create Single Table】" + tableName + " start");
            createSingleTable(database, true, daoClasses[i]);
            printLog("【create Single Table】" + tableName + "complete");

            printLog("【Restore data】" + tableName + " start");
            restoreSingleTable(database, daoConfig);
            printLog("【Restore data】" + tableName + " complete");
        }
        printLog("【migrate database】complete");

    }

    private static void generateSingleTempTables(Database db, DaoConfig daoConfig) {
        String tempTableName = null;
        String tableName = daoConfig.tablename;
        try {
            tempTableName = daoConfig.tablename.concat("_TEMP");
            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE IF EXISTS ").append(tempTableName).append(";");
            db.execSQL(dropTableStringBuilder.toString());

            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("CREATE TEMPORARY TABLE ").append(tempTableName);
            insertTableStringBuilder.append(" AS SELECT * FROM ").append(tableName).append(";");
            db.execSQL(insertTableStringBuilder.toString());
            printLog("【Table】" + tableName + "\n ---Columns-->" + getColumnsStr(daoConfig));
            printLog("【Generate temp table】" + tempTableName);
        } catch (SQLException e) {
            Log.e(TAG, "【Failed to generate temp table】" + tempTableName, e);
        }
    }

    private static boolean isTableExists(Database db, boolean isTemp, String tableName) {
        if (db == null || TextUtils.isEmpty(tableName)) {
            return false;
        }
        String dbName = isTemp ? SQLITE_TEMP_MASTER : SQLITE_MASTER;
        String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
        Cursor cursor = null;
        int count = 0;
        try {
            cursor = db.rawQuery(sql, new String[]{"table", tableName});
            if (cursor == null || !cursor.moveToFirst()) {
                return false;
            }
            count = cursor.getInt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return count > 0;
    }


    private static String getColumnsStr(DaoConfig daoConfig) {
        if (daoConfig == null) {
            return "no columns";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < daoConfig.allColumns.length; i++) {
            builder.append(daoConfig.allColumns[i]);
            builder.append(",");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    private static void dropSingleTable(Database db, boolean ifExists,
                                        @NonNull Class<? extends AbstractDao<?, ?>> daoClass) {
        reflectMethod(db, "dropTable", ifExists, daoClass);
        printLog("【Drop all table by reflect】");
    }

    private static void createSingleTable(Database db, boolean ifNotExists,
                                          @NonNull Class<? extends AbstractDao<?, ?>> daoClass) {
        reflectMethod(db, "createTable", ifNotExists, daoClass);
        printLog("【Create all table by reflect】");
    }

    /**
     * dao class already define the sql exec method, so just invoke it
     */
    private static void reflectMethod(Database db, String methodName, boolean isExists,
                                      @NonNull Class<? extends AbstractDao<?, ?>> daoClass) {
        try {
            Method method = daoClass.getDeclaredMethod(methodName, Database.class, boolean.class);
            method.invoke(null, db, isExists);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void restoreSingleTable(Database db,
                                           DaoConfig daoConfig) {
        String tableName = daoConfig.tablename;
        String tempTableName = daoConfig.tablename.concat("_TEMP");

        if (!isTableExists(db, true, tempTableName)) {//临时表不存在
            return;
        }

        try {
            // get all columns from tempTable, take careful to use the columns list
            List<String> columns = getColumns(db, tempTableName);
            ArrayList<String> properties = new ArrayList<>(columns.size());
            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;
                if (columns.contains(columnName)) {
                    properties.add("`" + columnName + "`");
                }
            }
            if (properties.size() > 0) {
                final String columnSQL = TextUtils.join(",", properties);

                StringBuilder insertTableStringBuilder = new StringBuilder();
                insertTableStringBuilder.append("REPLACE INTO ").append(tableName).append(" (");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(") SELECT ");
                insertTableStringBuilder.append(columnSQL);
                insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
                db.execSQL(insertTableStringBuilder.toString());
                printLog("【Restore data】 to " + tableName);
            }
            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
            db.execSQL(dropTableStringBuilder.toString());
            printLog("【Drop temp table】" + tempTableName);
        } catch (SQLException e) {
            Log.e(TAG, "【Failed to restore data from temp table 】" + tempTableName, e);
        }
    }

    private static List<String> getColumns(Database db, String tableName) {
        List<String> columns = null;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
            if (null != cursor && cursor.getColumnCount() > 0) {
                columns = Arrays.asList(cursor.getColumnNames());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (null == columns)
                columns = new ArrayList<>();
        }
        return columns;
    }

    private static void printLog(String info) {
        if (DEBUG) {
            Log.d(TAG, info);
        }
    }

    private static boolean isShouldNotUpdate(Database db, DaoConfig daoConfig, String dbTableName) {
        String[] tarAllColumns = daoConfig.allColumns;
        List<String> tarColumnsList = Arrays.asList(tarAllColumns);
        List<String> srcColumns = getColumns(db, dbTableName);
        return StringUtils.isListEquals(srcColumns, tarColumnsList);
    }
}