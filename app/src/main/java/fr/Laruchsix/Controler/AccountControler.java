package fr.Laruchsix.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.FonctionsAux;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.AccountDatas;
import fr.Laruchsix.SQLite.PersonDatas;

public class AccountControler extends AppCompatActivity{
    private Account account;
    private int accountId;
    private Person owner;
    private String firstName, lastName;
    private Integer id;
    private Devise devise;
    private AccountDatas accountDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.loadData();

        // button Add Activité
        Button addAct = findViewById(R.id.ajoutActivite);
        addAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start new actvity
                Intent otherActivity = new Intent(getApplicationContext(), formulaireCreationDeActivite.class);
                otherActivity.putExtra(MainActivity.EXTRA_ID, accountId);
                otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                startActivity(otherActivity);
                finish();
            }
        });

        // button Supp Activité
        Button suppAct = findViewById(R.id.suppActivite);
        suppAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start new actvity
                Intent otherActivity = new Intent(getApplicationContext(), ActivityDelete.class);
                otherActivity.putExtra(MainActivity.EXTRA_ID, accountId);
                otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                startActivity(otherActivity);
                finish();
            }
        });

        //Si l'utilisateur n'a pas encore des activites on change le textView
        if(this.account.getActivities().isEmpty())
        {
            TextView s = findViewById(R.id.TitreActivityList);
            s.setText("Vous n'avez pas d'activités relies à votre compte");
        }

        //On met le nom du compte comme titre de la view
        TextView accountName = (TextView)findViewById(R.id.TitreAccount);
        accountName.setText(this.account.getName());

        //On ajoute l'adapter à la liste de comptes
        ListView activityListView = findViewById(R.id.activity_list);
        ActivityAdapter adapter = new ActivityAdapter(this, this.account.getActivities()){
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        activityListView.setAdapter(adapter);

        //On met à jour le balance total de la view
        TextView totalBalanceView = (TextView)findViewById(R.id.total_balance_number);
        DecimalFormat totalBalanceFormat = new DecimalFormat("#.##");
        totalBalanceView.setText(String.valueOf(totalBalanceFormat.format(this.account.getCurrentBalance())));

        TextView accountCurrency = (TextView)findViewById(R.id.account_currency);
        accountCurrency.setText(getStringFromCurrency(this.account.getDevise()));

        FonctionsAux.savePerson(firstName, lastName, this, owner);
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

    private void loadData(){
        Intent intent = getIntent();
        accountId = intent.getIntExtra(MainActivity.EXTRA_ID, -1);
        firstName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);

        //création du propriÃ©taire
        PersonDatas personDatas = new PersonDatas(this);
        this.owner = personDatas.findUser(firstName, lastName);

        // on récupère toutes ces activités
        this.accountDatas = new AccountDatas(this);
        accountDatas.loadAcc(this.owner);

        // on récupère la compte choisi
        this.account = this.owner.findAccountById(accountId);
    }

    public void btnMonthYear(View view) {
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(this,
                new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear)
                    {
                        //Écrire la requête sql qui prend juste toutes les activités faites dans le mois et l'année choisies et les stocke dans la compte actuelle
                    }
                }
                , today.get(Calendar.YEAR), today.get(Calendar.MONTH));
        builder.setActivatedMonth(Calendar.JULY)
                .setMinYear(1990)
                .setActivatedMonth(today.get(Calendar.MONTH))
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2040)
                .setTitle("Choisir : Mois | Année")
                .build().show();
    }
}
