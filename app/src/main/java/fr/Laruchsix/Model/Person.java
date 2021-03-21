package fr.Laruchsix.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import fr.Laruchsix.R;

public class Person {
    // attributs
    private float globalBalance;
    private String firstName;
    private String lastName;
    private Devise globalDevise;
    private InterfaceColors colors;
    private ArrayList<Account> accounts;
    private int id;
    private static int maxid = 0;
    
    // constructeurs
    public Person(Devise dev, String firstName, String lastName)
    {
        this.globalBalance = 0.0f;
        this.firstName = firstName;
        this.lastName = lastName;
        this.globalDevise = dev;
        
        this.accounts = new ArrayList<>();
        this.colors = new InterfaceColors();

        this.id = maxid;
        maxid++;
    }

    public Person(Devise dev, String firstName, String lastName, int id)
    {
        this.globalBalance = 0.0f;
        this.firstName = firstName;
        this.lastName = lastName;
        this.globalDevise = dev;

        this.accounts = new ArrayList<>();
        this.colors = new InterfaceColors();

        this.id = id;
        if(maxid <= id)
            maxid = id + 1;

    }
    
    // get/set
    public String getFirstName() 
    {
        return this.firstName;
    }
    
    public String getLastName()
    {
        return this.lastName;
    }
    
    public Devise getDevise()
    {
        return this.globalDevise;
    }

    public float getGlobalBalance(){ return this.globalBalance; }

    public int getId(){ return this.id; }

    private void setTotalBalance(float newTotalBalance){ this.globalBalance = newTotalBalance;}

    public ArrayList<Account> getAccounts(){ return this.accounts; }

    public void setDevise(Devise newdev)
    {
        float coef = CurrencyTranslation.coefDev(this.globalDevise, newdev);
        this.globalBalance = this.globalBalance * coef;
        this.globalDevise = newdev;
    }

    public InterfaceColors getColors()
    {
        return this.colors;
    }

    public static void setMaxid(int mid) { maxid = mid; };

    // methodes
    public void refreshAll()
    {
        float sum = 0.0f;
        // on parcours tous les compte de l'utilisateur
        for(Account currentacc : this.accounts)
        {
            float coef = CurrencyTranslation.coefDev(currentacc.getDevise(), this.globalDevise);
            sum += currentacc.getCurrentBalance() * coef;
        }
        this.globalBalance = sum;
    }

    public void refreshNewOne(float balance, Devise accountDevise)
    {
        this.globalBalance += balance * CurrencyTranslation.coefDev(accountDevise, this.globalDevise);
    }

    public Account findName(String name)
    {
        Account ret = null;

        for(Account currentAcc : this.accounts)
        {
            if(currentAcc.getName() == name)
            {
                ret = currentAcc;
                break;
            }
        }
        return ret;
    }

    public Account addNewAccount(float balance, String name, String description, Devise devise, int id)
    {
        Account ret;
        Date currentTime = Calendar.getInstance().getTime();
        if(id == -1)
            ret = new Account(balance, name, description, currentTime, this, devise);
        else
            ret = new Account(balance, name, description, currentTime, this, devise, id);
        refreshNewOne(balance, devise);
        this.accounts.add(ret);
        return ret;
    }

    public void forceRefresh()
    {
        // on parcours tous les comptes de l'utilisateur
        for(Account currentAcc : this.accounts)
        {
            currentAcc.forceRefresh();
        }
        this.refreshAll();
    }

    @Override
    public String toString()
    {
        String ret = this.firstName + "--" + this.lastName + "--" + this.globalDevise.toString() + "--"
                + this.accounts.size();
        for(Account currentAcc : this.accounts)
        {
            ret = ret + "\n" + currentAcc.toString();
         }

        return ret;
    }

    public Account findAccountById(int id)
    {
        Account ret = null;
        for(Account currentAcc : this.accounts)
        {
            if(currentAcc.getId() == id){
                ret = currentAcc;
                break;
            }
        }
        return ret;
    }

    public String[] getAccountNames(Context context)
    {
        String[] accountNames = new String[this.accounts.size()+1];
        accountNames[0] = context.getString(R.string.allAcc);
        for(int i= 1; i < this.accounts.size()+1; i++)
        {
            accountNames[i] = this.accounts.get(i-1).getName();
        }
        return accountNames;
    }
}
