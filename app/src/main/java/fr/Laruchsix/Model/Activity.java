package fr.Laruchsix.Model;

import java.util.Date;

import fr.Laruchsix.Model.Account;

public class Activity {
    private final float value;
    private final String description;
    private final String name;
    private final Date date;
    private final int id;
    private static int current_id = 0;
    private final Account account;
    
    public Activity(float vl, String ds, String nm, Date dt, Account ac)
    {
        this.value = vl;
        this.description = ds;
        this.name = nm;
        this.date = dt;
        this.id = current_id;
        current_id++;
        this.account = ac;
    }
    
    public float getValue()
    {
        return this.value;
    }
    
    public String getDescription()
    {
        return this.description;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Date getDate()
    {
        return this.date;
    }

    public int getId()
    {
        return this.id;
    }
    
    public Account getAccount()
    {
        return this.account;
    }

    @Override
    public String toString()
    {
        String ret = this.name + "--" + this.description + "--" + this.value;
        return ret;
    }
}
