package tycho.tychobuisman_pset5;

/**
 * Created by Tycho on 28-11-2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String item) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ITEM, item);

        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.ITEM };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        return cursor;
    }

    public int delete(Long _id) {
        return database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public void insertSubTable(String item) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUB_ITEM, item);

        database.insert(DatabaseHelper.SUBTABLE_NAME, null, contentValue);
    }

    public Cursor fetchSubTable() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUB_ITEM };
        Cursor cursor = database.query(DatabaseHelper.SUBTABLE_NAME, columns, null, null, null, null, null);

        return cursor;
    }

    public int deleteSubTable(Long _id) {
        return database.delete(DatabaseHelper.SUBTABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}