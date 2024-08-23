package com.cwtstudio.expensemanager.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cwtstudio.expensemanager.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DatabaseDAO
{
    private DatabaseHelper mHelper;

    public DatabaseDAO(Context context)
    {
        mHelper = new DatabaseHelper(context);
    }

    public long insertTransaction(Transaction transaction)
    {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        long result = -1;
        try
        {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_AMOUNT, transaction.getAmount());
            values.put(DatabaseHelper.COLUMN_TYPE, transaction.getType());
            values.put(DatabaseHelper.COLUMN_NOTE, transaction.getNote());
            values.put(DatabaseHelper.COLUMN_DATE, transaction.getDate());
            result = database.insert(DatabaseHelper.TABLE_NAME, null, values);
        } finally
        {
            database.close();
        }
        return result;
    }
    public Transaction getTransactionById(int id) {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = null;
        Transaction transaction = null;
        try {
            cursor = database.query(DatabaseHelper.TABLE_NAME, null, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String amount = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
                transaction = new Transaction(amount, date, id, note, type);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return transaction;
    }


    public long deleteTransaction(int id)
    {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        long result = -1;
        try
        {
            result = database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        } finally
        {
            database.close();
        }
        return result;
    }

    private List<Transaction> getTransactions(String selection, String[] selectionArgs)
    {
        List<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor = null;
        try
        {
            cursor = database.query(DatabaseHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null);
            while (cursor != null && cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String amount = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TYPE));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOTE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
                transactions.add(new Transaction(amount, date, id, note, type));
            }
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            database.close();
        }
        return transactions;
    }

    public List<Transaction> getAllTransactions()
    {
        return getTransactions(null, null);
    }

    public List<Transaction> getAllIncomes()
    {
        return getTransactions(DatabaseHelper.COLUMN_TYPE + " = ?", new String[]{"Income"});
    }

    public List<Transaction> getAllExpenses()
    {
        return getTransactions(DatabaseHelper.COLUMN_TYPE + " = ?", new String[]{"Expense"});
    }

    public List<Transaction> getAllBudgets()
    {
        return getTransactions(DatabaseHelper.COLUMN_TYPE + " = ?", new String[]{"Budget"});
    }

    public void setBudget(float budget) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("budget", budget); // Assuming the column name is 'budget'

        db.update("Settings", values, null, null);
    }

    public long updateTransaction(Transaction updatedTransaction) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        long rowsAffected = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_AMOUNT, updatedTransaction.getAmount());
            values.put(DatabaseHelper.COLUMN_TYPE, updatedTransaction.getType());
            values.put(DatabaseHelper.COLUMN_NOTE, updatedTransaction.getNote());
            values.put(DatabaseHelper.COLUMN_DATE, updatedTransaction.getDate());
            // Update the transaction based on its ID
            rowsAffected = database.update(DatabaseHelper.TABLE_NAME, values,
                    DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(updatedTransaction.getId())});
        } finally {
            database.close();
        }
        return rowsAffected;

    }
}