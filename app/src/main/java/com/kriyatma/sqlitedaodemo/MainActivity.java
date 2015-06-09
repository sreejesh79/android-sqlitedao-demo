package com.kriyatma.sqlitedaodemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kriyatma.sqlite.SqliteAdapter;
import com.kriyatma.sqlite.dao.SqliteDAO;
import com.kriyatma.sqlitedaodemo.db.dao.Category;
import com.kriyatma.sqlitedaodemo.db.dao.Competitor;
import com.kriyatma.sqlitedaodemo.db.dao.FileData;
import com.kriyatma.sqlitedaodemo.db.dao.Product;
import com.kriyatma.sqlitedaodemo.db.dao.ProductCategory;

import org.json.JSONArray;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SqliteAdapter.getInstance(this).getWritableDatabase();
        Product product = Product.getInstance();
        JSONArray jsonArray = product.find()
                                    .sort("id","DESC")
                                    .limit(1)
                                    .execute()
                                    .json();
        Log.d("sqlitedaoquery",jsonArray.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
