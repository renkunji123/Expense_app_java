package com.cwtstudio.expensemanager.model;


public class Transaction
{
    private int id;
    private String note;
    private String amount;
    private String  type;
    private String date;

    public Transaction()
    {
    }

    public Transaction(String amount, String date, int id, String note, String  type)
    {
        this.amount = amount;
        this.date = date;
        this.id = id;
        this.note = note;
        this.type = type;
    }

    public Transaction(String amount, String date, String note, String type)
    {
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.type = type;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getDate()
    {
        return date;
    }

    public int getId()
    {
        return id;
    }

    public String getNote()
    {
        return note;
    }

    public String  getType()
    {
        return type;
    }
}
