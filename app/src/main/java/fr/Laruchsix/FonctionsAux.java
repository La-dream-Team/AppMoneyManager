package fr.Laruchsix;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

public class FonctionsAux {
    public static float toEuro(Devise dev)
    {
        switch(dev){
            case Dolar_American :
                return 1.2125f;
            case Livre_Sterling :
                return 0.8885f;
            case Yen :
                return 125.8712f;
            case Rouble :
                return 91.6078f;
            default :
                return 1f;
        }
    } 
    
    public static float toDolarAmerican(Devise dev)
    {
        switch(dev){
            case Euro :
                return 0.8246f;
            case Livre_Sterling :
                return 0.7323f;
            case Yen :
                return 103.8015f;
            case Rouble :
                return 75.5571f;
            default :
                return 1f;
        }
    } 
    
    public static float toLivreSterling(Devise dev)
    {
        switch(dev){
            case Euro :
                return 1.1260f;
            case Dolar_American :
                return 1.3655f;
            case Yen :
                return 141.7380f;
            case Rouble :
                return 103.1712f;
            default :
                return 1f;
        }
    } 
    
    public static float toYen(Devise dev)
    {
        switch(dev){
            case Euro :
                return 0.0079f;
            case Livre_Sterling :
                return 0.0071f;
            case Dolar_American :
                return 0.0096f;
            case Rouble :
                return 0.7277f;
            default :
                return 1f;
        }
    } 
    
    public static float toRouble(Devise dev)
    {
        switch(dev){
            case Euro :
                return 0.0109f;
            case Livre_Sterling :
                return 0.0096f;
            case Yen :
                return 1.3741f;
            case Dolar_American :
                return 0.0132f;
            default :
                return 1f;
        }
    } 
    
    public static float coefDev(Devise from, Devise to)
    {
        if(from != to)
        {
            switch (to){
                case Euro :
                    return toEuro(from);
                case Livre_Sterling :
                    return toLivreSterling(from);
                case Yen :
                    return toYen(from);
                case Dolar_American :
                    return toDolarAmerican(from);
                case Rouble:
                    return toRouble(from);
                default :
                    return 1f;
            }
        }
        else
        {
            return 1.0f;
        }  
    }


    public static void saveUser(String fistName, String lastName, AppCompatActivity act, Account acc)
    {
        String fileName = fistName + lastName + "data.txt";
        StringBuilder sb;
        try {
            // Open Stream to write file.
            FileOutputStream out = act.openFileOutput(fileName, MODE_PRIVATE);

            // on met l'espace utilisateur dans le fichier
            String save = acc.toString();
            out.write(save.getBytes());
            out.close();

        } catch (Exception e) {
            Toast.makeText(act,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


      public static Account loadUser(String fistName, String lastName, AppCompatActivity act)
      {
          String fileName = fistName + lastName + "data.txt";
          StringBuilder sb;
          try {
              // Open stream to read file.
              FileInputStream in = act.openFileInput(fileName);

              BufferedReader br= new BufferedReader(new InputStreamReader(in));

              sb= new StringBuilder();
              String s= null;
              while((s= br.readLine())!= null)  {
                  sb = sb.append(s).append("\n");
                  String[] person = sb.toString().split(" ");
                  Person p = new Person(Devise.valueOf(person[2]), person[0], person[1]);

                  for(int i=0; i < Integer.getInteger(person[3]) ; i++)
                  {
                      sb = sb.append(br.readLine()).append("\n");
                      String[] currentStringAcc = sb.toString().split(" ");
                      Account currentAcc = p.createAcc(Float.valueOf(currentStringAcc[3]), currentStringAcc[0],
                              currentStringAcc[1], Devise.valueOf(currentStringAcc[2]));

                      for int j=0 ; j < InterfaceColors currentStringAcc[4]

                  }
              }
          } catch (Exception e) {
              //Toast.makeText(this,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
              return "";
          }
          return sb.toString();
      }
}
