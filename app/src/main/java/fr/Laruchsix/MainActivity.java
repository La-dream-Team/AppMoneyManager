package fr.Laruchsix;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_FIRST_NAME = "fr.Laruchsix.application.name.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "fr.Laruchsix.application.name.EXTRA_LAST_NAME";

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
                finish();
            }
        });

        TextView data = findViewById(R.id.readData);
        Person myp = FonctionsAux.loadUser("Tibo", "Ru", this);
        data.setText(FonctionsAux.readUser(this) + myp.toString());
        // + FonctionsAux.loadUser("Tibo", "Ru", this).toString()

        // button load
        Button loaduser= findViewById(R.id.butload);
        loaduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUser(edNom.getText().toString(), edPrenom.getText().toString());
            }
        });
    }


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
            Intent otherActivity = new Intent(this, AccountsSelect.class);
            otherActivity.putExtra(EXTRA_FIRST_NAME, firstName);
            otherActivity.putExtra(EXTRA_LAST_NAME, lastName);
            startActivity(otherActivity);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.toastConnexion, Toast.LENGTH_SHORT).show();
        }
    }


}