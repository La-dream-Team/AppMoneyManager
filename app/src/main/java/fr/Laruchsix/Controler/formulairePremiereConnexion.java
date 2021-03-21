package fr.Laruchsix.Controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.FonctionsAux;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.PersonDatas;

public class formulairePremiereConnexion extends AppCompatActivity {
    private AppCompatActivity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_premiere_connexion);

        this.act = this;

        // button cancel
        final Button butCancel = (Button) findViewById(R.id.butAccCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

        // button Ok
        final Button butOk = (Button) findViewById(R.id.butAccOk);
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName, lastName;
                Devise dev;

                EditText tfname = findViewById(R.id.edAccName);
                EditText tlname = findViewById(R.id.edAccName2);

                // recupération du contenu des Edits texts
                firstName = tfname.getText().toString();
                lastName = tlname.getText().toString();

                dev = getDevise();

                if((dev == null) || (firstName.equals("")) || (lastName.equals("")))
                {
                    Toast.makeText(getApplicationContext(), R.string.toastFormulaire, Toast.LENGTH_LONG).show();
                }
                else
                    {
                        //System.out.println("bonjour " + firstName + " "  + lastName + " qui a pour devise " + dev.toString());

                        // paramètre pour gere la base de donnée
                        PersonDatas dataBase = new PersonDatas(act);

                        Person ispresent = dataBase.findUser(firstName, lastName);

                        if(ispresent == null){
                            Person.setMaxid(dataBase.getMaxiD());
                            Person newPerson = new Person(dev, firstName, lastName);

                            dataBase.ajout(newPerson);

                            Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(otherActivity);
                            finish();
                        }
                        else
                        {
                            System.out.println("ce qui est présent dans la base = " + ispresent.toString());
                            Toast.makeText(getApplicationContext(), R.string.toastFormulaire, Toast.LENGTH_LONG).show();
                        }


                        //FonctionsAux.saveUser(lastName, firstName, act);
                        //FonctionsAux.createUserFile(firstName, lastName, dev, act);

                }
            }
        });

    }

    private Devise getDevise()
    {
        // Le RadioGroup
        RadioGroup gp;
        // le bouton select
        int select ;

        // recup du groupe
        gp = findViewById(R.id.butgpUser);
        try{
            select = findViewById(gp.getCheckedRadioButtonId()).getId();
        } catch (Exception e){
            return null;
        }

        // on recupère les ids de tous les boutons
        int butEuroPer = findViewById(R.id.butEuroPer).getId();
        int butDolarPer = findViewById(R.id.butDolarPer).getId();
        int butLivrePer = findViewById(R.id.butLivrePer).getId();
        int butYenPer = findViewById(R.id.butYenPer).getId();
        int butRouble = findViewById(R.id.butRouble).getId();

        if (select == butEuroPer)
            return Devise.Euro;
        else
        {
            if  (select == butDolarPer)
                return Devise.Dolar_American;
            else
                {
                if (select == butLivrePer)
                    return Devise.Livre_Sterling;
                else
                {
                    if(select == butYenPer)
                        return Devise.Yen;
                    else
                    {
                        if(select == butRouble)
                            return Devise.Rouble;
                        else
                            return null;
                    }
                }
            }
        }
    }
}