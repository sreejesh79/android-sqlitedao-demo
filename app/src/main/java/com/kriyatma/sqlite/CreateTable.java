package com.kriyatma.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sreejeshpillai on 22/05/15.
 */
public class CreateTable implements ICreateTable {
    private static CreateTable ourInstance = new CreateTable();

    private String queryString = "";
    public static CreateTable getInstance() {
        return ourInstance;
    }

    private CreateTable() {
    }

    public ICreateTable table(String name){
        queryString = "create table "+name+" ( ";
        return ourInstance;
    }

    public ICreateTable field(String name,JSONObject attributes){
       queryString += name+" ";
        try{
            String type = attributes.getString("type");
            if(type.toLowerCase() == "string"){
                type = "varchar";
            }
            queryString+=type+" ";

        }catch(JSONException e){
            Log.d("field type error",e.getMessage());
        }

        try{
            Boolean required = attributes.getBoolean("required");
            if(required)
            {
                queryString+="not null ";
            }
        }catch (JSONException e){
            Log.d("field required error",e.getMessage());
        }

        try{
            Boolean pk = attributes.getBoolean("primaryKey");
            if(pk)
            {
                queryString += "primary key ";
            }
        }catch (JSONException e){
            Log.d("field primaryKey error",e.getMessage());
        }

        try{
            Boolean autoincrement = attributes.getBoolean("autoIncrement");
            if(autoincrement)
            {
                queryString += "autoincrement ";
            }
        }catch (JSONException e){
            Log.d("field error",e.getMessage());
        }

        try{
            Boolean unique = attributes.getBoolean("unique");
            if(unique)
            {
                queryString += "unique ";
            }
        }catch(JSONException e){
            Log.d("field unique error",e.getMessage());
        }

        try{
           String def = attributes.getString("default");
            queryString += "default "+def+" ";
        }catch(JSONException e){
            Log.d("field default error",e.getMessage());
        }

        try{
            String def = attributes.getString("defaultsTo");
            queryString += "default "+def+" ";

        }catch(JSONException e){
            Log.d("field default error",e.getMessage());
        }
        queryString +=", ";
        return ourInstance;
    }

    public String getQueryString(){
        return queryString;
    }

    public void execute(SQLiteDatabase database){
        queryString = queryString.substring(0,queryString.length()-2);
        queryString += ");";
        database.execSQL(queryString);
    }

}
