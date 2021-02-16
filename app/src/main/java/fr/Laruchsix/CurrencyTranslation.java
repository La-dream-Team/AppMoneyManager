package fr.Laruchsix;

public class CurrencyTranslation {
    public static float toEuro(Devise dev)
    {
        switch(dev){
            case Dolar_American :
                return 1.2125f;
            case Livre_Sterling :
                return 0.8885f;
            case Yen :
                return 125.8712f;
            case Rouble :
                return 91.6078f;
            default :
                return 1f;
        }
    }

    public static float toDolarAmerican(Devise dev)
    {
        switch(dev){
            case Euro :
                return 0.8246f;
            case Livre_Sterling :
                return 0.7323f;
            case Yen :
                return 103.8015f;
            case Rouble :
                return 75.5571f;
            default :
                return 1f;
        }
    }

    public static float toLivreSterling(Devise dev)
    {
        switch(dev){
            case Euro :
                return 1.1260f;
            case Dolar_American :
                return 1.3655f;
            case Yen :
                return 141.7380f;
            case Rouble :
                return 103.1712f;
            default :
                return 1f;
        }
    }

    public static float toYen(Devise dev)
    {
        switch(dev){
            case Euro :
                return 0.0079f;
            case Livre_Sterling :
                return 0.0071f;
            case Dolar_American :
                return 0.0096f;
            case Rouble :
                return 0.7277f;
            default :
                return 1f;
        }
    }

    public static float toRouble(Devise dev)
    {
        switch(dev){
            case Euro :
                return 0.0109f;
            case Livre_Sterling :
                return 0.0096f;
            case Yen :
                return 1.3741f;
            case Dolar_American :
                return 0.0132f;
            default :
                return 1f;
        }
    }

    public static float coefDev(Devise from, Devise to)
    {
        if(from != to)
        {
            switch (to){
                case Euro :
                    return toEuro(from);
                case Livre_Sterling :
                    return toLivreSterling(from);
                case Yen :
                    return toYen(from);
                case Dolar_American :
                    return toDolarAmerican(from);
                case Rouble:
                    return toRouble(from);
                default :
                    return 1f;
            }
        }
        else
        {
            return 1.0f;
        }
    }
    public static String currencySymbol(Devise devise)
    {
        switch (devise){
            case Euro:
                return "Euro (€)";
            case Livre_Sterling:
                return "Pound sterling (£)";
            case Yen:
                return "Yen (¥)";
            case Dolar_American:
                return "American dollar ($)";
            case Rouble:
                return "Ruble (₽)";
            default:
                return "";
        }
    }
}
