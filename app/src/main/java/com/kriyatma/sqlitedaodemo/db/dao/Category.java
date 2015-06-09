package com.kriyatma.sqlitedaodemo.db.dao;

import com.kriyatma.sqlite.dao.SqliteDAO;

/**
 * Created by sreejeshpillai on 05/06/15.
 */
public class Category extends SqliteDAO {
    private static Category ourInstance = new Category();

    public static Category getInstance() {
        return ourInstance;
    }

    private Category() {
        primaryKey = "id";
        table_name = "category";
        fields = new String[]{
          "id as CategoryID",
                "term"
        };
    }
}
