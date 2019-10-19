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
                do {
                    list.add(new CategoryDTO(cursor.getInt(0),
                            cursor.getString(1),
                            Boolean.parseBoolean(cursor.getString(2))));
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return list;
    }

    public void seed() {
        SQLiteDatabase db = dbManager.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBManager.CATEGORY_NAME, "Tong hop");
        values.put(DBManager.CATEGORY_VISIBLE, String.valueOf(true));
        db.insert(DBManager.CATEGORY_TABLE_NAME, null, values);

        for (int i = 0; i < 10; i++) {
            values = new ContentValues();
            values.put(DBManager.CATEGORY_NAME, "Thoi su");
            values.put(DBManager.CATEGORY_VISIBLE, String.valueOf(true));
            db.insert(DBManager.CATEGORY_TABLE_NAME, null, values);
        }

        db.close();
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
