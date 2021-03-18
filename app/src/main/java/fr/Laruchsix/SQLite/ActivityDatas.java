package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Activity;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;

public class ActivityDatas {
    private String nomBase = "bdActivity.sqlite";
    private Integer versionBase = 1;
    private MySqlLiteActivity accesBD;
    private SQLiteDatabase bd;

    // constructeur
    public ActivityDatas(Context context) {
        accesBD = new MySqlLiteActivity(context, nomBase, null, versionBase);
    }


    public void ajout(Account account, Person owener, Activity activity){
        bd = accesBD.getWritableDatabase();
        String sql = "INSERT INTO activity (id, nom, description, date, value, account, owner) VALUES (\""
                + activity.getId() + "\" ,\""
                + activity.getName() + "\", \""
                + activity.getDescription() + "\", \""
                + activity.getDate().getTime() + "\", \""
                + activity.getValue() + "\", \""
                + account.getId() + "\", \""
                + owener.getId() + "\");";

        System.out.println("requete sql =" + sql);

        bd.execSQL(sql);
    }


    // on cherche les activités correspondante au compte de l'utilisateur
    public void findAllAct (Person owner, Account account){
        bd = accesBD.getReadableDatabase();
        Person ret = null;
        // requete sql
        String sql = "SELECT * FROM activity WHERE owner = \""
                + owner.getId() + "\" AND account = \"" + account.getId() + "\";";


        System.out.println("requete sql =" + sql);

        Cursor curseur = bd.rawQuery(sql, null );

        //System.out.println("voici le curseur " + curseur.getColumnNames().length);

        curseur.moveToFirst();
        // on compte le nombre de colomns
        for(int i=0 ; i<curseur.getCount(); i++)
        {
            // on recupère toute les informations
            int id = curseur.getInt(0);
            String nom = curseur.getString(1);
            String desc = curseur.getString(2);
            Date date = new Date(curseur.getLong(3));
            float montant = curseur.getFloat(4);

            account.addActivity(montant, desc, nom, date, id);
            if(!curseur.moveToNext())
                break;
        }
    }

    public void removeActivity(Person owner, Account account, int id){
        bd = accesBD.getWritableDatabase();
        String sql = "DELETE FROM activity WHERE"
                + " id = \"" + id + "\" AND"
                + " account = \"" + account.getId() + "\" AND"
                + " owner = \"" + owner.getId() + "\";";
        //System.out.println("requete sql =" + sql);

        bd.execSQL(sql);
    }

    public void removeAllActivities(Person owner, Account account){
        for(Activity currentAct :  account.getActivities()){
            removeActivity(owner, account, currentAct.getId());
        }
    }

}
