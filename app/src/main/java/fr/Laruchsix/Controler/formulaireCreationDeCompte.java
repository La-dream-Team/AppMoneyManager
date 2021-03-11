package fr.Laruchsix.Controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.AccountDatas;
import fr.Laruchsix.SQLite.PersonDatas;

public class formulaireCreationDeCompte extends AppCompatActivity {

    private Person owner;
    private String firstName, lastName;
    private Integer id;
    private Devise devise;
    private AccountDatas accountDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_creation_de_compte);

        // chargement des donnés
        loadDatas();

        // button cancel
        final Button butCancel = (Button) findViewById(R.id.butCompteCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), AccountsSelect.class);
                otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                startActivity(otherActivity);
                finish();
            }
        });

        TextView ednom = findViewById(R.id.ednomCompte);
        TextView eddesc = findViewById(R.id.eddescriptionCompte);
        TextView edvalue = findViewById(R.id.edbalanceParDefaut);

        // button Ok
        final Button butOk = (Button) findViewById(R.id.butCompteOk);
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Devise dev = getDevise();

                String nom, desc;
                Float value;
                if(edvalue.getText().toString() != null)
                    value = Float.parseFloat(edvalue.getText().toString());
                else
                    value = null;
                nom = ednom.getText().toString();
                desc = eddesc.getText().toString();

                System.out.println("nom = " + nom + " desc = " + desc + " value = " + value + " devise = " + dev.toString());
                if((dev != null) && (nom != "") && (desc != "") && (value != null))
                {
                    Account newAcc = owner.addNewAccount(value, nom, desc, dev, -1);
                    accountDatas.ajout(newAcc, owner);

                    Intent otherActivity = new Intent(getApplicationContext(), AccountsSelect.class);
                    otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                    otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                    startActivity(otherActivity);
                    finish();
                }


            }
        });
    }

    private void loadDatas (){
        // on charge les données
        Intent intent = getIntent();
        firstName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);

        //création du propriétaire
        PersonDatas personDatas = new PersonDatas(this);
        this.owner = personDatas.findUser(firstName, lastName);

        System.out.println("voici le propriétaire " + owner.toString());

        // on récupère toutes ces activités
        accountDatas = new AccountDatas(this);
        accountDatas.loadAcc(this.owner);
    }

    private Devise getDevise() {
        // Le RadioGroup
        RadioGroup gp;
        // le bouton select
        int select;

        // recup du groupe
        gp = findViewById(R.id.butgpAcc);
        try {
            select = findViewById(gp.getCheckedRadioButtonId()).getId();
        } catch (Exception e) {
            return null;
        }

        // on recupère les ids de tous les boutons
        int butEuroPer = findViewById(R.id.butEuroPerAcc).getId();
        int butDolarPer = findViewById(R.id.butDolarPerAcc).getId();
        int butLivrePer = findViewById(R.id.butLivrePerAcc).getId();
        int butYenPer = findViewById(R.id.butYenPerAcc).getId();
        int butRouble = findViewById(R.id.butRoubleAcc).getId();

        if (select == butEuroPer)
            return Devise.Euro;
        else {
            if (select == butDolarPer)
                return Devise.Dolar_American;
            else {
                if (select == butLivrePer)
                    return Devise.Livre_Sterling;
                else {
                    if (select == butYenPer)
                        return Devise.Yen;
                    else {
                        if (select == butRouble)
                            return Devise.Rouble;
                        else
                            return null;
                    }
                }
            }
        }
    }
}