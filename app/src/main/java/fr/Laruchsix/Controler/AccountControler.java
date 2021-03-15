package fr.Laruchsix.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;

public class AccountControler extends AppCompatActivity{
    private Account account;
    //private TextView totalBalanceView;
    //private String firstName, lastName;
    //private Integer id;
    //private Devise devise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Intent intent = getIntent();
        //firstName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        //lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);
        //id = intent.getIntExtra(MainActivity.EXTRA_ID, -1);
        //devise = Devise.valueOf(intent.getStringExtra(MainActivity.EXTRA_DEVISE));

        // bouton reset
        Button reset = findViewById(R.id.resetuser);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_User();

                // on relance la page par default
                Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

        // button Add Acc
        Button addAcc = findViewById(R.id.ajoutActivite);
        addAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start new actvity
                Intent otherActivity = new Intent(getApplicationContext(), formulaireCreationDeActivite.class);
                startActivity(otherActivity);
                finish();
            }
        });


        Person owner = new Person(Devise.Euro, "Daniel", "Rodriguez", 000);

        //Compte crée pour tester
        Date currentTime = Calendar.getInstance().getTime();
        this.account = new Account(10000.86f, "Main", "Mon compte principal", currentTime, owner, Devise.Euro);

        //Liste d'activités
        this.account.addActivity(-5500.36f, "Achat de la Ferrai FX50", "Achat de nouvelle voiture", currentTime,-1);
        this.account.addActivity(-18.36f, "Burger King 15/03/2021", "Restaurant avec les potes", currentTime,-1);
        this.account.addActivity(-300.36f, "Achat de Samsung FE 2020", "Achat de portable", currentTime,-1);


        //Si l'utilisateur n'a pas encore des activites on change le textView
        if(this.account.getActivities().isEmpty())
        {
            TextView s = findViewById(R.id.TitreActivityList);
            s.setText("Vous n'avez pas d'activités relies à votre compte");
        }

        //On ajoute l'adapter à la liste de comptes
        ListView activityListView = findViewById(R.id.activity_list);
        activityListView.setAdapter(new ActivityAdapter(this, this.account.getActivities()));

        //On met à jour le balance total de la view
        TextView totalBalanceView = (TextView)findViewById(R.id.total_balance_number);
        DecimalFormat totalBalanceFormat = new DecimalFormat("#.##");
        totalBalanceView.setText(String.valueOf(totalBalanceFormat.format(this.account.getCurrentBalance())));

        TextView accountCurrency = (TextView)findViewById(R.id.account_currency);
        accountCurrency.setText(getStringFromCurrency(this.account.getDevise()));

        //FonctionsAux.savePerson(firstName, lastName, this, owner);
    }

    private void reset_User()
    {
        // on créé la liste la chaîne de caractère de données
        String data = "";

        String fileName = getResources().getString(R.string.dateUser) ;

        try {
            // Open Stream to write file.
            FileOutputStream out = this.openFileOutput(fileName, MODE_PRIVATE);

            out.write(data.getBytes());
            out.close();
            Toast.makeText(this,"File saved!",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //Ce methode transforme la devise global de l'utilisateur en l'index du tableau ou la devise se trouve sous forme de string
    public String getStringFromCurrency (Devise currency)
    {
        switch (currency) {
            case Euro:
                return "Euro (€)";
            case Livre_Sterling:
                return "Pound sterling (£)";
            case Yen:
                return "Yen (¥)";
            case Dolar_American:
                return "American dollar ($)";
            case Rouble:
                return "Ruble (₽)";
            default:
                return "";
        }
    }
}
