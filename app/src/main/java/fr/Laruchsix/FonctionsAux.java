package fr.Laruchsix;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

public class FonctionsAux {

    public static void clearFile(String fileName, AppCompatActivity act)
    {
        String clear = "";
        try {
            // Open Stream to write file.
            FileOutputStream out = act.openFileOutput(fileName, MODE_PRIVATE);
            out.write(clear.getBytes());
            out.close();

        } catch (Exception e) {
            Toast.makeText(act,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public static void savePerson(String firstName, String lastName, AppCompatActivity act, Person per)
    {
        String fileName = lastName + firstName + "Data.txt";
        clearFile(fileName, act);
        try {
            // Open Stream to write file.
            FileOutputStream out = act.openFileOutput(fileName, MODE_PRIVATE);

            // on met l'espace utilisateur dans le fichier
            String save = per.toString();
            System.out.println(save);
            out.write(save.getBytes());
            out.close();

        } catch (Exception e) {
            Toast.makeText(act,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


      public static Person loadUser(String firstName, String lastName, AppCompatActivity act)
      {
          String fileName = lastName + firstName + "Data.txt";
          StringBuilder sb;
          Person p = null;
          String ret = null;
          try {
              // Open stream to read file.
              FileInputStream in = act.openFileInput(fileName);

              BufferedReader br= new BufferedReader(new InputStreamReader(in));

              sb= new StringBuilder();
              String s= null;
              while((s= br.readLine())!= null)  {
                  ret = ret + "\n" + sb.append(s).append("\n").toString();
              }
          } catch (Exception e) {
              //Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
              return null;
          }
          p = StringToPerson(ret);
          return p;
      }

      /*
      sb = sb.append(s).append("\n");
                  String[] person = sb.toString().split("--");
                  p = new Person(Devise.valueOf(person[2]), person[0], person[1]);

                  for(int i=0; i < Integer.parseInt(person[3]) ; i++)
                  {
                      s = br.readLine();
                      sb = sb.append(s).append("\n");
                      String[] currentStringAcc = sb.toString().split("--");
                      Account currentAcc = p.addNewAccount(Float.parseFloat(currentStringAcc[3]), currentStringAcc[0],
                      Account currentAcc = p.addNewAccount(Float.valueOf(currentStringAcc[3]), currentStringAcc[0],
                              currentStringAcc[1], Devise.valueOf(currentStringAcc[2]));

                      for (int j=0 ; j < Integer.valueOf(currentStringAcc[4]) ; j++)
                      {
                          String[] currentActString = sb.toString().split("--");
                          currentAcc.addActivity(Float.valueOf(currentActString[2]), currentActString[1], currentActString[0], null);
                      }
                  }
       */

    public static Person StringToPerson(String string)
    {
        int compteur = 2;
        String[] tab = string.split("\n");
        String[] person = tab[1].split("--");
        Person p = new Person(Devise.valueOf(person[2]), person[0], person[1]);

        for(String currentperson : tab)
        {
            System.out.println(currentperson);
        }
/*
        int nbAccount = Integer.parseInt(person[3]);
        while(nbAccount != 0)
        {
            System.out.println(tab[compteur]);

            String[] currentStringAcc = tab[compteur].split("--");
            compteur ++;
             Account currentAcc = p.addNewAccount(Float.parseFloat(currentStringAcc[3]), currentStringAcc[0],
                    currentStringAcc[1], Devise.valueOf(currentStringAcc[2]));
            int nbAct = Integer.parseInt(currentStringAcc[4]);
            while(nbAct !=0)
            {
                String[] currentActString = tab[compteur].split("--");
                compteur++;
                currentAcc.addActivity(Float.valueOf(currentActString[2]), currentActString[1], currentActString[0], null);
                nbAct --;
            }
            nbAccount --;
        }*/
        return p;
    }

    public static void saveUser(String nom, String prenom, AppCompatActivity activity)
    {
        String users = readUser(activity);
        String fileName = "user.txt";
        StringBuilder sb;

        String save = users + nom + "--" + prenom + "\n" ;
        try {
            // Open Stream to write file.
            FileOutputStream out = activity.openFileOutput(fileName, MODE_PRIVATE);

            // on met l'espace utilisateur dans le fichier
            out.write(save.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(activity,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    public static String readUser(AppCompatActivity activity) {
        String fileName = "user.txt";
        StringBuilder sb;
        String ret = null;
        try {
            // Open stream to read file.
            FileInputStream in = activity.openFileInput(fileName);

            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            sb= new StringBuilder();
            String s= null;
            while((s= br.readLine())!= null)  {
                ret = ret + "\n" + sb.append(s).append("\n").toString();

            }
        } catch (Exception e) {
            //Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
            return "";
        }
        return sb.toString();
    }


    public static String createUserFile(String firstName, String lastName, Devise devise, AppCompatActivity activity)
    {
        Person newp = new Person(devise, firstName, lastName);
        String fileName = lastName + firstName + "Data.txt";
        StringBuilder sb;
        String ret = newp.toString();
        try {
            // Open Stream to write file.
            FileOutputStream out = activity.openFileOutput(fileName, MODE_PRIVATE);

            // on met l'espace utilisateur dans le fichier
            out.write(ret.getBytes());
            out.close();
        } catch (Exception e) {
            Toast.makeText(activity,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return ret;
    }
}
