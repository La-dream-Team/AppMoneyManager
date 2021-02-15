package fr.Laruchsix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class AccountsSelect extends AppCompatActivity {

    private int totalBalance = 0;
    private TextView totalBalanceView;
    private List<Account> accountsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        addNewAccount(10000, "Main", "Mon compte principal", Devise.Euro);
        addNewAccount(520, "Francais", "Mon compte francais", Devise.Dolar_American);
        addNewAccount(3065, "Espagnol", "Mon compte espagnol", Devise.Yen);
        addNewAccount(10000, "Main", "Mon compte principal", Devise.Euro);
        addNewAccount(520, "Francais", "Mon compte francais", Devise.Dolar_American);
        addNewAccount(3065, "Espagnol", "Mon compte espagnol", Devise.Yen);
        addNewAccount(10000, "Main", "Mon compte principal", Devise.Euro);
        addNewAccount(520, "Francais", "Mon compte francais", Devise.Dolar_American);
        addNewAccount(3065, "Espagnol", "Mon compte espagnol", Devise.Yen);
        addNewAccount(10000, "Main", "Mon compte principal", Devise.Euro);
        addNewAccount(520, "Francais", "Mon compte francais", Devise.Dolar_American);
        addNewAccount(3065, "Espagnol", "Mon compte espagnol", Devise.Yen);

        ListView accountListView = findViewById(R.id.account_list);
        accountListView.setAdapter(new AccountAdapter(this, accountsList));

        this.totalBalanceView = (TextView)findViewById(R.id.total_balance);
        changeTotalBalanceViewText();
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

    private String readDataAccount() {
        String fileName = getResources().getString(R.string.dataAccount);
        StringBuilder sb;
        try {
            // Open stream to read file.
            FileInputStream in = this.openFileInput(fileName);

            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            sb= new StringBuilder();
            String s= null;
            while((s= br.readLine())!= null)  {
                sb.append(s).append("\n");
            }
        } catch (Exception e) {
            //Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
            return "";
        }
        return sb.toString();
    }

    private int getTotalBalance()
    {
        return this.totalBalance;
    }

    private void changeTotalBalanceViewText()
    {
        this.totalBalanceView.setText("Balance total de vos comptes :" + getTotalBalance());
    }

    private void addNewAccount(int balance, String name, String description, Devise devise)
    {
        Date currentTime = Calendar.getInstance().getTime();
        this.accountsList.add(new Account(balance, name, description, currentTime, devise));
        this.totalBalance += balance;
    }
}