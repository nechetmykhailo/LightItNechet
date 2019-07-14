package com.example.lightitnechet;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteConnector extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private Cursor cursor;
    private Context context;

    public SQLiteConnector(Context context) {
        super(context, "Products", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            db.execSQL("create table ProductsTable (_id integer primary key autoincrement,"
                    + "idProducts INTEGER,"
                    + "title TEXT,"
                    + "img TEXT,"
                    + "text TEXT )");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            db.execSQL("DROP TABLE IF EXISTS ProductsTable");
        }
    }

    public void insertProduct(int idProducts ,String title, String img, String text) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("idProducts", idProducts);
        values.put("title", title);
        values.put("img", img);
        values.put("text", text);

        db.insert("ProductsTable", null, values);
        db.close();
    }

    public void clearProducts() {
        db = this.getWritableDatabase();
        db.delete("ProductsTable", null, null);
        db.close();
    }
}
