package fr.Laruchsix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class formulairePremiereConnexion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_premiere_connexion);


        // button cancel
        final Button butCancel = (Button) findViewById(R.id.butAccCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(otherActivity);
                finish();
            }
        });

        // button Ok
        final Button butOk = (Button) findViewById(R.id.butAccOk);
        butOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName, lastName;


                //Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(otherActivity);
                //finish();
            }
        });



    }

    private void save_Acc(String fist, String last, Devise C)
    {

    }
}