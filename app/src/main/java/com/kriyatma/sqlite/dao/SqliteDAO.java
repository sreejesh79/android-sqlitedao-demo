package com.kriyatma.sqlite.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kriyatma.sqlite.SqliteAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by sreejeshpillai on 19/05/15.
 */
public class SqliteDAO implements ISqliteDAO {
    protected String table_name = "";
    protected String[] fields = new String[]{};
    protected String primaryKey;
    protected String foriegnKey;
    protected List<ForeignKey>foreignKeys = new ArrayList<ForeignKey>();
    protected SQLiteDatabase database;
    protected Cursor queryCursor;
    protected String queryBuilder = "";
    protected String strLimit = "";
    protected String strSort = "";

    private Boolean isMultiSelect = true;
    private Boolean isWhereStart = false;


    public SqliteDAO()
    {
        database = SqliteAdapter.getInstance().getWritableDatabase();
    }

    public SqliteDAO(SQLiteDatabase db){
        database = db;
    }

    public ISqliteDAO table(String table){
        table_name = table;
        return this;
    }

    public ISqliteDAO fields(String [] fields){
        this.fields = fields;
        return this;
    }

    public ISqliteDAO find()
    {
        String strFields = "";
        strFields = fieldsToString(fields);
        queryBuilder = "SELECT "+strFields.toString()+" FROM "+table_name+" ";
        isMultiSelect = true;
        isWhereStart = false;
        //dispatchEvent(new Event("new event",this));
        return this;
    }

    public ISqliteDAO findOne()
    {
        String strFields = "";
        strFields = fieldsToString(fields);
        strLimit = "LIMIT 1";
        queryBuilder = "SELECT "+strFields.toString()+" FROM "+table_name+" ";
        isMultiSelect = false;
        isWhereStart = false;
        return this;
    }

    public ISqliteDAO select(ISqliteDAO... daos){
        queryBuilder = "SELECT ";
        String strFields = "";
        strFields = fieldsToString(fields,table_name)+",";

        String strTables = this.table_name;
        Integer mainCounter = 0;
        for(ISqliteDAO dao:daos){
            Integer counter = 0;
            SqliteDAO sqliteDAO = (SqliteDAO)dao;

            if(mainCounter < daos.length-1){
                strFields+= fieldsToString(sqliteDAO.fields,sqliteDAO.table_name)+",";
                //strFields+=sqliteDAO.table_name+".*"+",";
                //strTables+= sqliteDAO.table_name+",";
            }
            else{
                strFields+= fieldsToString(sqliteDAO.fields,sqliteDAO.table_name);
             //   strFields+=sqliteDAO.table_name+".*";
                //strTables+= sqliteDAO.table_name;
            }
            mainCounter++;
        }
        queryBuilder+= strFields + " FROM "+strTables+" ";
        return this;
    }


    public ISqliteDAO select(String selection){
        queryBuilder = selection+" ";
        return this;
    }
    public ISqliteDAO createJoins(ISqliteDAO... daos){
        select(daos);
        for(ISqliteDAO dao:daos){
            join(dao);
        }
        return this;
    }
    public ISqliteDAO save(String values){

        String fields_str = "";
        Integer counter = 0;
        for(String field:fields){
            if(field != primaryKey ){
                if(counter < fields.length -1)
                {
                    fields_str += field+",";
                }
                else
                {
                    fields_str += field;
                }
            }

            counter++;

        }
        String insertQuery = "INSERT INTO "+table_name+" ( "+fields_str+") VALUES("+values.toString()+")";
        Log.d("save",insertQuery);
       database.execSQL(insertQuery);
        return this;
    }

    public ISqliteDAO insert(Object[] values){
        Log.d("ISqliteDAO","insert");
        String strValues = "";
        Integer counter = 0;
        for(Object value:values){
            if(counter < values.length-1){
                strValues += "'"+value.toString()+"'"+",";
            }
            else
            {
                strValues += "'"+value.toString()+"'";
            }
            counter++;
        }
        return this.save(strValues);
    }
    public ISqliteDAO update(String[]columns,Object[]values){
        queryBuilder = "UPDATE "+table_name+" SET ";
        Integer counter = 0;
        for(String column:columns){
            queryBuilder += column+" = ";
            if(counter < columns.length-1){
                queryBuilder += "'"+values[counter].toString()+"'"+",";
            }
            else
            {
                queryBuilder += "'"+values[counter].toString()+"'";
            }
            counter++;
        }
        queryBuilder+= " ";
        return this;
    }
    public ISqliteDAO where(String clause){
        if(!isWhereStart)
        {
            queryBuilder+="WHERE "+clause+" ";
        }
        else
        {
            queryBuilder+="and "+clause+" ";
        }
        isWhereStart = true;
        return this;
    }

