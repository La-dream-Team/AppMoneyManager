package fr.Laruchsix.SQLite;

import android.content.Context;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Person;

public class MethodesAux {
    public static void loadActivites(Person owner, Account currentAcc, Context context){
        ActivityDatas activityDatas = new ActivityDatas(context);

        activityDatas.findAllAct(owner, currentAcc);
    }
}
