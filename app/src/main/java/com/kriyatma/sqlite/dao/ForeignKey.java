package com.kriyatma.sqlite.dao;

/**
 * Created by sreejeshpillai on 29/05/15.
 */
public class ForeignKey {
    private String foriegnKey;
    private String tableName;

    public ForeignKey(String fk, String table){
        foriegnKey = fk;
        tableName = table;
    }

    public String getForiegnKey(){
        return foriegnKey;
    }

    public String getTableName(){
        return tableName;
    }
}
