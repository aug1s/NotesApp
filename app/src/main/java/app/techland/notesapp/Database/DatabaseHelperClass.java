package app.techland.notesapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperClass extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "NotesApp.db";
    public static final String TABLE_NAME = "Persons";
    public static final String Column1 = "id";
    public static final String Column2 = "title";
    public static final String Column3 = "description";

    public DatabaseHelperClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +Column1+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +Column2+ " TEXT," +Column3+ " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean INSERT_IN_LAB(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column2, title);
        values.put(Column3, description);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return result;
    }

    public boolean deleteDataById(Integer position) {
        String id = Integer.toString(position);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Column1 + "=?",new String[]{id});
        return true;
    }

    public boolean Update_IN_LAB(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Column2, title);
        values.put(Column3, description);
        long result = db.update(TABLE_NAME,  values,"id="+id,null);
        if (result == -1)
            return false;
        else
            return true;
    }

}
