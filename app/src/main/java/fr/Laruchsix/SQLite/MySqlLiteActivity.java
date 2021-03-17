package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlLiteActivity  extends SQLiteOpenHelper {
    // creation table
    private String creation = "CREATE TABLE activity ( "
            + "id INTEGER NOT NULL,"
            + "nom TEXT NOT NULL,"
            + "description TEXT NOT NULL,"
            + "date LONG NOT NULL,"
            + "value FLOAT NOT NULL,"
            + "account INTEGER NOT NULL," //FK
            + "owner INTEGER NOT NULL," //FK
            + "PRIMARY KEY(id,account,owner));";

    public MySqlLiteActivity(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println(creation);
        db.execSQL(creation);
    }//fin oncreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//fin onupgrade
}//fin class
