package fr.Laruchsix.Controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    public Context act = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On recupère les deux texts
        EditText edPrenom  = findViewById(R.id.edPrenom);
        EditText edNom = findViewById(R.id.edNom);

        // On sauvegarde l'état de l'application
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);

        // Quand l'utilisateur ouvre à nouveau l'app après avoir appliqué le theme sombre/normal
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Button dark mode
        Button btnDarkMode = findViewById(R.id.butDarkMode);
        btnDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isDarkModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();
                }
            }
        });

        // Button New User
        Button newuser = findViewById(R.id.butnew);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(),
                        formulairePremiereConnexion.class);
                startActivity(otherActivity);
                finish();
            }
        });


        // Button load
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
}