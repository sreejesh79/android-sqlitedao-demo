package com.kriyatma.sqlitedaodemo.db.dao;

import com.kriyatma.sqlite.dao.ForeignKey;
import com.kriyatma.sqlite.dao.SqliteDAO;

/**
 * Created by sreejeshpillai on 05/06/15.
 */
public class ProductCategory extends SqliteDAO {
    private static ProductCategory ourInstance = new ProductCategory();

    public static ProductCategory getInstance() {
        return ourInstance;
    }

    private ProductCategory() {
        primaryKey = "id";
        foreignKeys.add(new ForeignKey("product","product"));
        foreignKeys.add(new ForeignKey("image","filedata"));
        foreignKeys.add(new ForeignKey("category","category"));
        table_name = "productcategory";
        fields = new String[]{
          "id as ProductCategoryID",
                "details"
        };
    }
}
