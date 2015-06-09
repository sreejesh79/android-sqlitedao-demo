package com.kriyatma.sqlitedaodemo.db.dao;

import com.kriyatma.sqlite.dao.SqliteDAO;

/**
 * Created by sreejeshpillai on 05/06/15.
 */
public class FileData extends SqliteDAO {
    private static FileData ourInstance = new FileData();

    public static FileData getInstance() {
        return ourInstance;
    }

    private FileData() {
        primaryKey = "id";
        table_name = "filedata";
        fields = new String[]{
                "id as FileID",
                "filepath",
                "mimetype"
        };

    }
}
