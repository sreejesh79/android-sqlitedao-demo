package com.kriyatma.sqlitedaodemo.db.dao;

import com.kriyatma.sqlite.dao.SqliteDAO;

/**
 * Created by sreejeshpillai on 05/06/15.
 */
public class Product extends SqliteDAO {
    private static Product ourInstance = new Product();

    public static Product getInstance() {
        return ourInstance;
    }

    private Product() {
        primaryKey = "id";
        table_name = "product";
        fields = new String[]{
          "id",
                "title",
                "image"
        };
    }
}
