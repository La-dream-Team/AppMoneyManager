package fr.Laruchsix.Controler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.Laruchsix.R;

public class formulaireCreationDeActivite extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_creation_d_une_activite);


        // button cancel
        final Button butCancel = (Button) findViewById(R.id.butActivityCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), AccountControler.class);
                startActivity(otherActivity);
                finish();
            }
        });
    }
}
