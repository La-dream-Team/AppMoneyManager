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
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.activity = this;


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

        // button load
        Button loaduser= findViewById(R.id.butload);
        loaduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }


    private String readData() {
        String fileName = getResources().getString(R.string.dateUser);
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
}