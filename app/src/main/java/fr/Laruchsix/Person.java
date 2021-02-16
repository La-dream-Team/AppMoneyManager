package fr.Laruchsix;

import java.util.ArrayList;
import java.util.Date;

public class Person {
    // attributs
    private float globalBalance;
    private String firstName;
    private String secondName;
    private Devise globalDevise;
    private InterfaceColors colors;
    private ArrayList<Account> acconts;
    
    // constructeurs
    public Person(Devise dev, String firstName, String secondName)
    {
        this.globalBalance = 0f;
        this.firstName = firstName;
        this.secondName = secondName;
        this.globalDevise = dev;
        
        this.acconts = new ArrayList<>();
        this.colors = new InterfaceColors();
    }
    
    // get/set
    public String getFirstName() 
    {
        return this.firstName;
    }
    
    public String getSecondName() 
    {
        return this.secondName;
    }
    
    public Devise getDevise()
    {
        return this.globalDevise;
    }
    
    public void setDevise(Devise newdev)
    {
        float coef = FonctionsAux.coefDev(this.globalDevise, newdev);
        this.globalBalance = this.globalBalance * coef;
        this.globalDevise =newdev;
    }
    
    public InterfaceColors getColors()
    {
        return this.colors;
    }
    
    // methodes
    public void refresh()
    {
        float sum = 0f;
        // on parcours tous les compte de l'utilisateur
        for(Account currentacc : this.acconts)
        {
            float coef = FonctionsAux.coefDev(currentacc.getDevise(), this.globalDevise);
            sum += currentacc.getCurrentBalance() * coef;
        }
        this.globalBalance = sum;
    }
    
    public void forceRefresh()
    {
        // on parcours tous les comptes de l'utilisateur
        for(Account currentAcc : this.acconts)
        {
            currentAcc.forceRefresh();
        }
        this.refresh();
    }

    @Override
    public String toString()
    {
        String ret = this.firstName + " " + this.secondName + " " + this.globalDevise.toString() + " "
                + this.acconts.size();
        for(Account currentAcc : this.acconts)
        {
            ret = ret + "\n" + currentAcc.toString();
         }

        return ret;
    }
}
