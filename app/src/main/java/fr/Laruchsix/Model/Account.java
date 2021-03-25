package fr.Laruchsix.Model;

import android.content.Context;
import android.os.Build;
import android.provider.CalendarContract;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import fr.Laruchsix.R;

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
    public Activity addActivity(float val, String des, String name, Date day, int id, Periodicity periodicity, Date endDate)
    {
        Activity newAct;
        // creation de l'activité
        if(id == -1)
            newAct = new Activity(val, des, name, day, this, periodicity, endDate);
        else
            newAct = new Activity(val, des, name, day, this, id, periodicity, endDate);
        this.activites.add(newAct); // ajout aux activité du compte
        this.refresh(newAct); // mise a jours du solode courant
        return newAct;
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
        this.getActivitiesDate(null, Calendar.getInstance().getTime());
    }

    public void fillList (ArrayList<Activity> oldList)
    {
        Comparator<Activity> c = new Comparator<Activity>() {
            @Override
            public int compare(Activity o1, Activity o2) {
                return (int) (o1.getDate().getTime() - o2.getDate().getTime());
            }
        };
        oldList.sort(c);
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


    public String[] getActivityNames(Context context) {
        String[] activityNames = new String[this.activites.size() + 1];
        activityNames[0] = context.getString(R.string.allAct);
        for (int i = 1; i < this.activites.size() + 1; i++) {
            activityNames[i] = this.activites.get(i - 1).getName();
        }
        return activityNames;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Activity> computeBalanceFromDate (Month mois, Year annee){
        if(mois != null)
            System.out.println("mois = " + mois.toString());
        else
            System.out.println("mois = null");
        if(annee != null)
            System.out.println("annee = " + annee.toString());
        else
            System.out.println("annee = null");

        // si l'utilisateur veux visualier l'ensemble des acitivités du compte
        if((mois == null) && (annee == null)){
            // la date actuelle
            Date datedeFin = Calendar.getInstance().getTime() ;

            return this.getActivitiesDate(null, datedeFin);
        }
        else{
            Calendar calendar = new GregorianCalendar();

            Date dateDefin, dateDeDebut;
            if(mois == null){
                // on crée la date de début
                calendar.set(annee.getValue(), 0, 1, 0, 0,0);
                dateDeDebut = calendar.getTime();

                // on cree la date de fin
                calendar.set((annee.getValue() + 1), 0, 0, 23, 59,59);
                dateDefin = calendar.getTime();

                return this.getActivitiesDate(dateDeDebut, dateDefin);
            }
            else{
                // on crée la date de début
                calendar.set(annee.getValue(), mois.getValue()-1, 1, 0, 0,0);
                dateDeDebut = calendar.getTime();

                // on cree la date de fin
                calendar.set(annee.getValue(), mois.getValue(), 0, 23, 59,59);
                dateDefin = calendar.getTime();

                return this.getActivitiesDate(dateDeDebut, dateDefin);
            }
        }
    }

    private Date getNextDate (Date oldDate, Periodicity periodicity)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(oldDate);
        switch (periodicity){
            case Weekly:
                calendar.set(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 7);
                return calendar.getTime();
            case Quarterly:
                calendar.set(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) + 3, calendar.get(Calendar.DAY_OF_MONTH));
                return calendar.getTime();
            case Daily:
                calendar.set(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 1);
                return calendar.getTime();
            case Fortnightly:
                calendar.set(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) + 2, calendar.get(Calendar.DAY_OF_MONTH));
                return calendar.getTime();
            case Annual:
                calendar.set(calendar.get(Calendar.YEAR) +1 , calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                return calendar.getTime();
            default:
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
                return calendar.getTime();
        }
    }


    public ArrayList<Activity> getActivitiesDate(Date dateDeDebut,Date dateDefin){
        System.out.println(/*"date de debut : " + dateDeDebut.toString() + */"\ndate de fin : " + dateDefin.toString());

        ArrayList<Activity> ret = new ArrayList<>();

        // on remet a 0 le solde du compte
        this.currentBalance = this.DEFAULT_BALANCE;
        Date currentDate;

        for(Activity currentAct : this.activites){
            Date actDate = currentAct.getDate();
            //System.out.println(currentAct.toString());
            switch (currentAct.getPeriodicity()){
                case Occasional:
                    if( actDate.before(dateDefin)){
                        this.currentBalance += currentAct.getValue();
                        if((dateDeDebut == null) || actDate.after(dateDeDebut)){
                            ret.add(currentAct);
                        }
                    }
                    break;
                default:
                    currentDate = actDate;
                    while(currentDate.before(dateDefin)){
                        this.currentBalance += currentAct.getValue();
                        if((dateDeDebut == null) || (currentDate.after(dateDeDebut))){
                            Activity newAct = new Activity(currentAct.getValue(), currentAct.getDescription(), currentAct.getName(), currentDate,
                                    null, currentAct.getPeriodicity(), currentAct.getEndDate());
                            ret.add(newAct);
                            System.out.println("nouvelle activitée" + newAct.toString());
                        }
                        currentDate = getNextDate(currentDate, currentAct.getPeriodicity());
                        if(currentDate.after(currentAct.getEndDate()))
                            break;
                        //System.out.println(currentDate.toString() + " is befor than " + dateDefin.toString());
                        //System.out.println(currentDate.before(dateDefin));
                    }
            }
        }
        /*
        System.out.println("liste des activités");
        for(Activity current : ret ){
            System.out.println(current.toString());
        }*/
        this.fillList(ret);
        return ret;
    }
}