    public ISqliteDAO orWhere(String clause){
        if(!isWhereStart)
        {
            queryBuilder+=clause+" ";
        }
        else
        {
            queryBuilder+="or "+clause+" ";
        }
        isWhereStart = true;
        return this;
    }

    public ISqliteDAO limit(Integer limit) {
        if(isMultiSelect)
        {
            strLimit = "LIMIT "+limit.toString()+" ";
        }
        return this;
    }

    public ISqliteDAO sort(String field,String sortby){
        strSort = "ORDER BY "+field+" "+sortby+" ";
        return this;
    }

     public ISqliteDAO join(ISqliteDAO dao){
         SqliteDAO sqliteDAO = (SqliteDAO)dao;
         String onStr = "";
         Log.d("in join",sqliteDAO.table_name);
         join(sqliteDAO.table_name);
         if(this.foreignKeys.size() > 0){
             String fk = getFK(sqliteDAO.table_name);
             onStr = this.table_name+"."+fk+"="+sqliteDAO.table_name+"."+sqliteDAO.primaryKey;
         }
         else if(this.foriegnKey != null && this.foriegnKey != ""){
             onStr = this.table_name+"."+this.foriegnKey+"="+sqliteDAO.table_name+"."+sqliteDAO.primaryKey;
         }
         on(onStr);
         //queryBuilder += on(onStr);
         //Log.d("in join queryBuilder ",sqliteDAO.table_name);

         return this;
     }
    public ISqliteDAO join(String table){
        Log.d("in string join", table);
        queryBuilder += "INNER JOIN "+table+" ";
        Log.d("in string join",queryBuilder);
        return this;
    }

    public ISqliteDAO leftJoin(String table){
        queryBuilder += "LEFT JOIN "+table+" ";
        return this;
    }
    public ISqliteDAO on(String on){
        queryBuilder += "ON "+on+" ";
        return this;
    }
    public ISqliteDAO execute(){
        queryBuilder+=strSort;
        queryBuilder+=strLimit;
        Log.d("queryBuilder",queryBuilder);
        queryCursor = database.rawQuery(queryBuilder,null);
        return this;
    }

    public ISqliteDAO execute(String queryString){
        queryBuilder = queryString;
        queryCursor = database.rawQuery(queryBuilder,null);
        return this;
    }

    public void execSql(){
        Log.d("queryBuilder",queryBuilder);
        database.execSQL(queryBuilder);
    }

    public Cursor cursor(){
        return queryCursor;
    }

   public JSONArray json(){
       return cur2json(queryCursor);
   }

    public String getTable(){
        return table_name;
    }

    public String[] getFields(){
        return fields;
    }

    public String getFK(String table){
        String fk = "";
        Iterator<ForeignKey> fkIterator = foreignKeys.iterator();
        while(fkIterator.hasNext()){
            ForeignKey fkObj = (ForeignKey)fkIterator.next();
            if(fkObj.getTableName() == table){
                fk = fkObj.getForiegnKey();
                break;
            }
        }
        return fk;
    }

    public   JSONArray cur2json(Cursor cursor)
    {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                        Log.d("cur2json", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;
    }

    public String fieldsToString(String[]fields){
        String strFields = "";

        if(fields.length>0){
            Integer counter = 0;
            for(String field:fields){
                if(counter < fields.length-1){
                    strFields += field+",";
                }
                else {
                    strFields += field;
                }
                counter++;
            }
        }
        else
        {
            strFields = "*";
        }

        return strFields;
    }

    public String fieldsToString(String[]fields,String tableName){
        String strFields = "";
        Integer counter = 0;
        if(fields.length>0){
            for(String field:fields){
                if(counter < fields.length-1){
                    strFields += tableName+"."+field+",";
                }
                else {
                    strFields += tableName+"."+field;
                }
                counter++;
            }
        }
        else
        {
            strFields += tableName+".*";
        }

        return strFields;
    }
}
