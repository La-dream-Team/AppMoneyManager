package fr.Laruchsix.Controler;

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

import java.io.FileOutputStream;
import java.text.DecimalFormat;

import fr.Laruchsix.Controler.AccountAdapter;
import fr.Laruchsix.Controler.MainActivity;
import fr.Laruchsix.Controler.formulaireCreationDeCompte;
import fr.Laruchsix.Model.CurrencyTranslation;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.FonctionsAux;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;

public class AccountsSelect extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Person owner;
    private TextView totalBalanceView;
    private String firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_select);

        Intent intent = getIntent();
        firstName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);

        Toast.makeText(getApplicationContext(), "Bonjour " + firstName + " " + lastName , Toast.LENGTH_LONG).show();

        // on charge le fichier
        //owner = FonctionsAux.loadUser(firstName, lastName, this);
        //owner.addNewAccount(50.0f, "Toto", "Les blagues a toto", Devise.Yen);
        // pour test on l'affiche
        TextView txt = findViewById(R.id.TitreAccSelect);
        txt.setText(owner.toString());


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

        /*
        //Propietaire crée pour tester
        this.owner = new Person( Devise.Dolar_American, "Remy", "Debacque");

        //Liste de comptes
        this.owner.addNewAccount(10000.86f, "Main", "Mon compte principal", Devise.Euro);
        this.owner.addNewAccount(520.23f, "Francais", "Mon compte francais", Devise.Dolar_American);
        this.owner.addNewAccount(3065.57f, "Espagnol", "Mon compte espagnol", Devise.Yen);
        */

        //Si l'utilisateur n'a pas encore des comptes on change le textView
        if(this.owner.getAccounts().isEmpty())
        {
            TextView s = findViewById(R.id.TitreAccSelect);
            s.setText("Vous n'avez pas des comptes");
        }

        //On ajoute l'adapter à la liste de comptes
        ListView accountListView = findViewById(R.id.account_list);
        accountListView.setAdapter(new AccountAdapter(this, this.owner.getAccounts()));

        //On initialise le compteur
        this.totalBalanceView = (TextView)findViewById(R.id.total_balance_number);
        changeTotalBalanceViewText(this.owner.getGlobalBalance());

        //On recupere la devise de reference choisi par le propietaire de ces comptes
        int currencyIndex = getIndexFromCurrency(this.owner.getDevise());

        //On recupere le spinner et on l'associe à la string array que doit traiter
        Spinner currencySpinner = findViewById(R.id.currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
        currencySpinner.setSelection(currencyIndex);
        currencySpinner.setOnItemSelectedListener(this);

        FonctionsAux.savePerson(firstName, lastName, this, owner);
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

    private void changeTotalBalanceViewText(float balance)
    {
        DecimalFormat totalBalanceFormat = new DecimalFormat("#.##");
        this.totalBalanceView.setText(String.valueOf(totalBalanceFormat.format(balance)));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0:
                changeTotalBalanceViewText(this.owner.getGlobalBalance()* CurrencyTranslation.coefDev(this.owner.getDevise(), Devise.Euro));
                break;
            case 1:
                changeTotalBalanceViewText(this.owner.getGlobalBalance()*CurrencyTranslation.coefDev(this.owner.getDevise(), Devise.Livre_Sterling));
                break;
            case 2:
                changeTotalBalanceViewText(this.owner.getGlobalBalance()*CurrencyTranslation.coefDev(this.owner.getDevise(), Devise.Yen));
                break;
            case 3:
                changeTotalBalanceViewText(this.owner.getGlobalBalance()*CurrencyTranslation.coefDev(this.owner.getDevise(), Devise.Dolar_American));
                break;
            case 4:
                changeTotalBalanceViewText(this.owner.getGlobalBalance()*CurrencyTranslation.coefDev(this.owner.getDevise(), Devise.Rouble));
                break;
            default:
                changeTotalBalanceViewText(-1.0f);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Ce methode transforme la devise global de l'utilisateur en l'index du tableau ou la devise se trouve sous forme de string
    public int getIndexFromCurrency (Devise currency)
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