package fr.Laruchsix;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TextView;


public class Popup extends Dialog {

    // attributs
    private String title, subTitle;
    private Button yesButton, noButton;
    private TextView titleView, subTitleView;

    // constructeurs
    public Popup (Activity activity)
    {
        super(activity, R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.my_popup_layout);
        this.title ="Mon titre";
        this.title = "Mon sous-titre";
        this.noButton = findViewById(R.id.noButton);
        this.yesButton = findViewById(R.id.yesbutton);
        this.titleView = findViewById(R.id.titlepop);
        this.subTitleView = findViewById(R.id.descpop);
    }

    public void setTitle (String string)
    {
        this.title = string;
    }

    public void setSubTitle (String string)
    {
        this.subTitle =string;
    }

    public Button getYesButton()
    {
        return this.yesButton;
    }

    public Button getNoButton()
    {
        return this.noButton;
    }

    public void setTitleSize (float size)
    {
        this.titleView.setTextSize(size);
    }
    public void setSubTitleSize (float size)
    {
        this.subTitleView.setTextSize(size);
    }

    public void build()
    {
        show();
        titleView.setText(title);
        subTitleView.setText(subTitle);
    }

}
