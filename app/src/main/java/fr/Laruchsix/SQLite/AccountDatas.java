package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;

public class AccountDatas {
    private String nomBase = "bdAccount.sqlite";
    private Integer versionBase = 1;
    private MySqlLiteAccount accesBD;
    private SQLiteDatabase bd;

    // constructeur
    public AccountDatas(Context context) {
        accesBD = new MySqlLiteAccount(context, nomBase, null, versionBase);
    }

    public void ajout(Account account, Person owener){
        bd = accesBD.getWritableDatabase();
        String sql = "INSERT INTO account (id, nom, description, devise, montant, fk_person_id) VALUES (\""
                + account.getId() + "\" ,\""
                + account.getName() + "\", \""
                + account.getDescription() + "\", \""
                + account.getDevise().toString() + "\", \""
                + account.getDefaultBalance() + "\", \""
                + owener.getId() + "\");";

        //System.out.println("requete sql =" + sql);

        bd.execSQL(sql);
    }

    // recherche un utilisateur dans la table Person
    // Renvoir un objet Person
    public Person loadAcc(Person owner){
        bd = accesBD.getReadableDatabase();
        Person ret = null;
        // requete sql
        String sql = "SELECT * FROM account WHERE fk_person_id = \""
                + owner.getId() + "\";";

        //System.out.println("requete sql =" + sql);

        Cursor curseur = bd.rawQuery(sql, null );

        //System.out.println("voici le curseur " + curseur.getColumnNames().length);

        curseur.moveToFirst();
        // on compte le nombre de colomns
        do{
            // on recupère toute les informations
            int id = curseur.getInt(0);
            String nom = curseur.getString(1);
            String desc = curseur.getString(2);
            Devise dev = Devise.valueOf(curseur.getString(3));
            float montant = curseur.getFloat(4);

            owner.addNewAccount(montant, nom, desc, dev, id);
        }while(curseur.moveToNext());

        // on retourne l'utilisateur
        return ret;
    }

    public int getMaxiD(){
        bd = accesBD.getReadableDatabase();

        //
        int maxid = 0;
        String sql = "SELECT id FROM account;";

        Cursor curseur = bd.rawQuery(sql, null );

        maxid = curseur.getCount();

        return maxid;
    }
}
