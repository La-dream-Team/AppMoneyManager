package fr.Laruchsix.Controler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Activity;
import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;
import fr.Laruchsix.R;
import fr.Laruchsix.SQLite.AccountDatas;
import fr.Laruchsix.SQLite.ActivityDatas;
import fr.Laruchsix.SQLite.PersonDatas;

public class formulaireCreationDeActivite extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Account account;
    private int accountId;
    private Person owner;
    private String firstName, lastName;
    private Integer id;
    private Devise devise;
    private AccountDatas accountDatas;
    private ActivityDatas activityDatas;
    private Spinner periodiciteSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_creation_d_une_activite);

        // chargement des donnés
        loadDatas();

        // button cancel
        final Button butCancel = (Button) findViewById(R.id.butActivityCancel);
        butCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherActivity = new Intent(getApplicationContext(), AccountControler.class);
                otherActivity.putExtra(MainActivity.EXTRA_ID, accountId);
                otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                startActivity(otherActivity);
                finish();
            }
        });

        TextView ednom = findViewById(R.id.ednomActivite);
        TextView eddesc = findViewById(R.id.eddescriptionActivite);
        TextView edvalue = findViewById(R.id.edValueActivite);

        // button Ok
        final Button butOk = (Button) findViewById(R.id.butActivityOk);
        butOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String nom, desc;
                Float value;
                if(edvalue.getText().toString() != null)
                    value = Float.parseFloat(edvalue.getText().toString());
                else
                    value = null;
                nom = ednom.getText().toString();
                desc = eddesc.getText().toString();

                //System.out.println("nom = " + nom + " desc = " + desc + " value = " + value + " devise = " + dev.toString());
                if((nom != "") && (desc != "") && (value != null) && (periodiciteSpinner.getSelectedItemPosition() != 0))
                {
                    DatePicker dateDebutPicker = (DatePicker) findViewById(R.id.dateDebutPicker);
                    DatePicker dateFinPicker = (DatePicker) findViewById(R.id.dateFinPicker);
                    Calendar calendar = new GregorianCalendar();
                    Date dateDeDebut = null;
                    Date dateDeFin = null;
                    if(periodiciteSpinner.getSelectedItemPosition() == 1)
                    {
                        int jourDebut = dateDebutPicker.getDayOfMonth();
                        int moisDebut = dateDebutPicker.getMonth() + 1;
                        int anneeDebut = dateDebutPicker.getYear();

                        calendar.set(anneeDebut, moisDebut, jourDebut, 0, 0,0);
                        dateDeDebut = calendar.getTime();
                    }
                    else
                    {
                        int jourDebut = dateDebutPicker.getDayOfMonth();
                        int moisDebut = dateDebutPicker.getMonth() + 1;
                        int anneeDebut = dateDebutPicker.getYear();

                        calendar.set(anneeDebut, moisDebut, jourDebut, 0, 0,0);
                        dateDeDebut = calendar.getTime();


                        int jourFin = dateDebutPicker.getDayOfMonth();
                        int moisFin = dateDebutPicker.getMonth() + 1;
                        int anneeFin = dateDebutPicker.getYear();
                        calendar.set(anneeFin, moisFin, jourFin, 0, 0,0);
                        dateDeFin = calendar.getTime();
                    }

                    //Il faut gérer l'envoi de la date de début / fin
                    Activity newAct = account.addActivity(value, desc, nom,null/**/, -1);
                    activityDatas.ajout(account, owner, newAct);

                    Intent otherActivity = new Intent(getApplicationContext(), AccountControler.class);
                    otherActivity.putExtra(MainActivity.EXTRA_ID, accountId);
                    otherActivity.putExtra(MainActivity.EXTRA_FIRST_NAME, owner.getFirstName());
                    otherActivity.putExtra(MainActivity.EXTRA_LAST_NAME, owner.getLastName());
                    startActivity(otherActivity);
                    finish();
                }

            }
        });

        //On recupere le spinner et on l'associe à la périodicité
        periodiciteSpinner = findViewById(R.id.periodicite_spinner);

        //On établit la périodicité dans le spinner
        ArrayAdapter<CharSequence> adapter0 = ArrayAdapter.createFromResource(this, R.array.periodicity, android.R.layout.simple_spinner_item);
        adapter0.setDropDownViewResource(R.layout.periodicity_spinner_text_view);
        periodiciteSpinner.setAdapter(adapter0);
        periodiciteSpinner.setSelection(0);
        periodiciteSpinner.setOnItemSelectedListener(this);
    }

    private void loadDatas (){
        Intent intent = getIntent();
        accountId = intent.getIntExtra(MainActivity.EXTRA_ID, -1);
        firstName = intent.getStringExtra(MainActivity.EXTRA_FIRST_NAME);
        lastName = intent.getStringExtra(MainActivity.EXTRA_LAST_NAME);

        //création du propriétaire
        PersonDatas personDatas = new PersonDatas(this);
        this.owner = personDatas.findUser(firstName, lastName);

        // on récupère toutes ces activités
        this.accountDatas = new AccountDatas(this);
        accountDatas.loadAcc(this.owner);

        // on récupère la compte choisi
        this.account = this.owner.findAccountById(accountId);

        //
        this.activityDatas = new ActivityDatas(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LinearLayout dateDeDebutLayout = findViewById(R.id.dateDebutLinearLayout);
        LinearLayout dateDeFinLayout = findViewById(R.id.dateFinLinearLayout);

        if (position == 0) {
            dateDeDebutLayout.setEnabled(false);
            dateDeDebutLayout.setVisibility(View.INVISIBLE);
            dateDeFinLayout.setEnabled(false);
            dateDeFinLayout.setVisibility(View.INVISIBLE);
        }
        else if(position == 1)
        {
            TextView date = findViewById(R.id.dateDeDebut);
            date.setText("Date");
            dateDeDebutLayout.setEnabled(true);
            dateDeDebutLayout.setVisibility(View.VISIBLE);
            dateDeFinLayout.setEnabled(false);
            dateDeFinLayout.setVisibility(View.INVISIBLE);
        }
        else {
            TextView date = findViewById(R.id.dateDeDebut);
            date.setText("Date de début");
            dateDeDebutLayout.setEnabled(true);
            dateDeDebutLayout.setVisibility(View.VISIBLE);
            dateDeFinLayout.setEnabled(true);
            dateDeFinLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
