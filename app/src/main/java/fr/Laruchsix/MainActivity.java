package fr.Laruchsix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.time.Instant;

public class MainActivity extends AppCompatActivity {
    private Popup popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

        final Button formAcc = (Button) findViewById(R.id.newAccount);

        formAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), formulairePremiereConnexion.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }
}