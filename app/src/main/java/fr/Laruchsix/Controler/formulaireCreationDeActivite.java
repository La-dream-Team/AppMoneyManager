package fr.Laruchsix.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Activity;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.AccountDatas;
import fr.Laruchsix.SQLite.ActivityDatas;
import fr.Laruchsix.SQLite.PersonDatas;

public class formulaireCreationDeActivite extends AppCompatActivity {
    private Account account;
    private int accountId;
    private Person owner;
    private String firstName, lastName;
    private Integer id;
    private Devise devise;
    private AccountDatas accountDatas;
    private ActivityDatas activityDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_creation_d_une_activite);

        // chargement des donnés
        loadDatas();

        // button cancel
        final Button butCancel = (Button) findViewById(R.id.butActivityCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), AccountControler.class);
                otherActivity.putExtra(MainActivity.EXTRA_ID, accountId);
                otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                startActivity(otherActivity);
                finish();
            }
        });

        TextView ednom = findViewById(R.id.ednomActivite);
        TextView eddesc = findViewById(R.id.eddescriptionActivite);
        TextView edvalue = findViewById(R.id.edValueActivite);

        // button Ok
        final Button butOk = (Button) findViewById(R.id.butCompteOk);
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom, desc;
                Float value;
                if(edvalue.getText().toString() != null)
                    value = Float.parseFloat(edvalue.getText().toString());
                else
                    value = null;
                nom = ednom.getText().toString();
                desc = eddesc.getText().toString();

                //System.out.println("nom = " + nom + " desc = " + desc + " value = " + value + " devise = " + dev.toString());
                if((nom != "") && (desc != "") && (value != null))
                {
                    Activity newAct = account.addActivity(value, desc, nom, null, -1);
                    activityDatas.ajout(account, owner, newAct);

                    Intent otherActivity = new Intent(getApplicationContext(), AccountControler.class);
                    otherActivity.putExtra(MainActivity.EXTRA_ID, accountId);
                    otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                    otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                    startActivity(otherActivity);
                    finish();
                }


            }
        });
    }

    private void loadDatas (){
        Intent intent = getIntent();
        accountId = intent.getIntExtra(MainActivity.EXTRA_ID, -1);
        firstName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);

        //création du propriétaire
        PersonDatas personDatas = new PersonDatas(this);
        this.owner = personDatas.findUser(firstName, lastName);

        // on récupère toutes ces activités
        this.accountDatas = new AccountDatas(this);
        accountDatas.loadAcc(this.owner);

        // on récupère la compte choisi
        this.account = this.owner.findAccountById(accountId);

        //
        this.activityDatas = new ActivityDatas(this);
    }
}
