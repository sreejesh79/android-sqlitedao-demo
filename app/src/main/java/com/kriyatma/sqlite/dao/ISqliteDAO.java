package com.kriyatma.sqlite.dao;

import android.database.Cursor;

import org.json.JSONArray;

/**
 * Created by sreejeshpillai on 20/05/15.
 */
public interface ISqliteDAO {
    public ISqliteDAO table(String table);
    public ISqliteDAO fields(String[] fields);
    public ISqliteDAO find();
    public ISqliteDAO findOne();
    public ISqliteDAO select(ISqliteDAO... daos);
    public ISqliteDAO select(String selection);
    public ISqliteDAO createJoins(ISqliteDAO... daos);
    public ISqliteDAO save(String values);
    public ISqliteDAO insert(Object[] values);
    public ISqliteDAO update(String[] columns, Object[] values);
    public ISqliteDAO where(String clause);
    public ISqliteDAO join(ISqliteDAO dao);
    public ISqliteDAO join(String table);
    public ISqliteDAO leftJoin(String table);
    public ISqliteDAO on(String on);
    public ISqliteDAO orWhere(String clause);
    public ISqliteDAO limit(Integer limit);
    public ISqliteDAO sort(String field, String sortby);
    public ISqliteDAO execute();
    public ISqliteDAO execute(String queryString);
    public void execSql();
    public Cursor cursor();
    public JSONArray json();
    public String getTable();
    public  String[] getFields();

}
