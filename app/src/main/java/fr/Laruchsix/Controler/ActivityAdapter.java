package fr.Laruchsix.Controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import fr.Laruchsix.Model.Account;
import fr.Laruchsix.Model.Activity;
import fr.Laruchsix.Model.CurrencyTranslation;
import fr.Laruchsix.R;

public class ActivityAdapter extends BaseAdapter {
    private Context context;
    private List<Activity> activityList;
    private LayoutInflater inflater;
    private Account account;

    public ActivityAdapter(Context context, List<Activity> activityList, Account account)
    {
        this.context = context;
        this.activityList = activityList;
        this.inflater = LayoutInflater.from(context);
        this.account = account;
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return activityList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_activity, null);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Activity currentActivity = (Activity) getItem(position);
        String activityName = currentActivity.getName();
        String activityDescription = currentActivity.getDescription();
        String activityDate = dateFormat.format(currentActivity.getDate());
        String activityBalance = String.valueOf(currentActivity.getValue());
        String activityDevise = CurrencyTranslation.currencySymbol(account.getDevise());

        TextView activityNameView = convertView.findViewById(R.id.activity_name);
        activityNameView.setText(activityName);

        TextView activityDescriptionView = convertView.findViewById(R.id.activity_description);
        activityDescriptionView.setText(activityDescription);

        TextView activityDateView = convertView.findViewById(R.id.activity_date);
        activityDateView.setText(activityDate);

        TextView activityBalanceView = convertView.findViewById(R.id.activity_balance);
        activityBalanceView.setText(activityBalance);

        TextView activityDeviseView = convertView.findViewById(R.id.activity_currency);
        activityDeviseView.setText(activityDevise);

        return convertView;
    }
}
