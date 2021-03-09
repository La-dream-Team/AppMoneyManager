package fr.Laruchsix.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import fr.Laruchsix.Model.Person;

// accède et traitement de la table personne
public class PersonDatas {
    private String nomBase = "bdPerson.sqlite";
    private Integer versionBase = 1;
    private MySqlLite accesBD;
    private SQLiteDatabase bd;

    // constructeur
    public PersonDatas(Context context) {
        accesBD = new MySqlLite(context, nomBase, null, versionBase);
    }


    public void ajout(Person person){
        bd = accesBD.getWritableDatabase();
        String sql = "INSERT INTO person (id, nom, prenom) VALUES (\" "
                + person.getId() + " \" ,\"" + person.getFirstName() + "\", \"" + person.getLastName() + "\");";
        bd.execSQL(sql);
    }

    // recherche un utilisateur dans la table Person
    // Renvoir un objet Person
    public Person findUser(String firstName, String lastName){
        Person ret = null;
        // requete sql
        String sql = "SELECT * FROM person WHERE nom = \"" + lastName + "\" AND prenom = \"" + firstName + "\";";




        // on retourne l'utilisateur
        return ret;
    }
}
