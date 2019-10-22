package com.example.onlinenewsmobile.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.onlinenewsmobile.models.NewsDTO;
import com.example.onlinenewsmobile.models.NewspaperDTO;
import com.example.onlinenewsmobile.sqlite.DBManager;

import java.io.Serializable;
import java.util.ArrayList;

public class NewsDAO implements Serializable {
    private DBManager dbManager;

    public NewsDAO(Context context) {
        dbManager = new DBManager(context);
    }

    public void setMark(NewsDTO newsDTO) {
        int newsId = checkData(newsDTO.getLink());
        SQLiteDatabase db = dbManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBManager.NEWS_IS_MARK, String.valueOf(true));
        if (newsId < 0) {
            values.put(DBManager.NEWS_TITLE, newsDTO.getTitle());
            values.put(DBManager.NEWS_LINK, newsDTO.getLink());
            values.put(DBManager.NEWS_IMAGE_LINK, newsDTO.getImageLink());
            values.put(DBManager.NEWS_CATEGORY_ID, newsDTO.getCategoryId());
            values.put(DBManager.NEWS_RECENT, String.valueOf(false));

            db.insert(DBManager.NEWS_TABLE_NAME, null, values);
        } else {
            db.update(DBManager.NEWS_TABLE_NAME, values, DBManager.NEWS_ID + "=?", new String[]{String.valueOf(newsId)});
        }
        db.close();
    }

    public void setRecent(NewsDTO newsDTO) {
        int newsId = checkData(newsDTO.getLink());
        SQLiteDatabase db = dbManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBManager.NEWS_RECENT, String.valueOf(true));
        if (newsId < 0) {
            values.put(DBManager.NEWS_TITLE, newsDTO.getTitle());
            values.put(DBManager.NEWS_LINK, newsDTO.getLink());
            values.put(DBManager.NEWS_IMAGE_LINK, newsDTO.getImageLink());
            values.put(DBManager.NEWS_CATEGORY_ID, newsDTO.getCategoryId());
            values.put(DBManager.NEWS_IS_MARK, String.valueOf(false));

            db.insert(DBManager.NEWS_TABLE_NAME, null, values);
        } else {
            db.update(DBManager.NEWS_TABLE_NAME, values, DBManager.NEWS_ID + "=?", new String[]{String.valueOf(newsId)});
        }
        db.close();
    }

    private int checkData(String link) {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.NEWS_TABLE_NAME,
                new String[]{DBManager.NEWS_ID}, DBManager.NEWS_LINK + "=?",
                new String[]{link}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        }

        return -1;
    }

    public ArrayList<NewsDTO> getMarks() {
        ArrayList<NewsDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.NEWS_TABLE_NAME,
                new String[]{DBManager.NEWS_TITLE, DBManager.NEWS_LINK, DBManager.NEWS_IMAGE_LINK, DBManager.NEWS_CATEGORY_ID},
                DBManager.NEWS_IS_MARK + "=?", new String[]{String.valueOf(true)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                NewsDTO newsDTO;
                do {
                    newsDTO = new NewsDTO();
                    newsDTO.setTitle(cursor.getString(0));
                    newsDTO.setLink(cursor.getString(1));
                    newsDTO.setImageLink(cursor.getString(2));
                    newsDTO.setCategoryId(cursor.getInt(3));

                    list.add(newsDTO);
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return list;
    }

    public ArrayList<NewsDTO> getRencent() {
        ArrayList<NewsDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.NEWS_TABLE_NAME,
                new String[]{DBManager.NEWS_TITLE, DBManager.NEWS_LINK, DBManager.NEWS_IMAGE_LINK, DBManager.NEWS_CATEGORY_ID},
                DBManager.NEWS_RECENT + "=?", new String[]{String.valueOf(true)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                NewsDTO newsDTO;
                do {
                    newsDTO = new NewsDTO();
                    newsDTO.setTitle(cursor.getString(0));
                    newsDTO.setLink(cursor.getString(1));
                    newsDTO.setImageLink(cursor.getString(2));
                    newsDTO.setCategoryId(cursor.getInt(3));

                    list.add(newsDTO);
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return list;
    }
}
