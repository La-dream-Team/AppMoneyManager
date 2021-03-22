package fr.Laruchsix.Model;

import java.util.Date;

import fr.Laruchsix.Model.Account;

public class Activity {
    private final float value;
    private final String description;
    private final String name;
    private final Date date;
    private final int id;
    public static int max_id = 0;
    private final Account account;
    private Date endDate = null;
    private Periodicity periodicity;
    
    public Activity(float vl, String ds, String nm, Date dt, Account ac, Periodicity periodicity, Date endDate)
    {
        this.value = vl;
        this.description = ds;
        this.name = nm;
        this.date = dt;
        this.id = max_id;
        max_id++;
        this.account = ac;

        this.periodicity = periodicity;
        this.endDate = endDate;
    }

    public Activity(float vl, String ds, String nm, Date dt, Account ac, int id, Periodicity periodicity, Date endDate)
    {
        this.value = vl;
        this.description = ds;
        this.name = nm;
        this.date = dt;
        this.id = id;
        this.account = ac;

        if(id >= max_id)
            max_id = id + 1 ;

        this.periodicity = periodicity;
        this.endDate = endDate;
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

    public Devise getDevise(){ return this.account.getDevise();}
    
    public Account getAccount()
    {
        return this.account;
    }

    public static int getMax_id() { return  max_id ; }

    public static void setMax_id(int id) { max_id = id; }

    public void setPeriodicity(Periodicity periodicity) { this.periodicity = periodicity;}

    public Periodicity getPeriodicity() { return this.periodicity; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public  Date getEndDate() { return this.endDate; }


    @Override
    public String toString()
    {
        String ret = this.name + "--" + this.description + "--" + this.value + "--" + this.getDate() + "--" + this.getEndDate();
        return ret;
    }
}
