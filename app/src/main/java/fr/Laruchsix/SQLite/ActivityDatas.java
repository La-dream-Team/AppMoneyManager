package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ActivityDatas {
    private String nomBase = "bdActivity.sqlite";
    private Integer versionBase = 1;
    private MySqlLitePerson accesBD;
    private SQLiteDatabase bd;

    // constructeur
    public ActivityDatas(Context context) {
        accesBD = new MySqlLitePerson(context, nomBase, null, versionBase);
    }

    // TODO , agregar metodos que sean necesarion para el uso de una activity
    // ejemplo, agregar una actividad a un usuario
    // ver una actividad
    // borrar, etc
}
