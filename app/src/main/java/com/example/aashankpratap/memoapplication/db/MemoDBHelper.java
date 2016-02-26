package com.example.aashankpratap.memoapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aashankpratap.memoapplication.model.Memo;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to perform read and write operation on database
 */
public class MemoDBHelper extends SQLiteOpenHelper {

    private static MemoDBHelper sInstance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MemoTable";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseContracts.MemoTable.TABLE_NAME;

    public static MemoDBHelper getInstance(Context context) {
        if(sInstance == null)
            sInstance = new MemoDBHelper(context);
        return sInstance;
    }

    private MemoDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        // create table
        sql = "CREATE TABLE IF NOT EXISTS " + DatabaseContracts.MemoTable.TABLE_NAME + " (" +
                DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 0, " +
                DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_TAG + " TEXT NOT NULL, " +
                DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_CONTENT + " TEXT " +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     *
     * @param tag
     * @param content
     * @return greater than 0 on success and -1 otherwise
     */
    public long insertMemo (String tag, String content) {
        long retVal;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_TAG,tag);
        contentValues.put(DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_CONTENT, content);
        retVal = db.insert(DatabaseContracts.MemoTable.TABLE_NAME, null, contentValues);
        db.close();
        return retVal;
    }

    public List<Memo> getData (String tag) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Memo> listOfMemo = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from " + DatabaseContracts.MemoTable.TABLE_NAME + " where tag like \"%" + tag + "%\"", null);
            while(cursor.moveToNext()) {
                Memo memo = new Memo();
                memo.setContent(cursor.getString(cursor.getColumnIndex(DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_CONTENT)));
                memo.setTag(cursor.getString(cursor.getColumnIndex(DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_TAG)));
                listOfMemo.add(memo);
            }

        }finally {
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return listOfMemo;
    }

    public List<Memo> getAllMemo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Memo> listOfMemo = new ArrayList<>();
        try {
            cursor = db.query(DatabaseContracts.MemoTable.TABLE_NAME, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Memo memo = new Memo();
                memo.setContent(cursor.getString(cursor.getColumnIndex(DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_CONTENT)));
                memo.setTag(cursor.getString(cursor.getColumnIndex(DatabaseContracts.MemoTable.COLUMN_NAME_MEMO_TAG)));
                listOfMemo.add(memo);
            }
        } finally { // cursor should always be closed in finally block, because even if some exception occurred cursor instance
                    // won't be leaked
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return listOfMemo;
    }
}

