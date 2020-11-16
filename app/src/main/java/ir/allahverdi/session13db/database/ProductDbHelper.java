package ir.allahverdi.session13db.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.allahverdi.session13db.Const;
import ir.allahverdi.session13db.entity.Product;

public class ProductDbHelper extends DbHelper {

    private final String TABLE_NAME = Product.class.getSimpleName();
    private final String FIELD_ID = "id";
    private final String FIELD_NAME = "name";
    private final String FIELD_PRICE = "price";
    private final String FIELD_IMAGEID = "imageId";
    //private final String FIELD_SELLER = "seller";


    public ProductDbHelper(@Nullable Context context) {
        super(context);
    }


    public long insert(Product product) {
        Log.e(Const.TAG_DB, "Insert()");

        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        try {
            ContentValues cv = new ContentValues();
            //cv.put(FIELD_ID, product.getId());
            cv.put(FIELD_NAME, product.getName());
            cv.put(FIELD_PRICE, product.getPrice());
            cv.put(FIELD_IMAGEID, product.getImageId());

            result = db.insert(TABLE_NAME, null, cv);

        } catch (Exception ex) {

        } finally {
            db.close();
        }

        return result;
    }

    public List<Product> select() {
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_NAME, FIELD_PRICE, FIELD_IMAGEID};
        Cursor cursor = db.query(TABLE_NAME, col, null, null, null, null, null);

        if (cursor.getCount() == 0) {
            db.close();
            return productList;
        }

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
            int price = cursor.getInt(cursor.getColumnIndex(FIELD_PRICE));
            int imageId = cursor.getInt(cursor.getColumnIndex(FIELD_IMAGEID));

            Product product = new Product(id, name, price, imageId);
            productList.add(product);
        }

        cursor.close();
        db.close();
        return productList;
    }

    public List<Product> select(String param) {
        List<Product> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_NAME, FIELD_PRICE, FIELD_IMAGEID};
        Cursor cursor = db.query(TABLE_NAME, col, "name like ?", new String[]{"%"+param+"%"}, null, null, null);

        if (cursor.getCount() == 0)
            return productList;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
            int price = cursor.getInt(cursor.getColumnIndex(FIELD_PRICE));
            int imageId = cursor.getInt(cursor.getColumnIndex(FIELD_IMAGEID));

            Product product = new Product(id, name, price, imageId);
            productList.add(product);
        }

        cursor.close();
        return productList;
    }

    public Product select(int pid) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] col = {FIELD_ID, FIELD_NAME, FIELD_PRICE, FIELD_IMAGEID};
        Cursor cursor = db.query(TABLE_NAME, col, "id=?", new String[]{String.valueOf(pid)}, null, null, null);

        if (cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(FIELD_ID));
            String name = cursor.getString(cursor.getColumnIndex(FIELD_NAME));
            int price = cursor.getInt(cursor.getColumnIndex(FIELD_PRICE));
            int imageId = cursor.getInt(cursor.getColumnIndex(FIELD_IMAGEID));

            Product product = new Product(id, name, price, imageId);

            cursor.close();
            return product;
        }

        return null;
    }

    public int update(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(FIELD_NAME, product.getName());
        cv.put(FIELD_PRICE, product.getPrice());
        return db.update(TABLE_NAME, cv, "id=?", new String[]{String.valueOf(product.getId())});
    }

    public int delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});

    }
}
