package com.neverdaless.wemeet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AppHelper {
    private static final String TAG = "AppHelper";

    public static SQLiteDatabase database;

    private static String createTableUserSql = "create table if not exists user " +
            "(  _id integer PRIMARY KEY autoincrement, " +
            "   user_id text, " +
            "   user_password text, " +
            "   nickname text, " +
            "   introduce text, " +
            "   terms_use integer, " +
            "   terms_notification integer, " +
            "   country text, " +
            "   phone text, " +
            "   tall integer, " +
            "   body_shape text, " +
            "   education text, " +
            "   age integer, " +
            "   job text, " +
            "   drink text, " +
            "   smoke text, " +
            "   user_image1 text, " +
            "   user_image2 text, " +
            "   user_image3 text, " +
            "   user_image4 text" +
            ")";

    public static void openDatabase(Context context,String databaseName) {
        println("openDatabase()");

        try {
            database = context.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
            if (database != null) {
                println(databaseName + " database 오픈됨.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTableUser() {
        println("createTableUser()");

        try {
            if (database != null) {
                database.execSQL(createTableUserSql);
                println("user 테이블 생성 요청됨.");
            } else {
                println("데이터베이스를 먼저 오픈하세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Cursor selectTable(String tableName) {
        println("selectTable() " + tableName);
        String sql = "select * from " + tableName;

        try {
            if (database != null) {
                return database.rawQuery(sql, null);
            } else {
                println("데이터베이스를 먼저 오픈하세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void dropTable(String tableName) {
        println("dropTable() " + tableName);

        try {
            if (database != null) {
                database.execSQL("drop table " + tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void println(String data) {
        Log.d(TAG, data);
    }
}
