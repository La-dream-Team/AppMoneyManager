package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlLite extends SQLiteOpenHelper {
    // creation table
    private
    public MySqlLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    string creation ="CREATE TABLE person (" 
            ") "
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL();
    }//fin oncreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//fin onupgrade
}//fin class
