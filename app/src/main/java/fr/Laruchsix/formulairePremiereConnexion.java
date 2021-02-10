package fr.Laruchsix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
                Devise dev;

                EditText tfname = findViewById(R.id.edAccName);
                EditText tlname = findViewById(R.id.edAccName2);

                // recupération du contenu des Edits texts
                firstName = tfname.getText().toString();
                lastName = tlname.getText().toString();

                dev = getDevise();

                if(dev == null)
                {
                    Toast.makeText(getApplicationContext(), R.string.toastFormulaire, Toast.LENGTH_LONG).show();
                }
                else
                    {
                    save_Acc(firstName, lastName, dev);
                    Intent otherActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(otherActivity);
                    finish();
                }
            }
        });



    }

    private Devise getDevise()
    {
        // Le RadioGroup
        RadioGroup gp;
        // le bouton select
        int select ;

        // recup du groupe
        gp = findViewById(R.id.butgpAcc);
        select = findViewById(gp.getCheckedRadioButtonId()).getId();

        // on recupère les ids de tous les boutons
        final int butEuroPer = findViewById(R.id.butEuroPer).getId();
        int butDolarPer = findViewById(R.id.butDolarPer).getId();
        int butLivrePer = findViewById(R.id.butLivrePer).getId();
        int butYenPer = findViewById(R.id.butYenPer).getId();
        int butRouble = findViewById(R.id.butRouble).getId();

        /*switch(select){
            case butEuroPer :
                return Devise.Euro;
                break;
            case butDolarPer :
                return Devise.Dolar_American;
                break;
            case butLivrePer :
                return Devise.Livre_Sterling;
                break;
            case butYenPer :
                return Devise.Yen;
                break;
            case butRouble :
                return Devise.Rouble;
                break;
            default:
                return null;
        }*/


        return null;
    }

    private void save_Acc(String fist, String last, Devise C)
    {

    }
}