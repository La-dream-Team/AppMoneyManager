package fr.Laruchsix.Controler;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Activity;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.FonctionsAux;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.AccountDatas;
import fr.Laruchsix.SQLite.PersonDatas;

public class AccountControler extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Account account;
    private int accountId;
    private Person owner;
    private String firstName, lastName;
    private Integer id;
    private Devise devise;
    private AccountDatas accountDatas;
    private ListView activityListView;
    private ScrollView activityScrollView;
    private ActivityAdapter adapter;
    private Context context = this;

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

        // button retour
        Button butretour = findViewById(R.id.butActSelectReturn);
        butretour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //start new actvity
                Intent otherActivity = new Intent(getApplicationContext(), AccountsSelect.class);
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
            s.setText(R.string.noActivities);
        }

        //On met le nom du compte comme titre de la view
        TextView accountName = (TextView)findViewById(R.id.TitreAccount);
        accountName.setText(this.account.getName());

        //On ajoute l'adapter à la liste de comptes
        activityListView = findViewById(R.id.activity_list);
  /*      for(Activity current : this.account.getActivities()){
            System.out.println(current.toString());
        }
*/
        ArrayList<Activity> listAct = this.account.getActivitiesDate(null, Calendar.getInstance().getTime());
        /*System.out.println("liste des activités");
        for(Activity current : listAct){
            System.out.println(current.toString());
        }*/
        adapter = new ActivityAdapter(this, listAct, account){
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        activityListView.setAdapter(adapter);

        //On récupére la scroll view
        //activityScrollView = findViewById(R.id.activitiesListScrollView);

        //On applique la méthode de smartScroll
        //FonctionsAux.smartScroll(activityScrollView,  activityListView);

        //On met à jour le balance total de la view
        TextView totalBalanceView = (TextView)findViewById(R.id.total_balance_number);
        DecimalFormat totalBalanceFormat = new DecimalFormat("#.##");
        totalBalanceView.setText(String.valueOf(totalBalanceFormat.format(this.account.getCurrentBalance())));

        TextView accountCurrency = (TextView)findViewById(R.id.account_currency);
        accountCurrency.setText(getStringFromCurrency(this.account.getDevise()));

        //On recupere le spinner des mois
        Spinner moisSpinner = findViewById(R.id.mois_spinner);

        //On établit les mois dans le spinner
        ArrayAdapter<CharSequence> adapterMois = ArrayAdapter.createFromResource(this, R.array.mois, android.R.layout.simple_spinner_item);
        adapterMois.setDropDownViewResource(R.layout.periodicity_spinner_text_view);
        moisSpinner.setAdapter(adapterMois);
        moisSpinner.setSelection(0);
        moisSpinner.setOnItemSelectedListener(this);

        //On recupere le spinner des années
        Spinner anneeSpinner = findViewById(R.id.annee_spinner);

        //On établit les années dans le spinner
        ArrayAdapter<CharSequence> adapterAnnee = ArrayAdapter.createFromResource(this, R.array.annee, android.R.layout.simple_spinner_item);
        adapterAnnee.setDropDownViewResource(R.layout.periodicity_spinner_text_view);
        anneeSpinner.setAdapter(adapterAnnee);
        anneeSpinner.setSelection(0);
        anneeSpinner.setOnItemSelectedListener(this);

        // button apply
        Button apply = findViewById(R.id.filtrerActivites);
        apply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Spinner moisSpinner = findViewById(R.id.mois_spinner);
                int nbMois = moisSpinner.getSelectedItemPosition();
                Month mois = null;
                if(moisSpinner.getSelectedItemPosition() != 0)
                    mois = Month.of(nbMois);

                Spinner anneeSpinner = findViewById(R.id.annee_spinner);
                int nbYear = anneeSpinner.getSelectedItemPosition();
                Year year = null;
                if(anneeSpinner.getSelectedItemPosition() != 0)
                    year = Year.of(nbYear + 1989);

                ArrayList<Activity> list = account.computeBalanceFromDate(mois, year);

        /*for(Activity currentAct : list)
        {
            System.out.println(currentAct.toString());
        }
*/

                adapter = new ActivityAdapter(context, list, account){
                    @Override
                    public boolean isEnabled(int position) {
                        return false;
                    }
                };
                activityListView.setAdapter(adapter);

                //On met à jour le balance total de la view
                TextView totalBalanceView = (TextView)findViewById(R.id.total_balance_number);
                DecimalFormat totalBalanceFormat = new DecimalFormat("#.##");
                totalBalanceView.setText(String.valueOf(totalBalanceFormat.format(account.getCurrentBalance())));
            }
        });

    }


    //Ce methode transforme la devise global de l'utilisateur en l'index du tableau ou la devise se trouve sous forme de string
    public String getStringFromCurrency (Devise currency)
    {
        switch (currency) {
            case Euro:
                return getString(R.string.devSymEuro);
            case Livre_Sterling:
                return getString(R.string.devSymLivreSterling);
            case Yen:
                return getString(R.string.devSymYen);
            case Dolar_American:
                return getString(R.string.devSymDollarAmericain);
            case Rouble:
                return getString(R.string.devSymRouble);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
