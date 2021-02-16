package fr.Laruchsix;


import android.graphics.Color;

public class InterfaceColors {
    // attributs
    private Color background;
    private Color credit;
    private Color debit;
    
    // constructeur
    public InterfaceColors(){
    }
    
    // methodes 
    public Color getBackGround()
    {
        return this.background;
    } 
    
    public void setBackGround(Color color)
    {
        this.background = color;
    }
    
    public Color getCredit()
    {
        return this.credit;
    }
    
    public void setCredit(Color color)
    {
        this.credit = color;
    }
    
    public Color getDebit()
    {
        return this.debit;
    } 
    
    public void setDebit(Color color)
    {
        this.debit = color;
    }
    
}
