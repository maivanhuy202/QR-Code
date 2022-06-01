package com.example.bottom_menu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String COLUMN_QR_CONTENT = "QR_CONTENT";
    public static final String QR_TABLE = "QR_TABLE";
    public static final String COLUMN_QR_IS_FAVORITE = "QR_isFAVORITE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_QR_TYPE = "QR_TYPE";
    private static final String COLUMN_DATE_TIME = "QR_DATE_TIME";
    private static final String COLUMN_TITLE = "QR_TITLE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "QRCODE.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + QR_TABLE + " (" +
                COLUMN_ID               + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_QR_TYPE          + " INTEGER , " +
                COLUMN_DATE_TIME        + " TEXT, " +
                COLUMN_TITLE            + " TEXT, " +
                COLUMN_QR_CONTENT       + " TEXT, " +
                COLUMN_QR_IS_FAVORITE   + " BOOL)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean deleteOne(QrModel qrModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + QR_TABLE + " WHERE " + COLUMN_DATE_TIME + " = " + qrModel.getDateTime();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public boolean add(QrModel qrModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QR_TYPE, qrModel.getType());
        cv.put(COLUMN_DATE_TIME, qrModel.getDateTime());
        cv.put(COLUMN_TITLE, qrModel.getTitle());
        cv.put(COLUMN_QR_CONTENT, qrModel.getContent());
        cv.put(COLUMN_QR_IS_FAVORITE, qrModel.isFavorite());

        long insert = db.insert(QR_TABLE, null, cv);
        return insert != -1;
    }

    public List<QrModel> getAll() {
        List<QrModel> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + QR_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int type = cursor.getInt(1);
                String dateTime = cursor.getString(2);
                String title = cursor.getString(3);
                String content = cursor.getString(4);
                boolean isFavorite = cursor.getInt(5) == 1;
                QrModel newQR = new QrModel(type, dateTime, title, isFavorite, content);
                returnList.add(newQR);
            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
