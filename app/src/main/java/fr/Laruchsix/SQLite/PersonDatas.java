package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;

// accède et traitement de la table personne
public class PersonDatas {
    private String nomBase = "bdPerson.sqlite";
    private Integer versionBase = 1;
    private MySqlLitePerson accesBD;
    private SQLiteDatabase bd;

    // constructeur
    public PersonDatas(Context context) {
        accesBD = new MySqlLitePerson(context, nomBase, null, versionBase);
    }

    public void ajout(Person person){
        bd = accesBD.getWritableDatabase();
        String sql = "INSERT INTO person (id, nom, prenom, devise) VALUES (\""
                + person.getId() + "\" ,\"" + person.getLastName() + "\", \"" + person.getFirstName() + "\", \"" + person.getDevise().toString() + "\");";

        System.out.println("requete sql =" + sql);

        bd.execSQL(sql);
    }

    // recherche un utilisateur dans la table Person
    // Renvoir un objet Person
    public Person findUser(String firstName, String lastName){
        bd = accesBD.getReadableDatabase();
        Person ret = null;
        // requete sql
        String sql = "SELECT * FROM person WHERE nom = \"" + lastName + "\" AND prenom = \"" + firstName + "\";";

        System.out.println("requete sql =" + sql);

        Cursor curseur = bd.rawQuery(sql, null );

        System.out.println("voici le curseur " + curseur.getColumnNames().length);

        //curseur.moveToFirst();
        // on compte le nombre de colomns
        if(curseur.moveToFirst()){
            int id = curseur.getInt(0);
            Devise devise = Devise.valueOf(curseur.getString(3));

            ret = new Person(devise, firstName, lastName, id);
        }

        // on retourne l'utilisateur
        return ret;
    }

    public int getMaxiD(){
        bd = accesBD.getReadableDatabase();

        //
        int maxid = 0;
        String sql = "SELECT id FROM person;";

        Cursor curseur = bd.rawQuery(sql, null );

        maxid = curseur.getCount();

        return maxid;
    }
}
