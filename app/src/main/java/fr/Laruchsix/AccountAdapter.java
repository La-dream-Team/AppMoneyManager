package fr.Laruchsix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AccountAdapter extends BaseAdapter {

    private Context context;
    private List<Account> accountList;
    private LayoutInflater inflater;

    public AccountAdapter(Context context, List<Account> accountList)
    {
        this.context = context;
        this.accountList = accountList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return accountList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return accountList.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_account, null);

        Account currentAccount = (Account) getItem(position);
        String accountName = currentAccount.getName();
        String accountDescription = currentAccount.getDescription();
        String accountBalance = String.valueOf(currentAccount.getCurrentBalance());
        String accountDevise = CurrencyTranslation.currencySymbol(currentAccount.getDevise());

        TextView accountNameView = convertView.findViewById(R.id.account_name);
        accountNameView.setText(accountName);

        TextView accountDescriptionView = convertView.findViewById(R.id.account_description);
        accountDescriptionView.setText(accountDescription);

        TextView accountDeviseView = convertView.findViewById(R.id.account_currency);

        accountDeviseView.setText(accountDevise);

        TextView accountBalanceView = convertView.findViewById(R.id.account_balance);
        accountBalanceView.setText(accountBalance);

        return convertView;
    }
}
