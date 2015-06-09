package com.kriyatma.sqlitedaodemo.db.dao;

import com.kriyatma.sqlite.dao.SqliteDAO;

/**
 * Created by sreejeshpillai on 05/06/15.
 */
public class Competitor extends SqliteDAO {
    private static Competitor ourInstance = new Competitor();

    public static Competitor getInstance() {
        return ourInstance;
    }

    private Competitor() {
        primaryKey = "id";
        foriegnKey = "product_id";
        table_name = "competitor";

    }
}
