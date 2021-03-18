package fr.Laruchsix.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.AccountDatas;
import fr.Laruchsix.SQLite.PersonDatas;

public class AccountDelete extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Person owner;
    private String firstName, lastName;
    private AccountDatas accountDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accounts_delete);
        this.loadDatas();

        //On recupere le spinner et on l'associe à la string array que doit traiter
        Spinner accountSpinner = findViewById(R.id.account_spinner);

        //On recupere les noms des comptes
        String[] accountNames = this.owner.getAccountNames();
        List<String> accountNamesList = new ArrayList<>(Arrays.asList(accountNames));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountNamesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);
        accountSpinner.setSelection(0);
        accountSpinner.setOnItemSelectedListener(this);

        // button cancel
        final Button butCancel = (Button) findViewById(R.id.butSuppAccCancel);
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


        // Button ok
        final Button butOk = (Button) findViewById(R.id.butSuppAccOk);
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int possSelect = accountSpinner.getSelectedItemPosition();

                if(possSelect == 0){
                    for(Account currentAcc : owner.getAccounts())
                        accountDatas.removeAccount(owner, currentAcc);
                }
                else{
                    Account remAcc = owner.getAccounts().get(possSelect -1);
                    accountDatas.removeAccount(owner, remAcc);
                }

                Intent otherActivity = new Intent(getApplicationContext(), AccountsSelect.class);
                otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                startActivity(otherActivity);
                finish();
            }
        });
    }


    // charge toute la base de donnée
    private void loadDatas(){
        Intent intent = getIntent();
        firstName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);

        //création du propriétaire
        PersonDatas personDatas = new PersonDatas(this);
        this.owner = personDatas.findUser(firstName, lastName);

        // on récupère toutes ces activités
        accountDatas = new AccountDatas(this);
        accountDatas.loadAcc(this.owner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
