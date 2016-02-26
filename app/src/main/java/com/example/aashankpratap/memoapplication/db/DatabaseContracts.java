package com.example.aashankpratap.memoapplication.db;

import android.provider.BaseColumns;

/**
 * Database Contract class, please add all database schema(fields and table name) here only
 */
public final class DatabaseContracts {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatabaseContracts() {}

    /* Inner class that defines the table contents */
    public static abstract class MemoTable implements BaseColumns {
        public static final String TABLE_NAME = "memoentry";
        public static final String COLUMN_NAME_MEMO_ID = "id";
        public static final String COLUMN_NAME_MEMO_TAG = "tag";
        public static final String COLUMN_NAME_MEMO_CONTENT = "title";
    }
}
