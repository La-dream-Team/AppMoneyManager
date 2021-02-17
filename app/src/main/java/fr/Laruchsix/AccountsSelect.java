package fr.Laruchsix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AccountsSelect extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private float totalBalance = 0.0f;
    private TextView totalBalanceView;
    private List<Account> accountsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String fistName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        String lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);

        Toast.makeText(getApplicationContext(), "Bonjour " + fistName + " " + lastName , Toast.LENGTH_LONG).show();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_select);

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
        Button addAcc = findViewById(R.id.ajoutAcc);
        addAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start new actvity
                Intent otherActivity = new Intent(getApplicationContext(), formulaireCreationDeCompte.class);
                startActivity(otherActivity);
                finish();
            }
        });

        //Propietaire crée pour tester
        Person owner = new Person( Devise.Euro, "Remy", "Debacque");

        //Liste de comptes
        addNewAccount(10000.86f, "Main", "Mon compte principal", owner, Devise.Euro);
        addNewAccount(520.23f, "Francais", "Mon compte francais", owner, Devise.Dolar_American);
        addNewAccount(3065.57f, "Espagnol", "Mon compte espagnol", owner, Devise.Yen);

        //On ajoute l'adapter à la liste de comptes
        ListView accountListView = findViewById(R.id.account_list);
        accountListView.setAdapter(new AccountAdapter(this, accountsList));

        //On initialise le compteur
        this.totalBalanceView = (TextView)findViewById(R.id.total_balance_number);
        changeTotalBalanceViewText();

        //On recupere la devise de reference choisi par le propietaire de ces comptes
        int currencyIndex = getCurrencyStringIndex(owner.getDevise());

        //On recupere le spinner et on l'associe à la string array que doit traiter
        Spinner currencySpinner = findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setSelection(currencyIndex);
        currencySpinner.setOnItemSelectedListener(this);
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



    private float getTotalBalance()
    {
        return this.totalBalance;
    }

    private void changeTotalBalanceViewText()
    {
        this.totalBalanceView.setText(String.valueOf(getTotalBalance()));
    }

    private void addNewAccount(float balance, String name, String description, Person owner,  Devise devise)
    {
        Date currentTime = Calendar.getInstance().getTime();
        this.accountsList.add(new Account(balance, name, description, currentTime, owner, devise));
        this.totalBalance += balance;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getCurrencyStringIndex (Devise currency)
    {
        String[] tab = getResources().getStringArray(R.array.currency);
        switch (currency) {
            case Euro:
                return 0;
            case Livre_Sterling:
                return 1;
            case Yen:
                return 2;
            case Dolar_American:
                return 3;
            case Rouble:
                return 4;
            default:
                return -1;
        }
    }

}