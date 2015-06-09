package com.kriyatma.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kriyatma.events.Event;
import com.kriyatma.events.IEventHandler;
import com.kriyatma.services.HTTPService;
import com.kriyatma.services.SERVICE_CONSTANTS;
import com.kriyatma.services.ServiceEvent;
import com.kriyatma.sqlite.dao.ISqliteDAO;
import com.kriyatma.sqlite.dao.SqliteDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sreejeshpillai on 22/05/15.
 */
public class SqliteAdapter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "sqlitedaodemo.db";
    private static final int DATABASE_VERSION = 1;
    private static SqliteAdapter ourInstance = null;

    private SQLiteDatabase database;
    private HTTPService httpService;


    public static SqliteAdapter getInstance(Context context) {
        if(null == ourInstance)
       {
           ourInstance = new SqliteAdapter(context);
       }
        return ourInstance;
    }

    public static SqliteAdapter getInstance() {
        if(null == ourInstance)
        {
             throw new IllegalArgumentException("Parameter context missing");
        }
        return ourInstance;
    }

    public SqliteAdapter(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate","in create SQLiteDatabase1");
        database = db;
        // database.execSQL(DATABASE_CREATE);
        initialize();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // database.execSQL(DATABASE_CREATE);
    }

    protected void initialize()
    {
        createTables();
        insertData();
    }

    protected void createTables(){
        JSONObject primarykey = new JSONObject();
        JSONObject foriegnKey = new JSONObject();
        JSONObject stringField = new JSONObject();
        JSONObject textField = new JSONObject();
        try{
            primarykey.put("type","integer");
            primarykey.put("primaryKey",true);
            primarykey.put("autoincrement",true);
            foriegnKey.put("type","integer");
            stringField.put("type","string");
            textField.put("type","text");
        }catch(JSONException e){
            Log.d("JSONException",e.getMessage());
        }
        ICreateTable createTable = CreateTable.getInstance();
        createTable.table("product");
        createTable.field("id", primarykey);
        createTable.field("title", stringField);
        createTable.field("image", foriegnKey);
        createTable.field("detail", textField);
        createTable.execute(database);

        createTable.table("competitor");
        createTable.field("id", primarykey);
        createTable.field("product_id", foriegnKey);
        createTable.execute(database);

        createTable.table("category");
        createTable.field("id", primarykey);
        createTable.field("term", stringField);
        createTable.execute(database);

        createTable.table("productcategory");
        createTable.field("id", primarykey);
        createTable.field("product", foriegnKey);
        createTable.field("image", foriegnKey);
        createTable.field("category", foriegnKey);
        createTable.field("details", textField);
        createTable.execute(database);

        createTable.table("filedata");
        createTable.field("id", primarykey);
        createTable.field("filepath", stringField);
        createTable.field("mimetype",stringField);
        createTable.execute(database);


    }

    protected void insertData(){
        SqliteDAO productDao = new SqliteDAO(database);
        String[] productFields = new String[]{"title","image","detail"};
        Object[] productValues1 = new Object[]{"Product1",1,"Product Detail 1"};
        Object[] productValues2 = new Object[]{"Product2",2,"Product Detail 2"};
        Object[] productValues3 = new Object[]{"Product3",3,"Product Detail 3"};
        productDao.table("product")
                .fields(productFields)
                .insert(productValues1)
                .insert(productValues2)
                .insert(productValues3);

        SqliteDAO competitor = new SqliteDAO(database);
        String[] competitorFields = new String[]{"product_id"};
        Object[] competitorValues1 = new Object[]{1};
        Object[] competitorValues2 = new Object[]{2};
        Object[] competitorValues3 = new Object[]{3};
        competitor.table("competitor")
                .fields(competitorFields)
                .insert(competitorValues1)
                .insert(competitorValues2)
                .insert(competitorValues3);

        SqliteDAO category = new SqliteDAO(database);
        String[] categoryFields = new String[]{"term"};
        Object[] categoryValues1 = new Object[]{"Category 1"};
        Object[] categoryValues2 = new Object[]{"Category 2"};
        Object[] categoryValues3 = new Object[]{"Category 3"};
        category.table("category")
                .fields(categoryFields)
                .insert(categoryValues1)
                .insert(categoryValues2)
                .insert(categoryValues3);

        SqliteDAO productcategory = new SqliteDAO(database);
        String[] productcategoryFields = new String[]{"product","category","image","details"};
        Object[] productcategoryValues1 = new Object[]{1,1,4,"Product1 Category1"};
        Object[] productcategoryValues2 = new Object[]{1,2,5,"Product1 Category2"};
        Object[] productcategoryValues3 = new Object[]{1,3,6,"Product1 Category3"};
        Object[] productcategoryValues4 = new Object[]{2,1,7,"Product2 Category1"};
        Object[] productcategoryValues5 = new Object[]{2,2,8,"Product2 Category2"};
        Object[] productcategoryValues6 = new Object[]{2,3,9,"Product2 Category3"};
        Object[] productcategoryValues7 = new Object[]{3,1,10,"Product3 Category1"};
        Object[] productcategoryValues8 = new Object[]{3,2,11,"Product3 Category2"};
        Object[] productcategoryValues9 = new Object[]{3,3,12,"Product3 Category3"};
        productcategory.table("productcategory")
                        .fields(productcategoryFields)
                        .insert(productcategoryValues1)
                        .insert(productcategoryValues2)
                        .insert(productcategoryValues3)
                        .insert(productcategoryValues4)
                        .insert(productcategoryValues5)
                        .insert(productcategoryValues6)
                        .insert(productcategoryValues7)
                        .insert(productcategoryValues8)
                        .insert(productcategoryValues9);

        SqliteDAO fileData = new SqliteDAO(database);
        String[] fileDataFields = new String[]{"filepath","mimetype"};
        Object[] fileDataValues1 = new Object[]{"1.jpg","jpg"};
        Object[] fileDataValues2 = new Object[]{"2.jpg","jpg"};
        Object[] fileDataValues3 = new Object[]{"3.jpg","jpg"};
        Object[] fileDataValues4 = new Object[]{"4.jpg","jpg"};
        Object[] fileDataValues5 = new Object[]{"5.jpg","jpg"};
        Object[] fileDataValues6 = new Object[]{"6.jpg","jpg"};
        Object[] fileDataValues7 = new Object[]{"7.jpg","jpg"};
        Object[] fileDataValues8 = new Object[]{"8.jpg","jpg"};
        Object[] fileDataValues9 = new Object[]{"9.jpg","jpg"};
        Object[] fileDataValues10 = new Object[]{"10.jpg","jpg"};
        Object[] fileDataValues11 = new Object[]{"11.jpg","jpg"};
        Object[] fileDataValues12 = new Object[]{"12.jpg","jpg"};
        fileData.table("filedata")
                .fields(fileDataFields)
                .insert(fileDataValues1)
                .insert(fileDataValues2)
                .insert(fileDataValues3)
                .insert(fileDataValues4)
                .insert(fileDataValues1)
                .insert(fileDataValues6)
                .insert(fileDataValues7)
                .insert(fileDataValues8)
                .insert(fileDataValues9)
                .insert(fileDataValues10)
                .insert(fileDataValues11)
                .insert(fileDataValues12);

    }


}
