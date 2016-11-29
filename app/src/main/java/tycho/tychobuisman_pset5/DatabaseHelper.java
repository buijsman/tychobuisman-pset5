package tycho.tychobuisman_pset5;

/**
 * Created by Tycho on 28-11-2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tycho on 22-11-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "firstdb.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "todo";
    public static final String SUBTABLE_NAME = "todo_two";

    // Table columns
    public static final String _ID = "_id";
    public static final String ITEM = "item";
    public static final String SUB_ITEM = "sub_item";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT , item TEXT)";
        String query_two = "CREATE TABLE" + SUBTABLE_NAME + "(_id TEXT PRIMARY KEY , sub_item TEXT)";
        db.execSQL(query);
        db.execSQL(query_two);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SUBTABLE_NAME);
        onCreate(db);
    }
}