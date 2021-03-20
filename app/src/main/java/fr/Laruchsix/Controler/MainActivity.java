package fr.Laruchsix.Controler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.Laruchsix.Model.FonctionsAux;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.PersonDatas;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_FIRST_NAME = "fr.Laruchsix.application.name.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "fr.Laruchsix.application.name.EXTRA_LAST_NAME";
    public static final String EXTRA_ID = "fr.Laruchsix.application.Account.id.EXTRA_ID";
    //public static final String EXTRA_DEVISE = "fr.Laruchsix.application.devise.EXTRA_DEVISE";

    public Context act = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on recupère les deux texts
        EditText edPrenom  = findViewById(R.id.edPrenom);
        EditText edNom = findViewById(R.id.edNom);

        // but new
        Button newuser = findViewById(R.id.butnew);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), formulairePremiereConnexion.class);
                startActivity(otherActivity);
            }
        });

        // button load
        Button loaduser= findViewById(R.id.butload);
        loaduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonDatas datasUser = new PersonDatas(act);
                Person currentUser = datasUser.findUser(edPrenom.getText().toString(), edNom.getText().toString());
                if (currentUser != null){
                    Intent otherActivity = new Intent(act, AccountsSelect.class);
                    otherActivity.putExtra(EXTRA_FIRST_NAME, currentUser.getFirstName());
                    otherActivity.putExtra(EXTRA_LAST_NAME, currentUser.getLastName());
                    startActivity(otherActivity);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), R.string.toastConnexion, Toast.LENGTH_SHORT).show();
            }
        });
    }

/*
    private void loadUser(String lastName, String firstName)
    {
        String users = FonctionsAux.readUser(this);
        String[] usersTab = users.split("\n");

        boolean ret = false;
        for(String currentUser : usersTab)
        {
            String[] user = currentUser.split("--");
            if(user[0].equals(lastName) && user[1].equals(firstName))
            {
                ret = true;
                break;
            }
        }

        if(ret)
        {

        }
        else
        {

        }
    }*/


}