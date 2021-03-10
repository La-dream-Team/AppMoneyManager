package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlLiteAccount extends SQLiteOpenHelper {
    // creation table
    private String creaton = "CREATE TABLE account ("
            + "id INTEGER PRIMARY KEY,"
            + "nom TEXT NOT NULL,"
            + "description TEXT NOT NULL,"
            + "devise TEXT NOT NULL,"
            + "montant FLOAT NOT NULL,"
            + "fk_person_id INT PRIMARY KEY;";

    public MySqlLiteAccount(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creaton);
    }//fin oncreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//fin onupgrade
}//fin class
}
