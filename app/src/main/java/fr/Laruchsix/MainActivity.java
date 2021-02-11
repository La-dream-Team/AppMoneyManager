package fr.Laruchsix;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder  popup;
    private MainActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.activity = this;

        // autre pop up
        this.popup = new AlertDialog.Builder(this.activity);
        this.popup.setTitle("Bonjour, merci d'avoir téléchargé MoneyMananger");
        this.popup.setMessage("Pour continuer appuyer sur oui afin de remplir un petit formulaire pour facilier la gestion de l'application. \nPour quitter appuyer sur non.");
        this.popup.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent otherActivity = new Intent(getApplicationContext(), formulairePremiereConnexion.class);
                startActivity(otherActivity);
                finish();
            }
        });

        this.popup.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        this.popup.show();


        /*
        // initialisation du popup
        this.popup = new Popup(this);
        this.popup.setTitle("Bonjour, merci d'avoir téléchargé MoneyMananger");
        this.popup.setSubTitle("Pour continuer appuyer sur oui afin de remplir un petit formulaire pour facilier la gestion de l'application. \nPour quitter appuyer sur non.");
        this.popup.setTitleSize(20f);

        // bouton oui
        Button yes = this.popup.getYesButton();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), formulairePremiereConnexion.class);
                startActivity(otherActivity);
                finish();
            }
        });

        // bouton non
        Button no = this.popup.getNoButton();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // on affiche le popup
        this.popup.build();
        */
        final Button formAcc = (Button) findViewById(R.id.newAccount);

        formAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent otherActivity = new Intent(getApplicationContext(), formulairePremiereConnexion.class);
                //startActivity(otherActivity);
                //finish();
                readData();
            }
        });


    }


    private void readData() {
        String fileName = getResources().getString(R.string.dateUser);
        try {
            // Open stream to read file.
            FileInputStream in = this.openFileInput(fileName);

            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            StringBuilder sb= new StringBuilder();
            String s= null;
            while((s= br.readLine())!= null)  {
                sb.append(s).append("\n");
            }
            ((TextView) findViewById(R.id.ret)).setText(sb.toString());

        } catch (Exception e) {
            Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}