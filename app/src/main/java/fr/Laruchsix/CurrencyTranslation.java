package fr.Laruchsix;

public class CurrencyTranslation {
    public static float toEuro(Devise dev)
    {
        switch(dev){
            case Dolar_American :
                return 0.83025f;
            case Livre_Sterling :
                return 1.15154f;
            case Yen :
                return 0.00782f;
            case Rouble :
                return 0.0112263f;
            default :
                return 1.0f;
        }
    }

    public static float toDolarAmerican(Devise dev)
    {
        switch(dev){
            case Euro :
                return 1.20450f;
            case Livre_Sterling :
                return 1.38650f;
            case Yen :
                return 0.00942f;
            case Rouble :
                return 0.0135153f;
            default :
                return 1.0f;
        }
    }

    public static float toLivreSterling(Devise dev)
    {
        switch(dev){
            case Euro :
                return 0.86875f;
            case Dolar_American :
                return 0.721163f;
            case Yen :
                return 0.00679605f;
            case Rouble :
                return 0.00974641f;
            default :
                return 1.0f;
        }
    }

    public static float toYen(Devise dev)
    {
        switch(dev){
            case Euro :
                return 127.94f;
            case Livre_Sterling :
                return 147.269f;
            case Dolar_American :
                return 106.205f;
            case Rouble :
                return 1.43567f;
            default :
                return 1.0f;
        }
    }

    public static float toRouble(Devise dev)
    {
        switch(dev){
            case Euro :
                return 89.0774f;
            case Livre_Sterling :
                return 102.541f;
            case Yen :
                return 0.697109f;
            case Dolar_American :
                return 73.9753f;
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
