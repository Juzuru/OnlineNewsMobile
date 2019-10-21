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

    public CategoryDTO getById(int id) {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.CATEGORY_TABLE_NAME,
                new String[]{DBManager.CATEGORY_NAME, DBManager.CATEGORY_NEWSPAPER_ID, DBManager.CATEGORY_RSS_LINK},
                DBManager.CATEGORY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                CategoryDTO dto = new CategoryDTO();
                dto.setId(id);
                dto.setName(cursor.getString(0));
                dto.setNewspaperId(cursor.getInt(1));
                dto.setRssLink(cursor.getString(2));

                return dto;
            }
        }

        return null;
    }
    
    public ArrayList<CategoryDTO> seed(int newspaperId) {
        ArrayList<CategoryDTO> categories = new ArrayList<>();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Trang chủ");
        categoryDTO.setVisible(true);
        categoryDTO.setNewspaperId(newspaperId);
        categoryDTO.setRssLink("24h.com.vn/upload/rss/trangchu24h.rss");
        categories.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setName("Bóng đá");
        categoryDTO.setVisible(true);
        categoryDTO.setNewspaperId(newspaperId);
        categoryDTO.setRssLink("24h.com.vn/upload/rss/bongda.rss");
        categories.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setName("An ninh - Hình sự");
        categoryDTO.setVisible(true);
        categoryDTO.setNewspaperId(newspaperId);
        categoryDTO.setRssLink("24h.com.vn/upload/rss/anninhhinhsu.rss");
        categories.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setName("Thời trang");
        categoryDTO.setVisible(true);
        categoryDTO.setNewspaperId(newspaperId);
        categoryDTO.setRssLink("24h.com.vn/upload/rss/thoitrang.rss");
        categories.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setName("Ẩm thực");
        categoryDTO.setVisible(true);
        categoryDTO.setNewspaperId(newspaperId);
        categoryDTO.setRssLink("\t24h.com.vn/upload/rss/amthuc.rss");
        categories.add(categoryDTO);

        categoryDTO = new CategoryDTO();
        categoryDTO.setName("Giáo dục - du học");
        categoryDTO.setVisible(true);
        categoryDTO.setNewspaperId(newspaperId);
        categoryDTO.setRssLink("24h.com.vn/upload/rss/giaoducduhoc.rss");
        categories.add(categoryDTO);

        return categories;

//        SQLiteDatabase db = dbManager.getWritableDatabase();
//
//        ContentValues values;
//        CategoryDTO dto;
//        for (int i = 0; i < categories.size(); i++) {
//            dto = categories.get(i);
//            values = new ContentValues();
//            values.put(DBManager.CATEGORY_NAME, dto.getName());
//            values.put(DBManager.CATEGORY_VISIBLE, String.valueOf(true));
//            values.put(DBManager.CATEGORY_NEWSPAPER_ID, dto.getNewspaperId());
//            values.put(DBManager.CATEGORY_RSS_LINK, dto.getRssLink());
//            db.insert(DBManager.CATEGORY_TABLE_NAME, null, values);
//        }
//
//        db.close();
    }

    public int update(CategoryDTO categoryDTO) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.CATEGORY_VISIBLE, String.valueOf(categoryDTO.isVisible()));

        int raw = db.update(DBManager.CATEGORY_TABLE_NAME, values, DBManager.CATEGORY_ID + "=?", new String[]{String.valueOf(categoryDTO.getId())});
        db.close();
        return raw;
    }
}
