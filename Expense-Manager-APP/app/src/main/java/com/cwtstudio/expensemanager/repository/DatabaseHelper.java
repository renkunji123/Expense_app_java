package com.cwtstudio.expensemanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_manager.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "transactions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_DATE = "date";

    private static final String CREATE_TABLE_TRANSACTIONS = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_AMOUNT + " TEXT, " +
            COLUMN_TYPE + " TEXT, " +
            COLUMN_NOTE + " TEXT, " +
            COLUMN_DATE + " TEXT);";

    private static final String CREATE_TABLE_SETTINGS = "CREATE TABLE Settings (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "budget REAL DEFAULT 0);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone_number";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSACTIONS);
        db.execSQL(CREATE_TABLE_SETTINGS);
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USERNAME + " TEXT, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_FULL_NAME + " TEXT, "
                + COLUMN_GENDER + " TEXT, "
                + COLUMN_AGE + " INTEGER, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PHONE + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);
    }
    public void addUser(String username, String password, String fullName, String gender, int age, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS Settings");
        onCreate(db);
    }

    public Cursor getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE "
                + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        return db.rawQuery(query, new String[]{username, password});
    }
}
