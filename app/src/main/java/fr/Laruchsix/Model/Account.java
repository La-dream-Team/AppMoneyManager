package fr.Laruchsix.Model;

import java.util.ArrayList;
import java.util.Date;

public class Account {
    // attributs
    // montant qu'il y avais par defaut sur le compte
    private final float DEFAULT_BALANCE;
    // montant du compte apres contabilité de toutes les activités
    private float currentBalance;
    private String name;// nom du compte
    private String description;
    private final Date openningDate; // date d'ouverture du compte
    private InterfaceColors colors; // couleurs choisi pour le ce compte
    private final int id; //
    public static int max_id = 0;
    private ArrayList<Activity> activites;
    private final Person owner;
    private Devise accountDevise;

    // constructeurs
    public Account(float balance, String name, String description
                   ,Date currentDate, Person owner, Devise devise)
    {
        this.DEFAULT_BALANCE = balance;
        this.currentBalance = balance;
        this.name = name;
        this.description = description;
        this.openningDate = currentDate;
        this.accountDevise = devise;

        this.colors = new InterfaceColors();
        this.colors.setBackGround(owner.getColors().getBackGround());
        this.colors.setCredit(owner.getColors().getCredit());
        this.colors.setDebit(owner.getColors().getDebit());


        this.activites = new ArrayList<>();
        this.owner = owner;

        this.id = max_id;
        max_id += 1;
    }

    public Account(float balance, String name, String description
            ,Date currentDate, Person owner, Devise devise, int id)
    {
        this.DEFAULT_BALANCE = balance;
        this.currentBalance = balance;
        this.name = name;
        this.description = description;
        this.openningDate = currentDate;
        this.accountDevise = devise;

        this.colors = new InterfaceColors();
        this.colors.setBackGround(owner.getColors().getBackGround());
        this.colors.setCredit(owner.getColors().getCredit());
        this.colors.setDebit(owner.getColors().getDebit());


        this.activites = new ArrayList<>();
        this.owner = owner;

        this.id = id;
        if(id >= max_id)
            max_id = id + 1 ;
    }

    // getters / setters
    public float getDefaultBalance()
    {
        return this.DEFAULT_BALANCE;
    }

    public float getCurrentBalance()
    {
        return this.currentBalance;
    }

    public int getID()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription(){ return  this.description; }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getOpenningDate()
    {
        return this.openningDate;
    }

    public InterfaceColors getColors()
    {
        return this.colors;
    }

    public ArrayList<Activity> getActivities()
    {
        return this.activites;
    }

    public Devise getDevise()
    {
        return this.accountDevise;
    }

    public void setDevise(Devise dev)
    {
        this.accountDevise = dev;
    }

    public Person getOwner()
    {
        return this.owner;
    }

    public static int getMax_id() { return max_id; };

    public  static void setMax_id(int maxi) { max_id = maxi; };

    public int getId() { return this.id;  };

    // methodes
    public void addActivity(float val, String des, String name, Date day, int id)
    {
        Activity newAct;
        // creation de l'activité
        if(id == -1)
            newAct = new Activity(val, des, name, day, this);
        else
            newAct = new Activity(val, des, name, day, this, id);
        this.activites.add(newAct); // ajout aux activité du compte
        this.refresh(newAct); // mise a jours du solode courant
    }

    public void refresh(Activity newAct)
    {
        this.currentBalance += newAct.getValue(); // on met a jours le montant courant du compte

        this.owner.refreshAll();
    }

    // prend l'id de l'activité pour la suprimer
    // renvoie vrai si la supression c'est fait avec succès
    public boolean removeAct(int id)
    {
        boolean ret = false;
        // on parcours toutes les activités
        for(Activity currentAct : this.activites)
        {
            if(currentAct.getId() == id)
            {
                this.currentBalance -= currentAct.getValue();
                this.activites.remove(currentAct);
                ret = true;
                break;
            }
        }
        return ret;
    }

    public void forceRefresh()
    {
        this.currentBalance = this.DEFAULT_BALANCE;
        // on parcours toutes les activités du compte
        for(Activity currentAct : this.activites)
        {
            this.currentBalance += currentAct.getValue();
        }
    }

    @Override
    public String toString()
    {
        String ret = this.name + "--" + this.description + "--" + this.accountDevise.toString() + "--"
                + this.DEFAULT_BALANCE + "--" + this.activites.size();
        for(Activity currentAct : this.activites)
        {
            ret = ret + "\n" + currentAct.toString();
        }

        return ret;
    }
}
