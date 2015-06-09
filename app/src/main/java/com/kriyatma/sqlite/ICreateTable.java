package com.kriyatma.sqlite;

import android.database.sqlite.SQLiteDatabase;

import org.json.JSONObject;

/**
 * Created by sreejeshpillai on 22/05/15.
 */
public interface ICreateTable {
    public ICreateTable table(String name);
    public ICreateTable field(String name, JSONObject attributes);
    public String getQueryString();
    public void execute(SQLiteDatabase database);
}
