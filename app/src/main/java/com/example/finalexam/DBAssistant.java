package com.example.finalexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DBAssistant extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myDB";
    private static final String TABLE_NAME = "TODOList";

    private SQLiteDatabase db;

    public DBAssistant(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "creationDate LONG," +
                "completionDate LONG," +
                "completed INTEGER" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void close() {
        db.close();
        super.close();
    }

    public Cursor getEntries() {
        Cursor cur = db.rawQuery("SELECT id AS _id, name, creationDate, completionDate, completed FROM " + TABLE_NAME,
                null);
        return cur;
    }

    public Task addTask(String name) {
        Date date = new Date();
        ContentValues contents = new ContentValues();
        contents.put("name", name);
        contents.put("creationDate", date.getTime());
        contents.put("completionDate", 0);
        contents.put("completed", 0);

        int id = (int) db.insert(TABLE_NAME, null, contents);
        if (id != -1) return new Task(id, name, date, false);
        else          return null;
    }

    public Task updateTask(int id, String name, boolean completed) {
        String[] ids = {String.valueOf(id)};

        Date date = new Date(getLongDate(id));

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("completed", (completed ? 1 : 0));

        if (completed) {
            date = new Date();
            values.put("completionDate", date.getTime());
        }

        int updatedContacts = db.update(TABLE_NAME, values, "id=?", ids);

        if (updatedContacts == 1) return new Task(id, name, date, completed);
        else return null;
    }

    private long getLongDate(int id) {
        String[] ids = {String.valueOf(id)};
        Cursor cursor = db.rawQuery("SELECT id AS _id, creationDate FROM " + TABLE_NAME + " WHERE id=?", ids);

        if (cursor.getCount() == 1) {
            if (cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex("creationDate"));
            }
        }
        return 0;
    }
}
