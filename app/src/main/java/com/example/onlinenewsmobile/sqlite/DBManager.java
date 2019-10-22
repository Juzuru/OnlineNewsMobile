package com.example.onlinenewsmobile.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "OnlineNewsMobile";

    public static final String CATEGORY_TABLE_NAME = "Category";
    public static final String CATEGORY_ID = "id";
    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_VISIBLE = "visible";
    public static final String CATEGORY_NEWSPAPER_ID = "newspaperId";
    public static final String CATEGORY_RSS_LINK = "rssLink";

    public static final String NEWSPAPER_TABLE_NAME = "Newspaper";
    public static final String NEWSPAPER_ID = "id";
    public static final String NEWSPAPER_NAME = "name";
    public static final String NEWSPAPER_IMAGE_BASE64 = "imageBase64";

    public static final String NEWS_TABLE_NAME = "News";
    public static final String NEWS_ID = "id";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_LINK = "link";
    public static final String NEWS_IMAGE_LINK = "imageLink";
    public static final String NEWS_CATEGORY_ID = "categoryId";
    public static final String NEWS_IS_MARK = "isMark";
    public static final String NEWS_RECENT = "recent";

    public DBManager (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + CATEGORY_TABLE_NAME + " ("
                + CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORY_NAME + " TEXT, "
                + CATEGORY_VISIBLE + " TEXT, "
                + CATEGORY_NEWSPAPER_ID + " INTEGER, "
                + CATEGORY_RSS_LINK + " TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE " + NEWSPAPER_TABLE_NAME + " ("
                + NEWSPAPER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NEWSPAPER_NAME + " TEXT, "
                + NEWSPAPER_IMAGE_BASE64 + " TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE " + NEWS_TABLE_NAME + " ("
                + NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NEWS_TITLE + " TEXT, "
                + NEWS_LINK + " TEXT, "
                + NEWS_IMAGE_LINK + " TEXT, "
                + NEWS_CATEGORY_ID + " INTEGER, "
                + NEWS_IS_MARK + " TEXT, "
                + NEWS_RECENT + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NEWSPAPER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE_NAME);
        onCreate(db);
    }
}
