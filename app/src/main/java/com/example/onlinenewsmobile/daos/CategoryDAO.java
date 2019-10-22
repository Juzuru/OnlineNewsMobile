package com.example.onlinenewsmobile.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.onlinenewsmobile.models.CategoryDTO;
import com.example.onlinenewsmobile.sqlite.DBManager;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryDAO implements Serializable {
    private DBManager dbManager;

    public CategoryDAO(Context context) {
        dbManager = new DBManager(context);
    }

    public ArrayList<CategoryDTO> getAll() {
        ArrayList<CategoryDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        String sql = "SELECT * FROM " + DBManager.CATEGORY_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                CategoryDTO categoryDTO;
                do {
                    categoryDTO = new CategoryDTO();
                    categoryDTO.setId(cursor.getInt(0));
                    categoryDTO.setName(cursor.getString(1));
                    categoryDTO.setVisible(Boolean.parseBoolean(cursor.getString(2)));
                    categoryDTO.setNewspaperId(cursor.getInt(3));
                    categoryDTO.setRssLink(cursor.getString(4));

                    list.add(categoryDTO);
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return list;
    }

    public ArrayList<CategoryDTO> getByNewspaperIdForSetting(int newspaperId) {
        ArrayList<CategoryDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.CATEGORY_TABLE_NAME,
                new String[]{DBManager.CATEGORY_ID, DBManager.CATEGORY_NAME, DBManager.CATEGORY_NEWSPAPER_ID, DBManager.CATEGORY_RSS_LINK, DBManager.CATEGORY_VISIBLE},
                DBManager.CATEGORY_NEWSPAPER_ID + "=?",
                new String[]{String.valueOf(newspaperId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                CategoryDTO dto;
                do {
                    dto = new CategoryDTO();
                    dto.setId(cursor.getInt(0));
                    dto.setName(cursor.getString(1));
                    dto.setNewspaperId(cursor.getInt(2));
                    dto.setRssLink(cursor.getString(3));
                    dto.setVisible(Boolean.parseBoolean(cursor.getString(4)));

                    list.add(dto);
                } while (cursor.moveToNext());
            }
        }

        return list;
    }

    public ArrayList<CategoryDTO> getAllActive() {
        ArrayList<CategoryDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.CATEGORY_TABLE_NAME,
                new String[]{DBManager.CATEGORY_ID, DBManager.CATEGORY_NAME, DBManager.CATEGORY_VISIBLE},
                DBManager.CATEGORY_VISIBLE + "=?",
                new String[]{String.valueOf(true)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                CategoryDTO categoryDTO;
                do {
                    categoryDTO = new CategoryDTO();
                    categoryDTO.setId(cursor.getInt(0));
                    categoryDTO.setName(cursor.getString(1));
                    categoryDTO.setVisible(Boolean.parseBoolean(cursor.getString(2)));
                    categoryDTO.setNewspaperId(cursor.getInt(3));
                    categoryDTO.setRssLink(cursor.getString(4));

                    list.add(categoryDTO);
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return list;
    }

    public ArrayList<CategoryDTO> getByNewspaperId(int newspaperId) {
        ArrayList<CategoryDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.CATEGORY_TABLE_NAME,
                new String[]{DBManager.CATEGORY_ID, DBManager.CATEGORY_NAME, DBManager.CATEGORY_NEWSPAPER_ID, DBManager.CATEGORY_RSS_LINK},
                DBManager.CATEGORY_NEWSPAPER_ID + "=?",
                new String[]{String.valueOf(newspaperId)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                CategoryDTO dto;
                do {
                    dto = new CategoryDTO();
                    dto.setId(cursor.getInt(0));
                    dto.setName(cursor.getString(1));
                    dto.setNewspaperId(cursor.getInt(2));
                    dto.setRssLink(cursor.getString(3));
                    dto.setVisible(true);

                    list.add(dto);
                } while (cursor.moveToNext());
            }
        }

        return list;
    }

    public ArrayList<CategoryDTO> getActiveByNewspaperId(int newspaperId) {
        ArrayList<CategoryDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.CATEGORY_TABLE_NAME,
                new String[]{DBManager.CATEGORY_ID, DBManager.CATEGORY_NAME, DBManager.CATEGORY_NEWSPAPER_ID, DBManager.CATEGORY_RSS_LINK},
                DBManager.CATEGORY_NEWSPAPER_ID + "=? and " + DBManager.CATEGORY_VISIBLE + "=?",
                new String[]{String.valueOf(newspaperId), String.valueOf(true)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                CategoryDTO dto;
                do {
                    dto = new CategoryDTO();
                    dto.setId(cursor.getInt(0));
                    dto.setName(cursor.getString(1));
                    dto.setNewspaperId(cursor.getInt(2));
                    dto.setRssLink(cursor.getString(3));
                    dto.setVisible(true);

                    list.add(dto);
                } while (cursor.moveToNext());
            }
        }

        return list;
    }

    public int update(CategoryDTO categoryDTO) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.CATEGORY_VISIBLE, String.valueOf(categoryDTO.isVisible()));

        int raw = db.update(DBManager.CATEGORY_TABLE_NAME, values, DBManager.CATEGORY_ID + "=?", new String[]{String.valueOf(categoryDTO.getId())});
        db.close();
        return raw;
    }

    public void create(CategoryDTO categoryDTO) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBManager.CATEGORY_NAME, categoryDTO.getName());
        values.put(DBManager.CATEGORY_VISIBLE, String.valueOf(true));
        values.put(DBManager.CATEGORY_NEWSPAPER_ID, categoryDTO.getNewspaperId());
        values.put(DBManager.CATEGORY_RSS_LINK, categoryDTO.getRssLink());

        db.insert(DBManager.CATEGORY_TABLE_NAME, null, values);
        db.close();
    }
}
