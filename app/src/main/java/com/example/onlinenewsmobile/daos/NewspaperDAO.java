package com.example.onlinenewsmobile.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.onlinenewsmobile.models.NewspaperDTO;
import com.example.onlinenewsmobile.sqlite.DBManager;

import java.io.Serializable;
import java.util.ArrayList;

public class NewspaperDAO implements Serializable {
    private DBManager dbManager;

    public NewspaperDAO(Context context) {
        dbManager = new DBManager(context);
    }

    public ArrayList<NewspaperDTO> getAll() {
        ArrayList<NewspaperDTO> list = new ArrayList<>();

        SQLiteDatabase db = dbManager.getReadableDatabase();
        String sql = "SELECT * FROM " + DBManager.CATEGORY_TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                NewspaperDTO newspaperDTO;
                do {
                    newspaperDTO = new NewspaperDTO();
                    newspaperDTO.setId(cursor.getInt(0));
                    newspaperDTO.setName(cursor.getString(1));
                    newspaperDTO.setImageBase64(cursor.getString(2));

                    list.add(newspaperDTO);
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return list;
    }

    public NewspaperDTO getById(int id) {
        SQLiteDatabase db = dbManager.getReadableDatabase();
        Cursor cursor = db.query(DBManager.NEWSPAPER_TABLE_NAME,
                new String[]{DBManager.NEWSPAPER_NAME, DBManager.NEWSPAPER_IMAGE_BASE64},
                DBManager.CATEGORY_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                NewspaperDTO dto = new NewspaperDTO();
                dto.setId(id);
                dto.setName(cursor.getString(0));
                dto.setImageBase64(cursor.getString(1));

                return dto;
            }
        }

        return null;
    }

    public void create(NewspaperDTO newspaperDTO) {
        SQLiteDatabase db = dbManager.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBManager.NEWSPAPER_NAME, newspaperDTO.getName());
        values.put(DBManager.NEWSPAPER_IMAGE_BASE64, newspaperDTO.getImageBase64());

        db.insert(DBManager.NEWSPAPER_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<NewspaperDTO> seed() {
        ArrayList<NewspaperDTO> newspapers = new ArrayList<>();

        NewspaperDTO newspaperDTO = new NewspaperDTO();
        newspaperDTO.setName("24h");
        newspaperDTO.setImageBase64("");
        newspapers.add(newspaperDTO);

        newspaperDTO = new NewspaperDTO();
        newspaperDTO.setName("Dan tri");
        newspaperDTO.setImageBase64("");
        newspapers.add(newspaperDTO);

        return newspapers;
    }
}
